package NetWork.Message;

import Model.Expense;
import java.util.List;

/**
 * a response for all expenses
 */
public class ConsultAllExpensesResponse extends Message{
    List<Expense> listOfExpense;

    /**
     *
     * @param listOfExpense the result of consultation, contains expenses
     */
    public ConsultAllExpensesResponse(List<Expense> listOfExpense) {
        super(MessageType.CONSULTATION_ALL_EXPENSES_INFORMATION_RESPONSE);
        this.listOfExpense = listOfExpense;
    }

    /**
     *
     * @return list of the expenses
     */
    public List<Expense> getListOfExpense() {
        return listOfExpense;
    }
}
