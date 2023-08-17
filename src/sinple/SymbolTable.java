package sinple;

import java.util.*;


public class SymbolTable {
    
    private Vector scopes;    
    
    /** Creates a new instance of SymbolTable */
    public SymbolTable() {
        scopes = new Vector();
    }
    
    
    /** Starts a new scope */
    public void addNewScope() {
        scopes.addElement(new Hashtable());
    }
    
    
    /** Remove the last scope hashtable*/
    public void removeLastScope() {
        if(scopes.size() > 0) {
           scopes.remove( scopes.size() - 1);
        }
    }
    
    /** Remove all symbols from all tables */    
    public void removeAll() {
        scopes.clear();
    }
    
    
    /** Put a symbol in the current scope */    
    public void putInLastScope(String name, Object obj) {
        // Get a reference from the top hashtable
        Hashtable ht = (Hashtable)scopes.lastElement();
        ht.put(name, obj);
    }
    
    
    /** Retrieve a symbol from the neerest scope  */    
    public Object get(String name) {
        Hashtable ht;   
        for(int i = scopes.size() - 1; i >= 0; i--) {
            ht = (Hashtable)scopes.elementAt(i);
            Object v = ht.get(name);
            if( v != null ) return v;
        } 
        return null;
    }
    
    
    /** Verify if a symbol already exists in the current scope */    
    public boolean existsInLastScope(String name) {
        Hashtable ht = (Hashtable)scopes.lastElement();
        if( ht.get(name) != null) return true;
        return false;        
    }
          
}
