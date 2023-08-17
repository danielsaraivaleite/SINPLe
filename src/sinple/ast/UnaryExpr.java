package sinple.ast;
import  sinple.MemoryTable;
import  sinple.Sym;

public class UnaryExpr implements Expr {
    
    private Expr exp;
    private int  oper;
    
    /** Creates a new instance of UnaryExpr */
    public UnaryExpr( Expr exp , int oper ) {
        this.exp = exp;
        this.oper = oper;
    }
    
    public Object evaluate( MemoryTable memory ) {
        Integer i = exp.evaluateAsInt( memory );
        switch(oper) {
            case Sym.PLUS:
                return i;
            case Sym.MINUS:
                return new Integer(-i.intValue());
            case Sym.NOT:
                if( i.intValue() == 0 ) return TrueExpr.TRUE.evaluateAsInt( memory );
                else                    return NullExpr.NULL.evaluateAsInt( memory );          
        }
        return  NullExpr.NULL.evaluateAsInt( memory );
    }
    
    public Integer evaluateAsInt( MemoryTable memory ) {
        return (Integer)evaluate(memory);
    }
    
    public String evaluateAsString( MemoryTable memory ) {
        return ((Integer)evaluate(memory)).toString();
    }
    
}
