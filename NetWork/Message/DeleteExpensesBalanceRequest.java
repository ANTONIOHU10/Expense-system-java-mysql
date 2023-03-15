package NetWork.Message;

/**
 * a request for deleting all expenses and balance information
 */
public class DeleteExpensesBalanceRequest extends Message{
    public DeleteExpensesBalanceRequest() {
        super(MessageType.DELTE_EXPENSES_BALANCE_INFORMATION_REQUEST);
    }
}
