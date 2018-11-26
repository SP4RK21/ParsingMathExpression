package exception;

public class OperationNotFoundException extends ParsingException {
    public OperationNotFoundException(int index, String expression) {
        super("Operation missing at position " + String.valueOf(index) + " in expression \n" + expression);
    }
}
