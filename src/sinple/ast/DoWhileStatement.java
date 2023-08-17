package sinple.ast;
import  sinple.MemoryTable;
 
public class DoWhileStatement implements Statement {
    
    Expr testExpr;
    Statement statement;
    
    /** Creates a new instance of WhileStatement */
    public DoWhileStatement( Expr testExpr , Statement statement ) {
        this.testExpr = testExpr;
        this.statement = statement;
    }
    
    public void interpret( MemoryTable memory ) {
        memory.removeFromGlobal("#return"); 
        memory.removeFromGlobal("#continue");
        memory.removeFromGlobal("#break");      
        
        do {
            memory.removeFromGlobal("#continue");
            statement.interpret( new MemoryTable( memory ) );
        }
        while(testExpr.evaluateAsInt(memory).intValue() != 0 && (!memory.containsInGlobal("#return")) 
              && (!memory.containsInGlobal("#break")) );
        
        memory.removeFromGlobal("#continue");
        memory.removeFromGlobal("#break");  
}
    
}

