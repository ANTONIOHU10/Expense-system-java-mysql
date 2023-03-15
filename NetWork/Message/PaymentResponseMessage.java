package NetWork.Message;

/**
 * a response for payment
 */
public class PaymentResponseMessage extends Message{
    String message;

    /**
     *
     * @param message the message to be sent to client about the payment
     */
    public PaymentResponseMessage(String message) {
        super(MessageType.PAYMENT_EXPENSE_RESPONSE);
        this.message = message;
    }

    /**
     *
     * @return message to be sent
     */
    public String getMessage() {
        return message;
    }
}
