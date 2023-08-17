package sinple.ast;
import  sinple.MemoryTable;


public interface Expr {
    
    public Object evaluate( MemoryTable memory );
    
    public Integer evaluateAsInt( MemoryTable memory );
    
    public String evaluateAsString( MemoryTable memory );
    
    
}
