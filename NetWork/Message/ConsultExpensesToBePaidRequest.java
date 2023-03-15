package NetWork.Message;

/**
 * a request for all expenses to be paid
 */
public class ConsultExpensesToBePaidRequest extends Message{
    public ConsultExpensesToBePaidRequest() {
        super(MessageType.CONSULTATION_EXPENSES_TO_BE_PAID_REQUEST);
    }
}
