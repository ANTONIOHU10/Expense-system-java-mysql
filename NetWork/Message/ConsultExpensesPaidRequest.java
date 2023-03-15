package NetWork.Message;

/**
 * a request for expenses paid
 */
public class ConsultExpensesPaidRequest extends Message{
    public ConsultExpensesPaidRequest() {
        super(MessageType.CONSULTATION_EXPENSES_PAID_REQUEST);
    }
}
