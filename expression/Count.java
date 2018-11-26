package expression;

import exception.OverflowException;

public class Count extends AbstractUnaryOperation {

    public Count(TripleExpression first) {
        super(first);
    }

    public int evaluateIntImpl(int a) {
        return Integer.bitCount(a);
    }

    @Override
    protected void check(int a) throws OverflowException {
    }
}
