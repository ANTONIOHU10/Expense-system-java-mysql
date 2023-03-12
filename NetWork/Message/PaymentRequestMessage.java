package NetWork.Message;

public class PaymentRequestMessage extends Message{
    private int expenseId;

    public PaymentRequestMessage(int expenseId) {
        super(MessageType.PAYMENT_EXPENSE_REQUEST);
        this.expenseId = expenseId;
    }

    public int getExpenseId() {
        return expenseId;
    }
}
