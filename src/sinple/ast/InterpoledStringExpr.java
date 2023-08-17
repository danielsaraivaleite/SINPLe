/*
 * Needs improv.
 */

package sinple.ast;
import  sinple.MemoryTable;
import  java.util.*;


public class InterpoledStringExpr implements Expr {
    
    private String string;
    
    /** Creates a new instance of InterpoledStringExpr */
    public InterpoledStringExpr( String s ) {
        this.string = s;
    }
    
    public Object evaluate( MemoryTable memory ) {
        return evaluateAsString(memory);
    }
    
    public Integer evaluateAsInt( MemoryTable memory ) {
        try {
            Integer i = new Integer( evaluateAsString(memory) );
            return i;
        }
        catch(Exception e) {
            return NullExpr.NULL.evaluateAsInt( memory  );
        }
    }
    
    public String evaluateAsString( MemoryTable memory ) {
        Hashtable ht = memory.getAllVars();
        //System.out.println("table "+ht);
        String varName , value, stringAux=string;
        Enumeration en = ht.keys();
        while( en.hasMoreElements() ) {
            varName = (String)en.nextElement();
            value   = (String)ht.get(varName);
            stringAux = stringAux.replaceAll( varName , value );                        
        }
        return stringAux;        
    }
    
}
