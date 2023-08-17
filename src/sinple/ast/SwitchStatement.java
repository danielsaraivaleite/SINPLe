package sinple.ast;
import  sinple.MemoryTable;
import  java.util.*;

public class SwitchStatement implements Statement {
    
    private Expr testExpr;
    private LinkedHashMap cases;
    private StatementList statements;
    
    /** Creates a new instance of SwitchStatement */
    public SwitchStatement( Expr testExpr , LinkedHashMap cases , StatementList statements ) {
        this.testExpr = testExpr;
        this.cases = cases;
        this.statements = statements;
    }
    
    public void interpret( MemoryTable memory ) {
        Object target = testExpr.evaluate( memory );
        boolean finished = false;
        Iterator it = cases.keySet().iterator();
        while ( it.hasNext() && !finished ) {
            Expr caseExpr = (Expr)it.next();
            if( testExpr == caseExpr || target.equals( caseExpr.evaluate( memory ) )  ) {
                finished = true;
                int begin = ((Integer)cases.get( caseExpr )).intValue();
                StatementList exec = new StatementList();
                exec.list.addAll( statements.list.subList( begin , statements.list.size() ) );
                exec.interpret( memory );
            }
        }
    }
    
}
