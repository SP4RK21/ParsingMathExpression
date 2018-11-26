package exception;

public class DivisionByZeroException extends EvaluatingException {
    public DivisionByZeroException (String data) {
        super("Division by zero: " + data);
    }
}
