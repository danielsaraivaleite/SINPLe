package sinple.ast;
import  sinple.MemoryTable;


public interface Statement {
    
    public void interpret(MemoryTable memory);
    
}
