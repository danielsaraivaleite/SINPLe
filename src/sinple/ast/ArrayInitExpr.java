package sinple.ast;
import  sinple.MemoryTable;
import  java.util.*;
import  sinple.Error;

public class ArrayInitExpr implements Expr {
    
    private Vector exprList;
    
    /** Creates a new instance of ArrayInitExpr */
    public ArrayInitExpr(Vector list) {
        exprList = list;
    }
    
    public Object evaluate( MemoryTable memory ) {
        LinkedList list = new LinkedList();
        for(int i = 0 ; i < exprList.size() ; i++ ) {
            list.add(  ((Expr)exprList.elementAt(i)).evaluate( memory ) );
        }
        return list;
    }
    
    public Integer evaluateAsInt( MemoryTable memory ) {
        return NullExpr.NULL.evaluateAsInt( memory );        
    }
    
    public String evaluateAsString( MemoryTable memory ) {
        return evaluate(memory).toString();        
    }
    
}
