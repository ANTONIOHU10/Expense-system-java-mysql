package NetWork.Message;

/**
 * a request for payment
 */
public class PaymentRequestMessage extends Message{
    private final int expenseId;

    /**
     *
     * @param expenseId the unique id of the expense to be paid
     */
    public PaymentRequestMessage(int expenseId) {
        super(MessageType.PAYMENT_EXPENSE_REQUEST);
        this.expenseId = expenseId;
    }

    /**
     *
     * @return the unique id of the expense
     */
    public int getExpenseId() {
        return expenseId;
    }
}
