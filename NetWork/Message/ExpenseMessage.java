package NetWork.Message;

import Model.Expense;

public class ExpenseMessage extends Message{
    private Expense expense;

    /**
     *
     * @param expense a single expense to be added
     */
    public ExpenseMessage(Expense expense) {
        super(MessageType.EXPENSE);
        this.expense = expense;
    }

    /**
     *
     * @return the expense added
     */
    public Expense getExpense() {
        return expense;
    }

    /**
     *
     * @param expense the expense to be added
     */
    public void setExpense(Expense expense) {
        this.expense = expense;
    }

}
