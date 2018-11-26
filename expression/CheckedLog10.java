package expression;

import exception.IllegalArgumentException;

public class CheckedLog10 extends AbstractUnaryOperation {

    public CheckedLog10(TripleExpression first) {
        super(first);
    }

    public int log10(int a) {
        int res = 0;
        while (a > 9) {
            res++;
            a /= 10;
        }
        return res;
    }

    public int evaluateIntImpl(int a) throws IllegalArgumentException {
        check(a);
        return log10(a);
    }

    @Override
    protected void check(int a) throws IllegalArgumentException {
        if (a <= 0) {
            throw new IllegalArgumentException("Argument in logarithm can't be negative or zero");
        }
    }
}
