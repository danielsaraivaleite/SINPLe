package sinple.ast;
import  sinple.MemoryTable;
import  java.util.*;

public class ForEachStatement implements Statement {
    
    private VarExpr var , array;
    private Statement statement;
    
    /** Creates a new instance of ForEachStatement */
    public ForEachStatement( VarExpr var , VarExpr array , Statement st ) {
        this.var = var;
        this.array = array;
        this.statement = st;
    }
    
    public void interpret( MemoryTable memory ) {
        memory.removeFromGlobal("#return"); 
        memory.removeFromGlobal("#continue");
        memory.removeFromGlobal("#break"); 
        LinkedList list;
        Object obj = array.evaluate( memory );
        if( ! ( obj instanceof LinkedList ) ) {
            list = new LinkedList();
            list.add( obj );
        }
        else {
            list = (LinkedList)obj;
        }
        Iterator it = list.iterator();
        MemoryTable memoryAux = new MemoryTable( memory );
        memoryAux.put( var.getVarName() , NullExpr.NULL.evaluateAsInt( memory ) );
        while( it.hasNext() && (!memory.containsInGlobal("#return"))
               &&  (!memory.containsInGlobal("#break"))   ) {
            var.assign( memoryAux , it.next() );
            memory.removeFromGlobal("#continue");
            statement.interpret(  memoryAux  );
        }
        memory.removeFromGlobal("#continue");
        memory.removeFromGlobal("#break"); 
        
    }
    
}
