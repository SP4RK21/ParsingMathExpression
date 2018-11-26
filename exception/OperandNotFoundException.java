package exception;

public class OperandNotFoundException extends ParsingException {
    public OperandNotFoundException(int index, String expression) {
        super("Operand missing at position " + String.valueOf(index) + " in expression \n" + expression);
    }
}
