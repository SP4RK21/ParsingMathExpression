package expression;

import exception.IllegalArgumentException;
import exception.OverflowException;

public abstract class AbstractUnaryOperation implements TripleExpression {
    private TripleExpression first;

    public abstract int evaluateIntImpl(int a) throws OverflowException, IllegalArgumentException;
    protected abstract void check(int a) throws OverflowException, IllegalArgumentException;

    public AbstractUnaryOperation(TripleExpression first) {
        this.first = first;
    }


    public int evaluate(int x, int y, int z) throws Exception {
        return evaluateIntImpl(first.evaluate(x, y, z));
    }
}
