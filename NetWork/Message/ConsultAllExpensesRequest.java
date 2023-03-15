package NetWork.Message;

/**
 * a request for all expenses
 */
public class ConsultAllExpensesRequest extends Message{
    public ConsultAllExpensesRequest() {
        super(MessageType.CONSULTATION_ALL_EXPENSES_INFORMATION_REQUEST);
    }
}
