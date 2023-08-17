package sinple.ast;
import sinple.MemoryTable;
import java.util.*;


public class FunctionCall implements Statement , Expr {
    
    private Vector exprList;
    private String functionName;
    
    /** Creates a new instance of FunctionCall */
    public FunctionCall( String functionName , Vector exprList ) {
        this.exprList = exprList;
        this.functionName = functionName;
    }
    
    public void interpret( MemoryTable memory ) {
        evaluate( memory );        
    }
    
    public Object evaluate( MemoryTable memory ) {
        Object obj;
        if ( (obj = memory.retrievesSymbol( functionName )) != null ) {
            if( obj instanceof FunctionDeclarStatement ) {
                return ((FunctionDeclarStatement)obj).call( exprList , memory );
            }
        }
        sinple.Error.signalRuntimeError( "Function " + functionName + " does not exist or is not reachable");
        return NullExpr.NULL.evaluateAsInt( memory );
    }
    
    public Integer evaluateAsInt( MemoryTable memory ) {
        Object res = evaluate( memory );
        if( res instanceof String ) {
            try {
                Integer i =  new Integer((String)res);
                return i;
            }
            catch(Exception e) {
                return NullExpr.NULL.evaluateAsInt( memory );                
            }            
        }
        if( res instanceof Integer ) return (Integer)res;
        return NullExpr.NULL.evaluateAsInt( memory );        
    }
            
    
    public String evaluateAsString( MemoryTable memory ) {
        Object res = evaluate( memory );
        if( res instanceof String ) return (String)res;
        if( res instanceof Integer ) return ((Integer)res).toString();
        return NullExpr.NULL.evaluateAsString( memory );  
    }
    
}
