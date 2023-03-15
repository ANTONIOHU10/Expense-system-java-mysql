package NetWork.Message;

import Model.Expense;
import java.util.List;

/**
 * a response for all expenses paid
 */
public class ConsultExpensesPaidResponse extends Message{
    List<Expense> listOfExpense;

    /**
     *
     * @param listOfExpense list of Expense paid
     */
    public ConsultExpensesPaidResponse(List<Expense> listOfExpense) {
        super(MessageType.CONSULTATION_EXPENSES_PAID_RESPONSE);
        this.listOfExpense = listOfExpense;
    }

    /**
     *
     * @return list of expense
     */
    public List<Expense> getListOfExpense() {
        return listOfExpense;
    }
}
