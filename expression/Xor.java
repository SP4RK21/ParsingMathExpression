package expression;

import exception.DivisionByZeroException;
import exception.OverflowException;

public class Xor extends AbstractBinaryOperation {

    public Xor(TripleExpression first, TripleExpression second) {
        super(first, second);
    }

    @Override
    public int evaluateIntImpl(int a, int b) {
        return a ^ b;
    }

    @Override
    protected void check(int a, int b) throws OverflowException, DivisionByZeroException {
    }
}

