package expression;

import exception.OverflowException;

public class CheckedMultiply extends AbstractBinaryOperation {

    public CheckedMultiply(TripleExpression first, TripleExpression second) {
        super(first, second);
    }

    public void check (int a, int b) throws OverflowException {
        if (a > 0 && b > 0  && a > Integer.MAX_VALUE / b) {
            throw new OverflowException(a + " * " + b);
        } else if (a < 0 && b < 0 && b < Integer.MAX_VALUE / a) {
            throw new OverflowException(a + " * " + b);
        } else if (a < 0 && b > 0 && a < Integer.MIN_VALUE / b) {
            throw new OverflowException(a + " * " + b);
        } else if (a > 0 && b < 0  && b < Integer.MIN_VALUE / a) {
            throw new OverflowException(a + " * " + b);
        }
    }

    @Override
    public int evaluateIntImpl(int a, int b) throws OverflowException {
        check(a, b);
        return a * b;
    }
}
