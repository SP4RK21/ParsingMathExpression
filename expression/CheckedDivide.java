package expression;

import exception.DivisionByZeroException;
import exception.OverflowException;

public class CheckedDivide extends AbstractBinaryOperation {

    public CheckedDivide(TripleExpression first, TripleExpression second) {
        super(first, second);
    }

    public void check(int a, int b) throws DivisionByZeroException, OverflowException {
        if (b == 0) {
            throw new DivisionByZeroException(a + " / " + b);
        } else if (a == Integer.MIN_VALUE && b == -1) {
            throw new OverflowException(a + " / " + b);
        }
    }

    @Override
    public int evaluateIntImpl(int a, int b) throws DivisionByZeroException, OverflowException {
        check(a, b);
        return a / b;
    }
}
