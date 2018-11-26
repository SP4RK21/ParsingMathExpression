package expression;

public class BitNot extends AbstractUnaryOperation {

    public BitNot(TripleExpression first) {
        super(first);
    }

    public int evaluateIntImpl(int a) {
        return ~a;
    }

    @Override
    protected void check(int a) {
    }
}
