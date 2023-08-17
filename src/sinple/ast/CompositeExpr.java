// Needs improv casts

package sinple.ast;
import  sinple.Sym;
import  sinple.MemoryTable;
import  sinple.Error;

public class CompositeExpr implements Expr {
   
    private Expr left, right;
    private int  oper;
    
    /** Creates a new instance of CompositeExpr */
    public CompositeExpr( Expr left , int oper , Expr right ) {
        this.left = left;
        this.right = right;
        this.oper = oper;
    }

    public Object evaluate( MemoryTable memory ) {
        Integer iResult;
        int iLeft;
        int iRight;
        switch(oper) {
            case Sym.PLUS:
                iLeft  = left.evaluateAsInt( memory ).intValue();
                iRight = right.evaluateAsInt( memory ).intValue();
                return new Integer( iLeft + iRight );
                
            case Sym.MINUS:
                iLeft  = left.evaluateAsInt( memory ).intValue();
                iRight = right.evaluateAsInt( memory ).intValue();
                return new Integer( iLeft - iRight );
                
            case Sym.AND:
               iLeft  = left.evaluateAsInt( memory ).intValue();
               iRight = right.evaluateAsInt( memory ).intValue();
               if( iLeft != 0 && iRight != 0 ) return TrueExpr.TRUE.evaluateAsInt( memory );
               else return NullExpr.NULL.evaluateAsInt( memory );
                
            case Sym.OR:
               iLeft  = left.evaluateAsInt( memory ).intValue();
               iRight = right.evaluateAsInt( memory ).intValue();
               if( iLeft != 0 || iRight != 0 ) return TrueExpr.TRUE.evaluateAsInt( memory );
               else return NullExpr.NULL.evaluateAsInt( memory );
                
            case Sym.MULT:
                iLeft  = left.evaluateAsInt( memory ).intValue();
                iRight = right.evaluateAsInt( memory ).intValue();
                return new Integer( iLeft * iRight );
                
            case Sym.DIV:
                iLeft  = left.evaluateAsInt( memory ).intValue();
                iRight = right.evaluateAsInt( memory ).intValue();
                if( iRight ==0 ) { // runtime exception
                    Error.signalRuntimeError("Aritmetic Exception: Division by Zero");
                    return NullExpr.NULL.evaluateAsInt( memory );
                }
                return new Integer( iLeft / iRight );          

            case Sym.REMAINDER:
                iLeft  = left.evaluateAsInt( memory ).intValue();
                iRight = right.evaluateAsInt( memory ).intValue();
                if( iRight == 0 ) { // runtime exception
                    Error.signalRuntimeError("Aritmetic Exception: Division by Zero");
                    return NullExpr.NULL.evaluateAsInt( memory );
                }
                return new Integer( iLeft % iRight );
                
            case Sym.EQ:
               iLeft  = left.evaluateAsInt( memory ).intValue();
               iRight = right.evaluateAsInt( memory ).intValue();
               if( iLeft == iRight  ) return TrueExpr.TRUE.evaluateAsInt( memory );
               else return NullExpr.NULL.evaluateAsInt( memory );
                
            case Sym.NEQ:
               iLeft  = left.evaluateAsInt( memory ).intValue();
               iRight = right.evaluateAsInt( memory ).intValue();
               if( iLeft != iRight  ) return TrueExpr.TRUE.evaluateAsInt( memory );
               else return NullExpr.NULL.evaluateAsInt( memory );
 
               
            case Sym.GT:
               iLeft  = left.evaluateAsInt( memory ).intValue();
               iRight = right.evaluateAsInt( memory ).intValue();
               if( iLeft > iRight  ) return TrueExpr.TRUE.evaluateAsInt( memory );
               else return NullExpr.NULL.evaluateAsInt( memory );
               
            case Sym.GE:
               iLeft  = left.evaluateAsInt( memory ).intValue();
               iRight = right.evaluateAsInt( memory ).intValue();
               if( iLeft >= iRight  ) return TrueExpr.TRUE.evaluateAsInt( memory );
               else return NullExpr.NULL.evaluateAsInt( memory );

            case Sym.LT:
               iLeft  = left.evaluateAsInt( memory ).intValue();
               iRight = right.evaluateAsInt( memory ).intValue();
               if( iLeft < iRight  ) return TrueExpr.TRUE.evaluateAsInt( memory );
               else return NullExpr.NULL.evaluateAsInt( memory );
                
            case Sym.LE:
               iLeft  = left.evaluateAsInt( memory ).intValue();
               iRight = right.evaluateAsInt( memory ).intValue();
               if( iLeft <= iRight  ) return TrueExpr.TRUE.evaluateAsInt( memory );
               else return NullExpr.NULL.evaluateAsInt( memory );
                
            case Sym.CONCAT:                
               return left.evaluateAsString( memory ) + right.evaluateAsString( memory );
                
        }
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
