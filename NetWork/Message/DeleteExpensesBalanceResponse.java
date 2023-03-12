package NetWork.Message;

public class DeleteExpensesBalanceResponse extends Message{
    private String message;
    public DeleteExpensesBalanceResponse(String message) {
        super(MessageType.DELETE_EXPENSES_BALANCE_INFORMATION_RESPONSE);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
