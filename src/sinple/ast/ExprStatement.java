package sinple.ast;
import  sinple.MemoryTable;

public class ExprStatement implements Statement {
    
    private Expr exp;
    
    /** Creates a new instance of ExprStatement */
    public ExprStatement(Expr exp) {
        this.exp = exp;
    }
    
    public void interpret(MemoryTable memory ) {
        exp.evaluate( memory );
    }
    
}
