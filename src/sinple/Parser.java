package sinple;
import sinple.ast.InterpoledStringExpr;
import sinple.ast.CompositeStatement;
import sinple.ast.SimpleStringExpr;
import sinple.ast.NullExpr;
import sinple.ast.PrintStatement;
import sinple.ast.ReturnStatement;
import sinple.ast.StatementList;
import sinple.ast.TrueExpr;
import sinple.ast.AssignmentStatement;
import sinple.ast.Expr;
import sinple.ast.NumberExpr;
import sinple.ast.ReadStatement;
import sinple.ast.PreIncDec;
import sinple.ast.WhileStatement;
import sinple.ast.BreakStatement;
import sinple.ast.Statement;
import sinple.ast.FunctionCall;
import sinple.ast.VarExpr;
import sinple.ast.DoWhileStatement;
import sinple.ast.UnaryExpr;
import sinple.ast.ForStatement;
import sinple.ast.PrintLineStatement;
import sinple.ast.ExprStatement;
import sinple.ast.PosIncDec;
import sinple.ast.ArrayInitExpr;
import sinple.ast.ForEachStatement;
import sinple.ast.IfStatement;
import sinple.ast.CompositeExpr;
import sinple.ast.FunctionDeclarStatement;
import sinple.ast.SwitchStatement;
import sinple.ast.ContinueStatement;
import sinple.ast.Program;
import sinple.ast.ReadLineStatement;
import sinple.ast.ArrayAccessVarExpr;
import sinple.ast.ParenthesisExpr;
import sinple.lexer.Lexer;
import java.util.*;

public class Parser {
    
    Lexer lexer;
    Symbol token;  
    SymbolTable symTab;
    
    /** Creates a new instance of Parser */
    public Parser(String file) {
        try {
            lexer = new Lexer(file);
            symTab = new SymbolTable();
        }
        catch(Exception e) {
            Error.signalError("Cannot read file " + file );
        }
    }
    
    public void error(String msg) {
        Error.signalSintaxError( lexer.getCurrentFile() , lexer.getLine() , msg );        
    }
    
    public Symbol nextToken() {
        try { 
            return (token = lexer.nextToken());
        }
        catch(Exception e) {
            Error.signalRuntimeError("IO error");
        }
        return null;
    }
    
    public boolean startsExpr( int tok ) {
        switch(tok) {
            case Sym.DECREMENT:
            case Sym.PLUS:
            case Sym.MINUS:
            case Sym.NOT:
            case Sym.INCREMENT:
            case Sym.LEFTPAR:
            case Sym.FUNCTIONIDENT:
            case Sym.VARIDENT:
            case Sym.LITERALINTEGER:
            case Sym.LITERALINTERPOLEDSTRING:
            case Sym.LITERALSIMPLESTRING:
            case Sym.TRUE:
            case Sym.FALSE:
            case Sym.NULL:                
                  return true;
        }
        return false;
    }
    
    public Statement parse(  ) {
        nextToken();
        return program();
    }
    
    public Statement program() {
        symTab.addNewScope();
        StatementList sl = (StatementList)statementList();
        nextToken();
        if( token.type != Sym.EOF ) {
            error("Invalid statement.");
        }
        symTab.removeAll();
        return new Program( sl );        
    }
    
    public Statement statementList() {
        Vector vec = new Vector();
        
        while( (  token.type != Sym.RIGHTBRACE ) &&
                ( token.type != Sym.EOF ) &&
                ( token.type != Sym.CASE) &&
                ( token.type != Sym.DEFAULT )) {
            vec.add( statement() );
        }
        return new StatementList(vec);        
    }
    
    public Statement compositeStatement() {
        if( token.type != Sym.LEFTBRACE )
            error("\'{\' expected.");
        nextToken();
        symTab.addNewScope(); // for semantic analysis
        StatementList st = (StatementList)statementList();
        if( token.type != Sym.RIGHTBRACE )
            error("\'}\' expected.");
        nextToken();
        symTab.removeLastScope();  // for semantic analysis
        return new CompositeStatement( st );
    }
    
    
    public Statement statement() {
        Statement st;
        switch( token.type ) {
            case Sym.LEFTBRACE:
                return compositeStatement();
            case Sym.FOREACH:
                return forEachStatement();
            case Sym.FOR:
                return forStatement();
            case Sym.FUNCTION:
                return functionDeclarStatement();
            case Sym.IF:
                return ifStatement();
            case Sym.SWITCH:
                return switchStatement();
            case Sym.PRINT:
                st = printStatement();
                if( token.type != Sym.SEMICOLON )
                    error("\';\' expected.");
                nextToken();
                return st;
            case Sym.PRINTLN:
                st = printLineStatement();
                if( token.type != Sym.SEMICOLON )
                    error("\';\' expected.");
                nextToken();
                return st;       
            case Sym.WHILE:
                return whileStatement();
            case Sym.DO:
                st = doWhileStatement();                
                if( token.type != Sym.SEMICOLON )
                    error("\';\' expected.");
                nextToken();
                return st;                  
            case Sym.RETURN:
                st = returnStatement();
                if( token.type != Sym.SEMICOLON )
                    error("\';\' expected.");
                nextToken();
                return st;  
            case Sym.BREAK:
                nextToken();
                st = new BreakStatement();
                if( token.type != Sym.SEMICOLON )
                    error("\';\' expected.");
                nextToken();
                return st;            
            case Sym.CONTINUE:
                nextToken();
                st = new ContinueStatement();
                if( token.type != Sym.SEMICOLON )
                    error("\';\' expected.");
                nextToken();
                return st;    
            case Sym.READ:
                st =  readStatement();
                if( token.type != Sym.SEMICOLON )
                    error("\';\' expected.");
                nextToken();
                return st;
            case Sym.READLN:
                st =  readLineStatement();
                if( token.type != Sym.SEMICOLON )
                    error("\';\' expected.");
                nextToken();
                return st;                           
            default:
                if( ! startsExpr(token.type) ) {
                    error("Statement expected.");
                }
                st = new ExprStatement( expr() );
                if( token.type != Sym.SEMICOLON )
                    error("\';\' expected.");
                nextToken();
                return st;
        }    
    }
    
    public Statement forEachStatement() {
        nextToken(); // eat foreach
        if( token.type != Sym.LEFTPAR )
            error("\'(\' expected in foreach statement.");
        nextToken(); // eat (
        Expr varArray = postFixExpr();
        if( ! (varArray instanceof VarExpr) ) {
            error("Variable or array expected after \'(\' of foreach statement.");
        }
        if( token.type != Sym.AS ) {
            error("\'as\' expected in foreach statement.");
        }
        nextToken();
        if( token.type != Sym.VARIDENT ) {
            error("Variable name expected in foreach statement.");
        }
        VarExpr varIt = new VarExpr((String)(token.obj));
        nextToken();
        if( token.type != Sym.RIGHTPAR )
            error("\')\' expected in foreach statement.");
        nextToken();
        symTab.addNewScope(); // for semantic analysis
        Statement st = statement();
        symTab.removeLastScope(); // for semantic analysis
        return new ForEachStatement(varIt, (VarExpr)varArray , st);       
    }
    
    
    public Statement forStatement() {
        nextToken(); // eat for
        if( token.type != Sym.LEFTPAR )
            error("\'(\' expected in for statement.");
        nextToken(); // eat (
        Expr init = expr();
        if( token.type != Sym.SEMICOLON ) 
            error("\';\' expected in for statement.");
        nextToken();
        Expr test = expr();
        if( token.type != Sym.SEMICOLON ) 
            error("\';\' expected in for statement.");
        nextToken();
        Expr update = expr();
        if( token.type != Sym.RIGHTPAR ) 
            error("\')\' expected in for statement.");
        nextToken();
        symTab.addNewScope(); // for semantic analysis
        Statement st = statement();
        symTab.removeLastScope(); // for semantic analysis
        return new ForStatement( st , init , test , update );    
    }    
 
public Statement functionDeclarStatement() {
    // eat function
    nextToken();
    String fname;
    if( token.type != Sym.FUNCTIONIDENT )
        error("Function name expected.");
    fname = (String)token.obj;
    nextToken();
    if( token.type != Sym.LEFTPAR )
        error("\'(\' expected in function declaration.");
    nextToken();
    Vector list = paramList();
    if( token.type != Sym.RIGHTPAR )
        error("\')\' expected in function declaration.");
    nextToken();    
    // insert in the symTab earlier, to allow recursion
    symTab.putInLastScope( fname , new FunctionDeclarStatement(fname , null, list) );
    CompositeStatement ce = (CompositeStatement)compositeStatement();
    FunctionDeclarStatement fd = new FunctionDeclarStatement( fname , ce , list );
    // for semantic analysis
    symTab.putInLastScope( fname , fd );
    return fd;
}

public Statement ifStatement() {
    // eat if
    nextToken();
    if( token.type != Sym.LEFTPAR ) 
        error("\'(\' expected in if statement.");
    nextToken();
    Expr test = expr();
    if( token.type != Sym.RIGHTPAR ) 
        error("\')\' expected in if statement.");
    nextToken();    
    Statement thenPart = null , elsePart = null;
    symTab.addNewScope(); // for semantic analysis
    thenPart = statement();
    symTab.removeLastScope(); // for semantic analysis
    if( token.type == Sym.ELSE ) {
        nextToken();
        symTab.addNewScope(); // for semantic analysis
        elsePart = statement();
        symTab.removeLastScope(); // for semantic analysis
    }
    return new IfStatement(test , thenPart , elsePart );    
}

public Statement switchStatement() {
    StatementList stlist = new StatementList();
    LinkedHashMap map = new LinkedHashMap();
    // eat switch
    nextToken();
    Expr test;
    if( token.type != Sym.LEFTPAR )
        error("\'(\' expected in switch statement.");
    nextToken();
    test = expr();
    if( token.type != Sym.RIGHTPAR )
        error("\')\' expected in switch statement.");
    nextToken();    
    if( token.type != Sym.LEFTBRACE )
        error("\'{\' expected in switch statement.");
    nextToken();       
    symTab.addNewScope(); // for semantic analysis
    while( token.type == Sym.CASE  ) {
        nextToken(); // eat case
        Expr e = expr();
        if( token.type != Sym.COLON ) 
            error("\':\' expected after case clausule.");
        nextToken();
        map.put(e , new Integer(stlist.list.size()));
        StatementList st = (StatementList)statementList();
        stlist.list.addAll(st.list);
    }
    if( token.type == Sym.DEFAULT ) {
        nextToken();
        if( token.type != Sym.COLON )
            error("\':\' expected after default clausule.");
        nextToken();
        map.put(test , new Integer(stlist.list.size()));
        StatementList st = (StatementList)statementList();
        stlist.list.addAll(st.list);        
    }
    if( token.type != Sym.RIGHTBRACE )
        error("\'}\' expected in switch statement.");
    nextToken();  
    symTab.removeLastScope(); // for semantic analysis
    return new SwitchStatement( test , map , stlist );
}


public Statement printStatement() {
    // eat print
    nextToken();
    return new PrintStatement( expr() );    
}

public Statement printLineStatement() {
    // eat print
    nextToken();
    return new PrintLineStatement( expr() );    
}

public Statement returnStatement() {
    // eat return
    nextToken();
    return new ReturnStatement( expr() );    
}


public Statement whileStatement() {
    // eat while
    nextToken();
    if( token.type != Sym.LEFTPAR ) 
        error("\'(\' expect in while statement.");
    nextToken();
    Expr test = expr();
    if( token.type != Sym.RIGHTPAR ) 
        error("\')\' expect in while statement.");
    nextToken();    
    symTab.addNewScope(); // for semantic analysis
    Statement st = statement();
    symTab.removeLastScope(); // for semantic analysis
    return new WhileStatement( test , st );  
}


public Statement doWhileStatement() {
    // eat do
    nextToken();
    symTab.addNewScope(); // for semantic analysis
    Statement st = statement();
    symTab.removeLastScope(); // for semantic analysis
    if( token.type != Sym.WHILE ) 
        error("\'while\' expect in do-while statement.");    
    nextToken();
    if( token.type != Sym.LEFTPAR ) 
        error("\'(\' expect after while.");
    nextToken();
    Expr test = expr();
    if( token.type != Sym.RIGHTPAR ) 
        error("\')\' expect in do-while statement.");
    nextToken();    

    return new DoWhileStatement( test , st );  
}


public Vector exprList() {
    Vector v = new Vector();
    if( startsExpr(token.type) ) {
        v.add( expr() );
    }
    else return v;
    while( token.type == Sym.COMMA ) {
        nextToken();
        if( ! startsExpr(token.type) ) 
            error("Expression expected after \',\'");
        v.add( expr() );
    }
    return v;
}


public Vector paramList() {
    Vector v = new Vector();
    if( token.type == Sym.VARIDENT ) {
        v.add( (String)token.obj );
        nextToken();
    }
    else return v;
    while( token.type == Sym.COMMA ) {
        nextToken();
        if( token.type != Sym.VARIDENT )
            error("Parameter expected after \',\'");
        v.add( (String)token.obj );
        nextToken();
    }
    return v;
}

public Statement readStatement() {
    nextToken();
    if( token.type != Sym.LEFTPAR )
        error("\'(\' expected after read statement.");    
    nextToken();
    Vector v = paramList();
    if( token.type != Sym.RIGHTPAR )
        error("\')\' expected.");    
    nextToken();   
    return new ReadStatement(v);
}

public Statement readLineStatement() {
    nextToken();
    if( token.type != Sym.LEFTPAR )
        error("\'(\' expected after read statement.");    
    nextToken();
    Vector v = paramList();
    if( token.type != Sym.RIGHTPAR )
        error("\')\' expected.");    
    nextToken();   
    return new ReadLineStatement(v);
}

public Expr expr() {
    Expr left = orExpr();
    Expr right;
    if( token.type == Sym.ASSIGN  ) {
        if(! (left instanceof VarExpr) )
            error("Variable or array expected on left side.");
        nextToken();
        // array init
        if( token.type == Sym.ARRAY ) {
            // array init
            nextToken(); // eat "array"
            if( token.type != Sym.LEFTPAR )
                error("\'(\' expected in array initialization expression.");
            nextToken();
            Vector list = exprList();
            if( token.type != Sym.RIGHTPAR )
                error("\')\' expected in array initialization expression.");
            nextToken();   
            ArrayInitExpr init  =  new ArrayInitExpr( list );
            return (Expr)(new AssignmentStatement( (VarExpr)left , init ));            
        }
        else { // common assignment
            right = expr();
            return (Expr)(new AssignmentStatement( (VarExpr)left , right ));             
        }
     }
    else 
        if ( token.type >= Sym.PLUSASSIGN && token.type <= Sym.CONCATASSIGN) {
                if(! (left instanceof VarExpr) )
                    error("Variable or array expected on left side.");   
                    int op = 0;
                    switch(token.type) {
                        case Sym.PLUSASSIGN: op = Sym.PLUS;
                                             break;
                        case Sym.MINUSASSIGN: op = Sym.MINUS;
                                             break;
                        case Sym.MULTASSIGN: op = Sym.MULT;
                                             break;
                        case Sym.DIVASSIGN: op = Sym.DIV;
                                             break;
                        case Sym.REMAINDERASSIGN: op = Sym.REMAINDER;
                                             break;
                        case Sym.CONCATASSIGN: op = Sym.CONCAT;
                                             break;
                                             
                    }
                    nextToken();   // eat += op
                    right = expr();
                    return (Expr)(new AssignmentStatement( (VarExpr)left , new CompositeExpr(left,op,right) ));                     
        }
    
    return left;    
}

public Expr orExpr() {
    Expr left = andExpr();
    while( token.type == Sym.OR ) {
        nextToken();
        Expr right = andExpr();
        left = new CompositeExpr( left , Sym.OR , right );
    }
    return left;
}


public Expr andExpr() {
    Expr left = relExpr();
    while( token.type == Sym.AND ) {
        nextToken();
        Expr right = relExpr();
        left = new CompositeExpr( left , Sym.AND , right );
    }
    return left;
}

public Expr relExpr() {
    Expr left = addExpr();
    if( isRelation() ) {
        int op = token.type;
        nextToken();
        Expr right = addExpr();
        left = new CompositeExpr( left , op , right );
    }
    return left;    
}

public Expr addExpr() {
    Expr left = multExpr();
    while( isAddOp() ) {
        int op = token.type;
        nextToken();
        Expr right = multExpr();
        left = new CompositeExpr( left , op , right );
    }
    return left;
    
}


public Expr multExpr() {
    Expr left = unaryExpr();
    while( isMultOp() ) {
        int op = token.type;
        nextToken();
        Expr right = unaryExpr();
        left = new CompositeExpr(left , op , right);
    }
    return left;    
}

public Expr unaryExpr() {
    Expr left , right;
    switch( token.type ) {
        case Sym.NOT:
            nextToken();
            right = postFixExpr();
            return new UnaryExpr( right , Sym.NOT );
        case Sym.PLUS:
            nextToken();
            right = postFixExpr();
            return new UnaryExpr( right , Sym.PLUS );   
        case Sym.MINUS:
            nextToken();
            right = postFixExpr();
            return new UnaryExpr( right , Sym.MINUS );      
        case Sym.INCREMENT:
        case Sym.DECREMENT:
            int op = token.type;
            nextToken();
            right = postFixExpr();
            if(! (right instanceof VarExpr) ) {
                error("Operators \'++\' and \'--\' can only be applied to variables.");                
            }
            left = (Expr)(new PreIncDec( (VarExpr)right , op ));    
            return left;
        default:
            return postFixExpr(); 
    }
    
}


public Expr postFixExpr() {
    Expr left = primaryExpr();
    // pos inc and dec opers
    if( token.type == Sym.INCREMENT || token.type == Sym.DECREMENT ) {
        if(! (left instanceof VarExpr)  ) {
            error("Operators \'++\' and \'--\' can only be applied to variables.");
        }
        int op = token.type;
        nextToken();
        return (Expr)(new PosIncDec((VarExpr)left , op));
    }
    // array access oper
    while( token.type == Sym.LEFTBRACK ) {
        nextToken();
        Expr ind = expr();
        if( token.type != Sym.RIGHTBRACK )
            error("\']\' expected.");
        nextToken();
        left = new ArrayAccessVarExpr( (VarExpr)left , ind );
    }
    return left;    
}

public Expr primaryExpr() {
    Expr e = null;
    switch( token.type ) {
        case Sym.VARIDENT:
            e = new VarExpr( (String)token.obj );
            nextToken();
            break;
        case Sym.FALSE:
        case Sym.NULL:
            e = NullExpr.NULL;
            nextToken();
            break;
        case Sym.TRUE:
            e = TrueExpr.TRUE;
            nextToken();
            break;
        case Sym.LITERALINTEGER:
            e = new NumberExpr( (Integer)token.obj );
            nextToken();
            break;
        case Sym.LITERALINTERPOLEDSTRING:
            e = new InterpoledStringExpr( (String)token.obj );
            nextToken();
            break;
        case Sym.LITERALSIMPLESTRING:
            e = new SimpleStringExpr( (String)token.obj );
            nextToken();
            break;    
        case Sym.FUNCTIONIDENT:
             e = functionCallExpr();
             break;
        case Sym.LEFTPAR:
            nextToken();
            e = expr();
            if( token.type != Sym.RIGHTPAR )
                error("\')\' expected.");
            nextToken();
            e = new ParenthesisExpr( e );
            break;  
        default:
            error("Expression expected.");
            break;
    }
    return e;    
}



public Expr functionCallExpr() {
    String fname = (String)token.obj;
    nextToken();
    if( token.type != Sym.LEFTPAR ) {
        error("\'(\' expected calling function "+ fname + ".");
    }
    nextToken();
    Vector args = exprList();
    if( token.type != Sym.RIGHTPAR ) {
        error("\')\' expected calling function "+ fname + ".");
    }
    nextToken();
    if ( symTab.get(fname ) == null ) 
        error("Function " + fname + " was not declared or is not reachable.");
    FunctionDeclarStatement fd = (FunctionDeclarStatement)symTab.get(fname);
    if( fd.getParamNumber() != args.size() ) {
        error("Wrong number of arguments in function call. Function " + 
                fname + " expects " + fd.getParamNumber() + " arguments.");
    }
    return new FunctionCall( fname , args );
}


public boolean isRelation() {
    switch( token.type ) {
        case Sym.EQ: 
        case Sym.NEQ:
        case Sym.LT:
        case Sym.LE:
        case Sym.GT:
        case Sym.GE:
            return true;
    }
    return false;
}


public boolean isAddOp() {
    switch( token.type ) {
        case Sym.PLUS: 
        case Sym.MINUS:
        case Sym.CONCAT:
            return true;
    }
    return false;
}


public boolean isMultOp() {
    switch( token.type ) {
        case Sym.DIV: 
        case Sym.MULT:
        case Sym.REMAINDER:
            return true;
    }
    return false;
}


}
