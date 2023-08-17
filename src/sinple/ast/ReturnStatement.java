package sinple.ast;
import  sinple.MemoryTable;
import  java.util.*;


public class ReturnStatement implements Statement {
    
    private Expr expr;
    
    /** Creates a new instance of ReturnStatement */
    public ReturnStatement(Expr expr) {
        this.expr = expr;
    }
    
    public void interpret( MemoryTable memory ) {
        memory.putInGlobal("#return" , expr.evaluate( memory ) );
    }
    
}
