package NetWork.Message;

public class ExpenseMessageResponse extends Message{
    private final String message;

    /**
     *
     * @param message the message of response about the adding expense message
     */
    public ExpenseMessageResponse(String message) {
        super(MessageType.EXPENSE_RESPONSE);
        this.message = message;
    }

    /**
     *
     * @return the message of this response
     */
    public String getMessage() {
        return message;
    }
}
