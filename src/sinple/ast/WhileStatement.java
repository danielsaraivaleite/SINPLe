package sinple.ast;
import  sinple.MemoryTable;
 
public class WhileStatement implements Statement {
    
    Expr testExpr;
    Statement statement;
    
    /** Creates a new instance of WhileStatement */
    public WhileStatement( Expr testExpr , Statement statement ) {
        this.testExpr = testExpr;
        this.statement = statement;
    }
    
    public void interpret( MemoryTable memory ) {
        memory.removeFromGlobal("#return"); 
        memory.removeFromGlobal("#continue");
        memory.removeFromGlobal("#break");        
        while(testExpr.evaluateAsInt(memory).intValue() != 0 && (!memory.containsInGlobal("#return")) 
              && (!memory.containsInGlobal("#break")) ) {
            memory.removeFromGlobal("#continue");
            statement.interpret(new MemoryTable(memory));
        }
        memory.removeFromGlobal("#continue");
        memory.removeFromGlobal("#break");  
}
    
}
