package sinple.ast;
import  sinple.MemoryTable;
import  java.util.*;

public class ContinueStatement implements Statement {
    
    /** Creates a new instance of BreakStatement */
    public ContinueStatement() {
    }
    
    public void interpret( MemoryTable memory ) {
        memory.putInGlobal("#continue","");
    }
    
}
