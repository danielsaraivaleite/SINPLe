package sinple.ast;
import  sinple.MemoryTable;

public class Program implements Statement {
    
    StatementList list;
    
    /** Creates a new instance of Program */
    public Program( StatementList list ) {
        this.list = list;
    }
    
    public void interpret(MemoryTable mem) {
        list.interpret( mem );        
    }
    
}
