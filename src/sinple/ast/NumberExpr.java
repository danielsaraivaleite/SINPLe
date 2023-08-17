package sinple.ast;
import  sinple.MemoryTable;


public class NumberExpr  implements Expr {
   
    Integer integer;
    /** Creates a new instance of NumberExpr */
    public NumberExpr( Integer integer ) {
        this.integer = integer;
    }
    
    public Object evaluate( MemoryTable memory ) {
        return integer;
    }
    
    public Integer evaluateAsInt( MemoryTable memory ) {
        return integer;
    }
    
    public String evaluateAsString( MemoryTable memory ) {
        return integer.toString();
    }
    
}
