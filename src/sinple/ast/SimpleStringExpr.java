package sinple.ast;
import  sinple.MemoryTable;


public class SimpleStringExpr implements Expr {
    
    private String string;
    /** Creates a new instance of SimpleStringExpr */
    public SimpleStringExpr(String s) {
        this.string = s;
    }
    
    public Object evaluate( MemoryTable memory ) {
        return string;
    }
    
    public Integer evaluateAsInt( MemoryTable memory ) {
        try {
            Integer i = new Integer(string);
            return i;
        }
        catch(Exception e) {
            return NullExpr.NULL.evaluateAsInt( memory  );
        }
    }
    
    public String evaluateAsString( MemoryTable memory ) {
        return string;
    }
    
}
