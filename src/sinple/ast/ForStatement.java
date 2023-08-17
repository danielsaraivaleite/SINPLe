package sinple.ast;
import  sinple.MemoryTable;


public class ForStatement implements Statement {
    
    private Statement statement;
    private Expr startExpr , testExpr , incExpr;
    
    /** Creates a new instance of ForStatement */
    public ForStatement( Statement st , Expr startExpr , Expr testExpr , Expr incExpr ) {
        this.statement = st;
        this.startExpr = startExpr;
        this.testExpr  = testExpr;
        this.incExpr   = incExpr;  
    }
    
    public void interpret( MemoryTable memory ) {
        memory.removeFromGlobal("#return"); 
        memory.removeFromGlobal("#continue");
        memory.removeFromGlobal("#break"); 
        MemoryTable memAux = new MemoryTable( memory );
        MemoryTable memLoop;
        startExpr.evaluate( memAux ); // initial assignment
        while( true ) {
            if( ( testExpr.evaluateAsInt( (memLoop = new MemoryTable(memAux)) ).intValue() != 0 )
                    && (!memory.containsInGlobal("#return")) 
                    && (!memory.containsInGlobal("#break"))          ) {
                memory.removeFromGlobal("#continue");
                statement.interpret( memLoop );
                incExpr.evaluate( memLoop );
            }
            else {
                break;
            }
        }
        memory.removeFromGlobal("#continue");
        memory.removeFromGlobal("#break");  
    }
    
}
