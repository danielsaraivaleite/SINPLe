package sinple.ast;
import  sinple.MemoryTable;

public class ParenthesisExpr implements Expr {
    
    Expr expr;
    
    /** Creates a new instance of ParenthesisExpr */
    public ParenthesisExpr( Expr expr ) {
        this.expr = expr;
    }

    public Object evaluate( MemoryTable memory ) {
        return expr.evaluate( memory );
    }
    
    public Integer evaluateAsInt( MemoryTable memory ) {
        return expr.evaluateAsInt( memory );
    }
    
    public String evaluateAsString( MemoryTable memory ) {
        return expr.evaluateAsString( memory );
    }    
    
}
