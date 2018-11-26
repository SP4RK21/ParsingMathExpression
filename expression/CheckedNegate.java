package expression;

import exception.OverflowException;

public class CheckedNegate extends AbstractUnaryOperation {
    
    public CheckedNegate(TripleExpression first) {
        super(first);
    }

    public void check (int a) throws OverflowException {
        if (a < 0 && a == Integer.MIN_VALUE) {
            throw new OverflowException("-" + a);
        }
    }

    public int evaluateIntImpl(int a) throws OverflowException {
        check(a);
        return -a;
    }
}
