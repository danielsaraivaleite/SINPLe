package sinple.ast;
import  sinple.MemoryTable;
import  java.util.*;

public class StatementList implements Statement {
    
    public Vector list; // must be public
    
    /** Creates a new instance of StatementList */
    public StatementList() {
        list = new Vector();
    }
    
    public StatementList( Vector vec ) {
        list = vec;
    }
    
    public void interpret( MemoryTable memory ) {
        Statement st;
        memory.removeFromGlobal("#return");   
        memory.removeFromGlobal("#break");   
        memory.removeFromGlobal("#continue");   
        for(int i = 0 ; i < list.size() ; i++ ) {
            st = ( (Statement)list.get(i) );
            if( memory.containsInGlobal( "#return" )
                || memory.containsInGlobal("#continue")
                || memory.containsInGlobal("#break")) return; // skip
            st.interpret( memory );
        }        
    }
    
    public void addStatement(Statement st) {
        list.add(st);
    }
    
}
