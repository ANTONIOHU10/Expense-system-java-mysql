package NetWork.Message;

import Model.Expense;

import java.util.ArrayList;
import java.util.List;

public class ConsultExpensesToBePaidResponse extends Message{
    List<Expense> listOfExpense = new ArrayList<Expense>();
    public ConsultExpensesToBePaidResponse(List<Expense> listOfExpense) {
        super(MessageType.CONSULTATION_EXPENSES_TO_BE_PAID_RESPONSE);
        this.listOfExpense = listOfExpense;
    }

    public List<Expense> getListOfExpense() {
        return listOfExpense;
    }
}
