package exception;

public class OverflowException extends EvaluatingException {
    public OverflowException(String data) {
        super("Overflow: " + data);
    }
}
