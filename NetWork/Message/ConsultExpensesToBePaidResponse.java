package NetWork.Message;

import Model.Expense;
import java.util.List;

public class ConsultExpensesToBePaidResponse extends Message{
    List<Expense> listOfExpense;

    /**
     *
     * @param listOfExpense list of expenses to be paid
     */
    public ConsultExpensesToBePaidResponse(List<Expense> listOfExpense) {
        super(MessageType.CONSULTATION_EXPENSES_TO_BE_PAID_RESPONSE);
        this.listOfExpense = listOfExpense;
    }

    /**
     *
     * @return the list of expenses got
     */
    public List<Expense> getListOfExpense() {
        return listOfExpense;
    }
}
