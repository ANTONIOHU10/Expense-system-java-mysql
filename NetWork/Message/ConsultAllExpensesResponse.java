package NetWork.Message;

import Model.Expense;

import java.util.ArrayList;
import java.util.List;

public class ConsultAllExpensesResponse extends Message{
    List<Expense> listOfExpense = new ArrayList<Expense>();
    public ConsultAllExpensesResponse(List<Expense> listOfExpense) {
        super(MessageType.CONSULTATION_ALL_EXPENSES_INFORMATION_RESPONSE);
        this.listOfExpense = listOfExpense;
    }

    public List<Expense> getListOfExpense() {
        return listOfExpense;
    }
}
