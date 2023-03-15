package test.Controller;

import Controller.ExpenseController;
import Controller.UsersController;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExpenseControllerTest {

    @Test
    void insertExpense() throws SQLException {
        //database connection
        UsersController usersController = new UsersController("jdbc:mysql://localhost:3306/database_for_test","root","825310894");
        ExpenseController expenseController = new ExpenseController("jdbc:mysql://localhost:3306/database_for_test", "root", "825310894");

        //clear all existed data
        usersController.deleteExpensesBalance();
        usersController.deleteAllData();

        double amount = 100.0;
        int day = 15;
        int month = 3;
        int year = 2023;
        String description = "Esselunga";
        //creazione account
        usersController.registerAccount("NewUser","polimi123",0);
        usersController.registerAccount("NewUser666","polimi123",0);

        //inserimento
        int numberOfExpenseBefore = expenseController.countRowsInColumn("expense","id");
        int numberOfUsers = usersController.countRowsInColumn("users","id");

        expenseController.insertExpense(usersController.getFirstUserId(),amount,day,month,year,description);

        //--------------------verifico che sia stata creata una nuova spesa -------------------
        int numberOfExpenseAfter = expenseController.countRowsInColumn("expense","id");
        System.out.println(numberOfExpenseBefore);
        System.out.println(numberOfExpenseAfter);
        System.out.println(numberOfUsers);


        assertEquals(expenseController.countRowsInColumn("expense","id"),1);

        //---------------------verifico che sia stato inserito un record per ogni utente-------------------------
        //perché per l'utente che la carica, non deve pagare -> -1

        //verifica
        assertEquals(numberOfExpenseAfter-(numberOfUsers-1),numberOfExpenseBefore);

    }

    @Test
    void getUsernames() throws SQLException {
        //database connection
        UsersController usersController = new UsersController("jdbc:mysql://localhost:3306/database_for_test","root","825310894");
        ExpenseController expenseController = new ExpenseController("jdbc:mysql://localhost:3306/database_for_test", "root", "825310894");

        //clear all existed data
        usersController.deleteExpensesBalance();
        usersController.deleteAllData();

        //creazione account
        usersController.registerAccount("NewUser","polimi123",0);
        usersController.registerAccount("NewUser666","polimi123",0);

        List<String> usernameTest = new ArrayList<>();
        usernameTest.add("NewUser");
        usernameTest.add("NewUser666");

        List<String> usernames = expenseController.getUsernames();

        for(int i=0; i<2; i++){

            //verifica
            assertEquals(usernames.get(i),usernameTest.get(i));
        }
    }

    @Test
    void insertExpenseRecord() throws SQLException {
        //database connection
        UsersController usersController = new UsersController("jdbc:mysql://localhost:3306/database_for_test","root","825310894");
        ExpenseController expenseController = new ExpenseController("jdbc:mysql://localhost:3306/database_for_test", "root", "825310894");

        //clear all existed data
        usersController.deleteExpensesBalance();
        usersController.deleteAllData();

        //creazione account
        usersController.registerAccount("NewUser","polimi123",0);
        usersController.registerAccount("NewUser666","polimi123",0);

        //inserimento
        expenseController.insertExpenseRecord(1,2,3,400.00,100,13,2,1234,"TEST");

        String descriptionTest = expenseController.getStringFromTable("expense","payer_id",2,"description");
        int payer_idTest = expenseController.getIntFromTable("expense","payee_id",3,"payer_id");
        double payee_idTest = expenseController.getDoubleFromTable("expense","payer_id",2,"payee_id");
        double payerAmountTest = expenseController.getDoubleFromTable("expense","payer_id",2,"payer_amount");
        double payeeAmountTest = expenseController.getDoubleFromTable("expense","payer_id",2,"payee_amount");
        int dayTest = expenseController.getIntFromTable("expense","payer_id",2,"day");
        int monthTest = expenseController.getIntFromTable("expense","payer_id",2,"month");
        int yearTest = expenseController.getIntFromTable("expense","payer_id",2,"year");

        //verifica
        assertEquals(2,payer_idTest);
        assertEquals(3,payee_idTest);
        assertEquals(400,payerAmountTest);
        assertEquals(100,payeeAmountTest);
        assertEquals(13,dayTest);
        assertEquals(2,monthTest);
        assertEquals(1234,yearTest);
        assertEquals("TEST",descriptionTest);
    }


    @Test
    void getNextExpenseId() throws SQLException {
        //database connection
        UsersController usersController = new UsersController("jdbc:mysql://localhost:3306/database_for_test","root","825310894");
        ExpenseController expenseController = new ExpenseController("jdbc:mysql://localhost:3306/database_for_test", "root", "825310894");

        //clear all existed data
        usersController.deleteExpensesBalance();
        usersController.deleteAllData();

              //creazione account
        usersController.registerAccount("NewUser","polimi123",0);
        usersController.registerAccount("NewUser666","polimi123",0);

        //inserimento
        expenseController.insertExpenseRecord(1,2,3,400.00,100,13,2,1234,"TEST");

        //verifica
        assertEquals(expenseController.getNextExpenseId(),2);
    }

    @Test
    void getIdByUsername() throws SQLException {
        //database connection
        UsersController usersController = new UsersController("jdbc:mysql://localhost:3306/database_for_test","root","825310894");
        ExpenseController expenseController = new ExpenseController("jdbc:mysql://localhost:3306/database_for_test", "root", "825310894");

        //clear all existed data
        usersController.deleteExpensesBalance();
        usersController.deleteAllData();

        //creazione account
        usersController.registerAccount("NewUser","polimi123",0);
        usersController.registerAccount("NewUser666","polimi123",0);

        //verifica
        assertEquals(expenseController.getIdByUsername("NewUser"),usersController.getFirstUserId());
    }

    @Test
    void getUsernameById() throws SQLException {
        //database connection
        UsersController usersController = new UsersController("jdbc:mysql://localhost:3306/database_for_test","root","825310894");
        ExpenseController expenseController = new ExpenseController("jdbc:mysql://localhost:3306/database_for_test", "root", "825310894");

        //clear all existed data
        usersController.deleteExpensesBalance();
        usersController.deleteAllData();

        //creazione account
        usersController.registerAccount("NewUser","polimi123",0);
        usersController.registerAccount("NewUser666","polimi123",0);

        //verifica
        assertEquals(expenseController.getUsernameById(usersController.getFirstUserId()),"NewUser");
    }

    @Test
    void updateBalanceFromExpense() throws SQLException {
        //database connection
        UsersController usersController = new UsersController("jdbc:mysql://localhost:3306/database_for_test","root","825310894");
        ExpenseController expenseController = new ExpenseController("jdbc:mysql://localhost:3306/database_for_test", "root", "825310894");

        //clear all existed data
        usersController.deleteExpensesBalance();
        usersController.deleteAllData();

        //creazione account
        usersController.registerAccount("NewUser","polimi123",0);
        usersController.registerAccount("NewUser666","polimi123",0);

        //inserimento
        expenseController.insertExpense(usersController.getFirstUserId(),100,3,4,2023,"TEST");
        expenseController.updateBalanceFromExpense(1);
        double value_balance = expenseController.getDoubleFromTable("balance","id",usersController.getFirstUserId(),"balance");

        //verifica
        assertEquals(value_balance,50.00);
    }

    @Test
    void getPayeeAmountForExpense() throws SQLException {
        //database connection
        UsersController usersController = new UsersController("jdbc:mysql://localhost:3306/database_for_test","root","825310894");
        ExpenseController expenseController = new ExpenseController("jdbc:mysql://localhost:3306/database_for_test", "root", "825310894");

        //clear all existed data
        usersController.deleteExpensesBalance();
        usersController.deleteAllData();

        //creazione account
        usersController.registerAccount("NewUser","polimi123",0);
        usersController.registerAccount("NewUser666","polimi123",0);

        //inserimento
        expenseController.insertExpense(usersController.getFirstUserId(),100,3,4,2023,"TEST");

        //verifica
        assertEquals(expenseController.getPayeeAmountForExpense(1),50);
    }

    @Test
    void getPayerAmountForExpense() throws SQLException {
        //database connection
        UsersController usersController = new UsersController("jdbc:mysql://localhost:3306/database_for_test","root","825310894");
        ExpenseController expenseController = new ExpenseController("jdbc:mysql://localhost:3306/database_for_test", "root", "825310894");

        //clear all existed data
        usersController.deleteExpensesBalance();
        usersController.deleteAllData();

        //creazione account
        usersController.registerAccount("NewUser","polimi123",0);
        usersController.registerAccount("NewUser666","polimi123",0);

        //inserimento
        expenseController.insertExpense(usersController.getFirstUserId(),100,3,4,2023,"TEST");

        //verifica
        assertEquals(expenseController.getPayerAmountForExpense(1),100);
    }

    @Test
    void getPayerIdForExpense() throws SQLException {
        //database connection
        UsersController usersController = new UsersController("jdbc:mysql://localhost:3306/database_for_test","root","825310894");
        ExpenseController expenseController = new ExpenseController("jdbc:mysql://localhost:3306/database_for_test", "root", "825310894");

        //clear all existed data
        usersController.deleteExpensesBalance();
        usersController.deleteAllData();

        //creazione account
        usersController.registerAccount("NewUser","polimi123",0);
        usersController.registerAccount("NewUser666","polimi123",0);

        //inserimento
        expenseController.insertExpense(usersController.getFirstUserId(),100,3,4,2023,"TEST");

        //verifica
        assertEquals(expenseController.getPayerIdForExpense(1),usersController.getFirstUserId());
    }

    @Test
    void setBalance() throws SQLException {
        //database connection
        UsersController usersController = new UsersController("jdbc:mysql://localhost:3306/database_for_test","root","825310894");
        ExpenseController expenseController = new ExpenseController("jdbc:mysql://localhost:3306/database_for_test", "root", "825310894");

        //clear all existed data
        usersController.deleteExpensesBalance();
        usersController.deleteAllData();

        //creazione account
        usersController.registerAccount("NewUser","polimi123",0);
        usersController.registerAccount("NewUser666","polimi123",0);

        //inserimento
        expenseController.insertExpense(usersController.getFirstUserId(),100,3,4,2023,"TEST");
        expenseController.setBalance();


        double value_balance = expenseController.getDoubleFromTable("balance","id",usersController.getFirstUserId(),"balance");

        //verifica
        assertEquals(value_balance,50.00);
    }

    @Test
    void payExpense() throws SQLException {
        //database connection
        UsersController usersController = new UsersController("jdbc:mysql://localhost:3306/database_for_test","root","825310894");
        ExpenseController expenseController = new ExpenseController("jdbc:mysql://localhost:3306/database_for_test", "root", "825310894");

        //clear all existed data
        usersController.deleteExpensesBalance();
        usersController.deleteAllData();

        //creazione account
        usersController.registerAccount("NewUser","polimi123",0);
        usersController.registerAccount("NewUser666","polimi123",0);

        //inserimento
        expenseController.insertExpense(usersController.getFirstUserId(),100,3,4,2023,"TEST");

        //payment
        //+1 perché è il secondo user che paga
        expenseController.payExpense(1,usersController.getFirstUserId()+1);
        assertEquals(expenseController.getBooleanFromTable("expense","expense_id",1,"ifPaid"),true);
    }

    @Test
    void isExpensePaid() {

    }

    @Test
    void consultListExpense() {
    }

    @Test
    void getExpenses() {
    }

    @Test
    void consultAllExpenses() {
    }

    @Test
    void consultAllBalances() {
    }

    @Test
    void consultIfExistsExpense() {
    }

    @Test
    void countRowsInColumn() {
    }

    @Test
    void getStringFromTable() {
    }

    @Test
    void getDoubleFromTable() {
    }

    @Test
    void getIntFromTable() {
    }
}