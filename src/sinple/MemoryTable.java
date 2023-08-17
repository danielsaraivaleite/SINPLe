package sinple;
import java.util.*;


public class MemoryTable {
    
    private LinkedList scopes = new LinkedList();
    
    /** Creates a new instance of MemoryTable */
    public MemoryTable() {
        scopes.add(new Hashtable());
    }
    
    public MemoryTable(MemoryTable superiorTable ) {
        scopes.addAll(superiorTable.scopes);
        scopes.add(new Hashtable());
    }
    
    public void put( String name, Object obj ) {
        ((Hashtable)scopes.getLast()).put(name , obj);
    }
    
    public void putInGlobal(String name , Object obj ) {
        ((Hashtable)scopes.getFirst()).put(name , obj);  
    }
    
    public Hashtable containsSymbol( String name ) {
        for(int i = scopes.size() - 1 ; i>= 0 ; i--) {
            if(((Hashtable)scopes.get(i)).containsKey(name)) return (Hashtable)scopes.get(i);
        }
        return null;
    }

    public Object retrievesSymbol( String name ) {
        for(int i = scopes.size() - 1 ; i>= 0 ; i--) {
            if(((Hashtable)scopes.get(i)).containsKey(name)) 
                return ((Hashtable)scopes.get(i)).get( name );
        }
        return null;
    }    
    
    public void removeFromGlobal(String key ) {
        ((Hashtable)scopes.getFirst()).remove(key);        
    }
    
    public boolean containsInGlobal( String key ) {
        return ((Hashtable)scopes.getFirst()).containsKey( key );
    }
    
    public Object retrieveFromGlobal( String key ) {
        return ((Hashtable)scopes.getFirst()).get( key );
    }    
    
    public Hashtable getAllVars() {
        Hashtable ht = new Hashtable();   
        for( int i = 0 ; i < scopes.size() ; i++ ) {
            Hashtable table = (Hashtable)scopes.get( i );
            Enumeration it = table.keys();
            while( it.hasMoreElements() ) {
                String name = (String)it.nextElement();
                if( name.startsWith("$") )  {
                    ht.put("\\"+name , table.get(name).toString());
                }
            }
        }
        return ht;        
    }
    
    
    
}
