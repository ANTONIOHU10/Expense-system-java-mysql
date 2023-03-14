package NetWork.Message;

public class DeleteExpensesBalanceResponse extends Message{
    private final String message;

    /**
     *
     * @param message the message to be sent to Client
     */
    public DeleteExpensesBalanceResponse(String message) {
        super(MessageType.DELETE_EXPENSES_BALANCE_INFORMATION_RESPONSE);
        this.message = message;
    }

    /**
     *
     * @return the message to be sent to Client
     */
    public String getMessage() {
        return message;
    }
}
