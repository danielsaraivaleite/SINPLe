package sinple.ast;
import  sinple.MemoryTable;


public class CompositeStatement implements Statement {
    
    StatementList list;
    
    /** Creates a new instance of CompositeStatement */
    public CompositeStatement( StatementList list ) {
        this.list = list;
    }
    
    public void interpret( MemoryTable memory ) {
        list.interpret( new MemoryTable( memory ) );        
    }
    
}
