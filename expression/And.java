package expression;

public class And extends AbstractBinaryOperation {

    public And(TripleExpression first, TripleExpression second) {
        super(first, second);
    }

    @Override
    public int evaluateIntImpl(int a, int b) {
        return a & b;
    }

    @Override
    protected void check(int a, int b) {
    }
}

