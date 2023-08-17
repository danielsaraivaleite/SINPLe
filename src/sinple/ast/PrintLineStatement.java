package sinple.ast;
import  sinple.MemoryTable;
import  java.util.*;

public class PrintLineStatement implements Statement {
    
    private Expr expr;
    
    /** Creates a new instance of PrintStatement */
    public PrintLineStatement(Expr e) {
        expr = e;
    }
    
    public void interpret( MemoryTable  memory ) {
        System.out.println( expr.evaluateAsString(memory) );
    }
    
}
