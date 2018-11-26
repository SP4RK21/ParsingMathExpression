package expression;

import exception.IllegalArgumentException;
import exception.OverflowException;

public class CheckedPow10 extends AbstractUnaryOperation {

    public CheckedPow10(TripleExpression first) {
        super(first);
    }

    public int pow10(int a) {
        int res = 1;
        while (a > 0) {
            res *= 10;
            a--;
        }
        return res;
    }

    public int evaluateIntImpl(int a) throws IllegalArgumentException, OverflowException {
        check(a);
        return pow10(a);
    }

    @Override
    protected void check(int a) throws IllegalArgumentException, OverflowException {
        if (a >= 10) {
            throw new OverflowException(10 + " ^ " + a);
        } if (a < 0) {
            throw new IllegalArgumentException("Negative argument in power is not allowed: " + a);
        }
    }
}
