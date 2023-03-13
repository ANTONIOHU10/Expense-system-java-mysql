package NetWork.Message;

public class ExpenseMessageResponse extends Message{
    private String message;
    public ExpenseMessageResponse(String message) {
        super(MessageType.EXPENSE_RESPONSE);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
