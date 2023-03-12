package NetWork.Message;

public class PaymentResponseMessage extends Message{
    String message;
    public PaymentResponseMessage(String message) {
        super(MessageType.PAYMENT_EXPENSE_RESPONSE);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
