package sinple.ast;
import  sinple.MemoryTable;
import  java.util.*;
import  sinple.Error;


public class ArrayAccessVarExpr extends VarExpr {
    
    protected Expr   index;
    protected VarExpr var;
    
    /** Creates a new instance of ArrayAccessVarExpr */
    public ArrayAccessVarExpr(VarExpr var , Expr index ) {
        super( var.getVarName() );
        this.index = index;
        this.var = var;
    }
    
    public Object evaluate( MemoryTable memory ) {
        int ind = index.evaluateAsInt(memory).intValue();
        Object obj = var.evaluate( memory );
         if( obj instanceof LinkedList ) {
                if(ind >= 0 && ind < ((LinkedList)obj).size() ) {
                    return ((LinkedList)obj).get(ind);
                }    
          }
       if( ind < 0 ) {
              Error.signalRuntimeError("Runtime Exception:\nNegative array index Exception in " + name + "access.");
       }
        return NullExpr.NULL.evaluateAsString( memory );
    }
    
    public Integer evaluateAsInt( MemoryTable memory ) {
        Object obj = evaluate( memory );
        if(obj instanceof Integer ) return (Integer)obj;
        if( obj instanceof String ) {
            try {
                Integer i = new Integer((String)obj);
                return i;
            }
            catch (Exception e) {
                return NullExpr.NULL.evaluateAsInt( memory );
            }
        }
        return NullExpr.NULL.evaluateAsInt( memory );
    }
    
    public String evaluateAsString( MemoryTable memory ) {
        Object obj = evaluate(memory);
        if( obj instanceof NullExpr ) {
            return NullExpr.NULL.evaluateAsString( memory );
        }
        return evaluate(memory).toString();     
    }
    
    public Object assign( MemoryTable memory , Expr exp ) {
        Object result = exp.evaluate( memory );
        LinkedList list;
        Object obj = var.evaluate( memory );
        if(  (obj instanceof LinkedList)   )  { 
            list = (LinkedList)obj;
        }
        else {
            list = new LinkedList();
            var.assign( memory , list );
        }
        int ind = index.evaluateAsInt(memory).intValue();
        if( ind < 0 ) {
            Error.signalRuntimeError("Runtime Exception:\nNegative array index Exception in " + name + "access.");
            return NullExpr.NULL.evaluateAsString( memory );
        }
        while( ( list.size() - 1 ) < ind ) {
            list.add(NullExpr.NULL.evaluateAsString( memory ));
        }
        list.set( ind , result );
        
        return result;       
    }    

     public Object assign( MemoryTable memory , Object value ) {
        Object result = value;
        LinkedList list;
        Object obj = var.evaluate( memory );
        if( (obj instanceof LinkedList)   )  { 
            list = (LinkedList)obj;
        }
        else {
            list = new LinkedList();
            var.assign( memory , list );
        }
        int ind = index.evaluateAsInt(memory).intValue();
        if( ind < 0 ) {
            Error.signalRuntimeError("Runtime Exception:\nNegative array index Exception in " + name + "access.");
            return NullExpr.NULL.evaluateAsString( memory );
        }
        while( (list.size() - 1) < ind ) {
            list.add(NullExpr.NULL.evaluateAsString( memory ));
        }
        list.set( ind , result );
        
        return result;       
    }    
    
}
