package expression;

public class Const implements TripleExpression {
    private int constNum;

    public Const(int num) {
        constNum = num;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return constNum;
    }
}
