package NetWork.Message;

import Model.Expense;

import java.util.ArrayList;
import java.util.List;

public class ConsultExpensesPaidResponse extends Message{
    List<Expense> listOfExpense = new ArrayList<Expense>();
    public ConsultExpensesPaidResponse(List<Expense> listOfExpense) {
        super(MessageType.CONSULTATION_EXPENSES_PAID_RESPONSE);
        this.listOfExpense = listOfExpense;
    }

    public List<Expense> getListOfExpense() {
        return listOfExpense;
    }
}
