package sinple.ast;
import  sinple.Sym;
import sinple.MemoryTable;
import  java.util.*;

public class PosIncDec implements Statement , Expr {
    
    int op;
    VarExpr var;
    
    /** Creates a new instance of PreIncDec */
    public PosIncDec( VarExpr var , int op ) {
        this.op = op;
        this.var = var;
    }
    
    public void interpret( MemoryTable memory ) {
        evaluate( memory );
    }
    
    public Object evaluate( MemoryTable memory ) {
        Integer newValue = NullExpr.NULL.evaluateAsInt( memory );
        Integer oldValue =  var.evaluateAsInt(memory);
        switch( op ) {
            case Sym.INCREMENT:
                newValue = new Integer( oldValue.intValue() + 1 );                
                break;
            case Sym.DECREMENT:
                newValue = new Integer( oldValue.intValue() - 1 );               
                break;
        }
        var.assign( memory , new NumberExpr(newValue) );
        return oldValue;
    }
    
    public Integer evaluateAsInt( MemoryTable memory ) {
        return (Integer)evaluate(memory);
    }
    
    public String evaluateAsString( MemoryTable memory ) {
        return evaluateAsInt(memory).toString();
    }
    
}
