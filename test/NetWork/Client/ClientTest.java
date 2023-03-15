package test.NetWork.Client;

import Model.Balance;
import Model.Expense;
import NetWork.Client.Client;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {

    @Test
    void getIsLoggedIn() {
        Client.setLoggedIn(true);

        //condizione
        assertEquals(true,Client.getIsLoggedIn());
    }

    @Test
    void getIsAdmin() {
        Client.setAdmin();

        //condizione
        assertEquals(true,Client.getIsAdmin());
    }

    @Test
    void getIdClient() {
        Client.setId_client(1);

        //condizione
        assertEquals(1,Client.getIdClient());
    }

    @Test
    void setLoggedIn() {
        Client.setLoggedIn(true);

        //condizione
        assertEquals(true,Client.getIsLoggedIn());
    }

    @Test
    void setId_client() {
        Client.setId_client(1);

        //condizione
        assertEquals(1,Client.getIdClient());
    }

    @Test
    void setExpensesList() {
        List<Expense> expenseList = new ArrayList<>();
        expenseList.add(new Expense(1,2,10.00,20.00,13,12,2023,"TEST1",0));
        expenseList.add(new Expense(2,2,10.00,20.00,13,12,2023,"TEST2",0));
        Client.setExpensesList(expenseList);

        //condizione
        assertEquals(1,Client.getExpensesList().get(0).getExpense_id());
        assertEquals(2,Client.getExpensesList().get(0).getId_payer());
        assertEquals(10.00,Client.getExpensesList().get(0).getPayer_amount());
        assertEquals(20.00,Client.getExpensesList().get(0).getPayee_amount());
        assertEquals(13,Client.getExpensesList().get(0).getDay());
        assertEquals(12,Client.getExpensesList().get(0).getMonth());
        assertEquals(2023,Client.getExpensesList().get(0).getYear());
        assertEquals("TEST1",Client.getExpensesList().get(0).getDescription());
        assertEquals(0,Client.getExpensesList().get(0).getIfPaid());

    }

    @Test
    void setAdmin() {
        Client.setAdmin();

        //condizione
        assertEquals(true,Client.getIsAdmin());
    }

    @Test
    void setNotAdmin() {
        Client.setNotAdmin();

        //condizione
        assertEquals(false,Client.getIsAdmin());
    }

    @Test
    void setMessage() {
        Client.setMessage("TEST");

        //condizione
        assertEquals("TEST",Client.getMessage());
    }

    @Test
    void setBalanceList() {
        List<Balance> balanceList = new ArrayList<>();
        balanceList.add(new Balance(1,50.00,20.00,30.00));
        Client.setBalanceList(balanceList);

        //condizione
        assertEquals(1, Client.getBalanceList().get(0).getId());
        assertEquals(50.00, Client.getBalanceList().get(0).getAmount_owed());
        assertEquals(20.00, Client.getBalanceList().get(0).getAmount_paid());
        assertEquals(30.00, Client.getBalanceList().get(0).getBalance());

    }

    @Test
    void getMessage() {
        Client.setMessage("TEST");

        //condizione
        assertEquals("TEST",Client.getMessage());
    }

    @Test
    void getExpensesList() {
        List<Expense> expenseList = new ArrayList<>();
        expenseList.add(new Expense(1,2,10.00,20.00,13,12,2023,"TEST1",0));
        expenseList.add(new Expense(2,2,10.00,20.00,13,12,2023,"TEST2",0));
        Client.setExpensesList(expenseList);

        //condizione
        assertEquals(1,Client.getExpensesList().get(0).getExpense_id());
        assertEquals(2,Client.getExpensesList().get(0).getId_payer());
        assertEquals(10.00,Client.getExpensesList().get(0).getPayer_amount());
        assertEquals(20.00,Client.getExpensesList().get(0).getPayee_amount());
        assertEquals(13,Client.getExpensesList().get(0).getDay());
        assertEquals(12,Client.getExpensesList().get(0).getMonth());
        assertEquals(2023,Client.getExpensesList().get(0).getYear());
        assertEquals("TEST1",Client.getExpensesList().get(0).getDescription());
        assertEquals(0,Client.getExpensesList().get(0).getIfPaid());
    }

    @Test
    void getBalanceList() {
        List<Balance> balanceList = new ArrayList<>();
        balanceList.add(new Balance(1,50.00,20.00,30.00));
        Client.setBalanceList(balanceList);

        //condizione
        assertEquals(1, Client.getBalanceList().get(0).getId());
        assertEquals(50.00, Client.getBalanceList().get(0).getAmount_owed());
        assertEquals(20.00, Client.getBalanceList().get(0).getAmount_paid());
        assertEquals(30.00, Client.getBalanceList().get(0).getBalance());

    }
}