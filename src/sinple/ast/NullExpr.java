package sinple.ast;
import  sinple.MemoryTable;
import  java.util.*;

public class NullExpr implements Expr {
    
    private static   String    nullString = "";
    private static   Integer   nullInt = new Integer(0);
    public static   NullExpr   NULL    = new NullExpr();
    
    /** Creates a new instance of NullExpr */
    private NullExpr() {
    }
    
    public Object evaluate( MemoryTable memory ) {
        return nullInt;
    }
    
    public Integer evaluateAsInt( MemoryTable memory ) {
        return nullInt;
    }
    
    public String evaluateAsString( MemoryTable memory ) {
        return nullString;
    }
    
}
