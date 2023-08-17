package sinple.ast;
import java.util.*;
import sinple.MemoryTable;
import sinple.Error;



public class FunctionDeclarStatement  implements Statement {
    
    private Vector paramList;
    private CompositeStatement actions;
    private String name;
    
    /** Creates a new instance of FunctionDeclarStatement */
    public FunctionDeclarStatement( String name , CompositeStatement actions , Vector paramList ) {
        this.name = name;
        this.actions = actions;
        this.paramList = paramList;
    }
    
   
    public Object call( Vector exprList ,  MemoryTable memory ) {
        memory.removeFromGlobal("#return");
        MemoryTable memory2 = new MemoryTable( memory );
        if( exprList.size() != paramList.size() ) {
            // should never happen, since the parser checks
            Error.signalRuntimeError( "Wrong number of arguments calling function " + name );
            return NullExpr.NULL.evaluateAsInt( memory );
        }
        Expr arg;
        for( int i=0 ; i < exprList.size() ; i++ ) {
            arg = (Expr)exprList.elementAt(i);
            Object obj = arg.evaluate(memory);
            memory2.put( (String)paramList.elementAt(i) , obj );
        }
        actions.interpret( memory2 );
        if( memory2.containsInGlobal( "#return" ) ) {
            Object result = memory2.retrieveFromGlobal( "#return" );
            memory2.removeFromGlobal( "#return" );
            return result;
        }   
        return NullExpr.NULL.evaluateAsInt( memory );       
    }
    
    public Vector getParams() {
        return paramList;
    }
    
    public int getParamNumber() {
        return paramList.size();
    }    
    
    public void interpret(  MemoryTable memory  ) {
        memory.put(name , this);
    }
    
}
