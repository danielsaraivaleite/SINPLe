package sinple.ast;
import  sinple.MemoryTable;
import  java.util.*;

public class VarExpr implements Expr {
    
    protected String name;
    
    /** Creates a new instance of VarExpr */
    public VarExpr( String name ) {
        this.name = name;
    }
    
    public Object evaluate( MemoryTable memory ) {
        Hashtable ht = memory.containsSymbol( name );
        if(ht != null ) return ht.get( name );
        memory.put( name , NullExpr.NULL.evaluateAsInt( memory ) );
        return NullExpr.NULL.evaluateAsInt( memory ) ;
    }
    
    public Integer evaluateAsInt( MemoryTable memory ) {
        Object obj = evaluate( memory );
        if( obj instanceof Integer ) return (Integer)obj;
        if( obj instanceof String ) {
            try {
                Integer i = new Integer((String)obj);
                return i;
            }
            catch (Exception e) {
                return NullExpr.NULL.evaluateAsInt( memory );
            }
        }
        return  NullExpr.NULL.evaluateAsInt( memory );
    }
    
    public String evaluateAsString( MemoryTable memory ) {
        Object obj = evaluate(memory);
        return obj.toString();        
    }
    
    public Object assign( MemoryTable memory , Expr exp ) {
        Object result;
        Hashtable ht = memory.containsSymbol( name );
        if( ht != null )  ht.put(name , result = exp.evaluate(memory));
        else memory.put(name , result = exp.evaluate( memory ) );
        return result;       
    }
    
    public Object assign( MemoryTable memory , Object obj ) {
        Hashtable ht = memory.containsSymbol( name );
        if( ht != null )  ht.put(name , obj );
        else memory.put(name , obj );
        return obj;     
    }    
    
    public String getVarName() {
        return name;
    }
    
}
