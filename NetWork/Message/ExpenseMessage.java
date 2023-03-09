package NetWork.Message;

import Model.Expense;

public class ExpenseMessage extends Message{
    private Expense expense;

    public ExpenseMessage(Expense expense) {
        super(MessageType.EXPENSE);
        this.expense = expense;
    }

    public Expense getExpense() {
        return expense;
    }

    public void setExpense(Expense expense) {
        this.expense = expense;
    }

}
