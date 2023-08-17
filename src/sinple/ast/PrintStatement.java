package sinple.ast;
import  sinple.MemoryTable;
import  java.util.*;

public class PrintStatement implements Statement {
    
    private Expr expr;
    
    /** Creates a new instance of PrintStatement */
    public PrintStatement(Expr e) {
        expr = e;
    }
    
    public void interpret( MemoryTable  memory ) {
        System.out.print( expr.evaluateAsString(memory) );
    }
    
}
