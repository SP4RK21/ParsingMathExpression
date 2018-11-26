package expression;

import exception.OverflowException;

public class CheckedAdd extends AbstractBinaryOperation {

    public CheckedAdd(TripleExpression first, TripleExpression second) {
        super(first, second);
    }

    public void check (int a, int b) throws OverflowException {
        if (b > 0 && a > Integer.MAX_VALUE - b) {
            throw new OverflowException(a + " + " + b);
        } else if (b < 0 && a < Integer.MIN_VALUE - b) {
            throw new OverflowException(a + " + " + b);
        }
    }
    @Override
    public int evaluateIntImpl(int a, int b) throws OverflowException {
        check(a, b);
        return a + b;
    }
}

