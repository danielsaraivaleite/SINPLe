package sinple.ast;
import  sinple.MemoryTable;
import  java.util.*;

public class TrueExpr implements Expr {
    
    private static   String    trueString = "true";
    private static   Integer   trueInt = new Integer(1);
    public static   TrueExpr   TRUE    = new TrueExpr();
    
    /** Creates a new instance of NullExpr */
    private TrueExpr() {
    }
    
    public Object evaluate( MemoryTable memory ) {
        return trueInt;
    }
    
    public Integer evaluateAsInt( MemoryTable memory ) {
        return trueInt;
    }
    
    public String evaluateAsString( MemoryTable memory ) {
        return trueString;
    }
    
}
