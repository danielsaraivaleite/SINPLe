package sinple.ast;
import  sinple.Sym;
import sinple.MemoryTable;
import  java.util.*;

public class PreIncDec implements Statement , Expr {
    
    int op;
    VarExpr var;
    
    /** Creates a new instance of PreIncDec */
    public PreIncDec(VarExpr var , int op) {
        this.op = op;
        this.var = var;
    }
    
    public void interpret( MemoryTable memory ) {
        evaluate(memory);
    }
    
    public Object evaluate( MemoryTable memory ) {
        Integer newValue = NullExpr.NULL.evaluateAsInt( memory );
        switch( op ) {
            case Sym.INCREMENT:
                newValue = new Integer( var.evaluateAsInt(memory).intValue() + 1 );                
                break;
            case Sym.DECREMENT:
                newValue = new Integer( var.evaluateAsInt(memory).intValue() - 1 );               
                break;
        }
        return var.assign(memory , new NumberExpr( newValue ) );
    }
    
    public Integer evaluateAsInt( MemoryTable memory ) {
        return (Integer)evaluate(memory);
    }
    
    public String evaluateAsString( MemoryTable memory ) {
        return evaluateAsInt(memory).toString();
    }
    
}
