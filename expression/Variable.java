package expression;

public class Variable implements TripleExpression {
    private String var;

    public Variable(String  c) {
        var = c;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return var.equals("x") ? x : var.equals("y") ? y : z;
    }
}
