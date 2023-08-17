package sinple.ast;
import  sinple.MemoryTable;
import  java.util.*;

public class BreakStatement implements Statement {
    
    /** Creates a new instance of BreakStatement */
    public BreakStatement() {
    }
    
    public void interpret(MemoryTable memory) {
        memory.putInGlobal("#break","");
    }
    
}
