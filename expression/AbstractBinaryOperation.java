package expression;

import exception.DivisionByZeroException;
import exception.OverflowException;

public abstract class AbstractBinaryOperation implements TripleExpression {
    private TripleExpression first;
    private TripleExpression second;

    public abstract int evaluateIntImpl(int a, int b) throws OverflowException, DivisionByZeroException;

    protected abstract void check(int a, int b) throws OverflowException, DivisionByZeroException;

    protected AbstractBinaryOperation(TripleExpression first, TripleExpression second) {
        this.first = first;
        this.second = second;
    }

    public int evaluate(int x, int y, int z) throws Exception {
        return evaluateIntImpl(first.evaluate(x, y, z), second.evaluate(x, y, z));
    }
}
