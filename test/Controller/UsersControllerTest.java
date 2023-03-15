package test.Controller;

import Controller.ExpenseController;
import Controller.UsersController;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class UsersControllerTest {

    @Test
    void registerAccount() throws SQLException {
        //database connection
        UsersController usersController = new UsersController("jdbc:mysql://localhost:3306/database_for_test","root","825310894");
        ExpenseController expenseController = new ExpenseController("jdbc:mysql://localhost:3306/database_for_test", "root", "825310894");

        //clear all existed data
        usersController.deleteExpensesBalance();
        usersController.deleteAllData();

        //creazione account
        usersController.registerAccount("NewUser","polimi123",0);
        usersController.registerAccount("NewUser666","polimi123",0);

        //condizione
        assertEquals(usersController.getFirstUserId(), usersController.getUserId("NewUser"));

    }

    @Test
    void usernameExists() throws SQLException {
        //database connection
        UsersController usersController = new UsersController("jdbc:mysql://localhost:3306/database_for_test","root","825310894");
        ExpenseController expenseController = new ExpenseController("jdbc:mysql://localhost:3306/database_for_test", "root", "825310894");

        //clear all existed data
        usersController.deleteExpensesBalance();
        usersController.deleteAllData();

        //creazione account
        usersController.registerAccount("NewUser","polimi123",0);
        usersController.registerAccount("NewUser666","polimi123",0);

        //condizione
        assertEquals(usersController.usernameExists("NewUser"),true);
        assertEquals(usersController.usernameExists("NewUser666"),true);
        assertEquals(usersController.usernameExists("POP"),false);
    }

    @Test
    void login() throws SQLException {
        //database connection
        UsersController usersController = new UsersController("jdbc:mysql://localhost:3306/database_for_test","root","825310894");
        ExpenseController expenseController = new ExpenseController("jdbc:mysql://localhost:3306/database_for_test", "root", "825310894");

        //clear all existed data
        usersController.deleteExpensesBalance();
        usersController.deleteAllData();

        //creazione account
        usersController.registerAccount("NewUser","polimi123",0);
        usersController.registerAccount("NewUser666","polimi123",0);

        //condizione
        assertEquals(true,usersController.login("NewUser","polimi123"));
        assertEquals(true,usersController.login("NewUser666","polimi123"));
        assertEquals(false,usersController.login("NewUser99999","polimi123"));

    }

    @Test
    void getUserId() throws SQLException {
        //database connection
        UsersController usersController = new UsersController("jdbc:mysql://localhost:3306/database_for_test","root","825310894");
        ExpenseController expenseController = new ExpenseController("jdbc:mysql://localhost:3306/database_for_test", "root", "825310894");

        //clear all existed data
        usersController.deleteExpensesBalance();
        usersController.deleteAllData();

        //creazione account
        usersController.registerAccount("NewUser","polimi123",0);
        usersController.registerAccount("NewUser666","polimi123",0);

        //condizione
        assertEquals(usersController.getFirstUserId(), usersController.getUserId("NewUser"));

    }

    @Test
    void getUserRoleById() throws SQLException {
        //database connection
        UsersController usersController = new UsersController("jdbc:mysql://localhost:3306/database_for_test","root","825310894");
        ExpenseController expenseController = new ExpenseController("jdbc:mysql://localhost:3306/database_for_test", "root", "825310894");

        //clear all existed data
        usersController.deleteExpensesBalance();
        usersController.deleteAllData();

        //creazione account
        usersController.registerAccount("NewUser","polimi123",0);
        usersController.registerAccount("NewUser666","polimi123",0);

        //condizione
        assertEquals("User",usersController.getUserRoleById(usersController.getFirstUserId()));
    }

    @Test
    void deleteAllData() throws SQLException {
        //database connection
        UsersController usersController = new UsersController("jdbc:mysql://localhost:3306/database_for_test","root","825310894");
        ExpenseController expenseController = new ExpenseController("jdbc:mysql://localhost:3306/database_for_test", "root", "825310894");

        //clear all existed data
        usersController.deleteExpensesBalance();
        usersController.deleteAllData();

        //creazione account
        usersController.registerAccount("NewUser","polimi123",0);
        usersController.registerAccount("NewUser666","polimi123",0);

        //cancellazione dei dati
        usersController.deleteAllData();

        //condizione
        assertEquals(true,usersController.isTableEmpty("users"));
    }

    @Test
    void deleteExpensesBalance() throws SQLException {
        //database connection
        UsersController usersController = new UsersController("jdbc:mysql://localhost:3306/database_for_test","root","825310894");
        ExpenseController expenseController = new ExpenseController("jdbc:mysql://localhost:3306/database_for_test", "root", "825310894");

        //clear all existed data
        usersController.deleteExpensesBalance();
        usersController.deleteAllData();

        //creazione account
        usersController.registerAccount("NewUser","polimi123",0);
        usersController.registerAccount("NewUser666","polimi123",0);

        //cancellazione dei dati
        usersController.deleteExpensesBalance();

        //condizione
        assertEquals(true,usersController.isTableEmpty("expense"));
    }

    @Test
    void getUsersIdAndUsername() throws SQLException {
        //database connection
        UsersController usersController = new UsersController("jdbc:mysql://localhost:3306/database_for_test","root","825310894");
        ExpenseController expenseController = new ExpenseController("jdbc:mysql://localhost:3306/database_for_test", "root", "825310894");

        //clear all existed data
        usersController.deleteExpensesBalance();
        usersController.deleteAllData();

        //creazione account
        usersController.registerAccount("NewUser","polimi123",0);
        usersController.registerAccount("NewUser666","polimi123",0);

        //condizione
        assertEquals("NewUser",usersController.getUsersIdAndUsername().get(usersController.getFirstUserId()));
        assertEquals("NewUser666",usersController.getUsersIdAndUsername().get(usersController.getFirstUserId()+1));
        assertEquals(false,usersController.getUsersIdAndUsername().entrySet().contains(999));

    }

    @Test
    void countRowsInColumn() throws SQLException {
        //database connection
        UsersController usersController = new UsersController("jdbc:mysql://localhost:3306/database_for_test","root","825310894");
        ExpenseController expenseController = new ExpenseController("jdbc:mysql://localhost:3306/database_for_test", "root", "825310894");

        //clear all existed data
        usersController.deleteExpensesBalance();
        usersController.deleteAllData();

        //creazione account
        usersController.registerAccount("NewUser","polimi123",0);
        usersController.registerAccount("NewUser666","polimi123",0);

        //condizione
        assertEquals(2,usersController.countRowsInColumn("users","id"));
    }

    @Test
    void getFirstUserId() throws SQLException {
        //database connection
        UsersController usersController = new UsersController("jdbc:mysql://localhost:3306/database_for_test","root","825310894");
        ExpenseController expenseController = new ExpenseController("jdbc:mysql://localhost:3306/database_for_test", "root", "825310894");

        //clear all existed data
        usersController.deleteExpensesBalance();
        usersController.deleteAllData();

        //creazione account
        usersController.registerAccount("NewUser","polimi123",0);
        usersController.registerAccount("NewUser666","polimi123",0);

        //condizione
        assertEquals(usersController.getFirstUserId(),usersController.getUserId("NewUser"));

    }

    @Test
    void isTableEmpty() throws SQLException {
//database connection
        UsersController usersController = new UsersController("jdbc:mysql://localhost:3306/database_for_test","root","825310894");
        ExpenseController expenseController = new ExpenseController("jdbc:mysql://localhost:3306/database_for_test", "root", "825310894");

        //clear all existed data
        usersController.deleteExpensesBalance();
        usersController.deleteAllData();

        //condizione
        assertEquals(true,usersController.isTableEmpty("users"));
    }
}