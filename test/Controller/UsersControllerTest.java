package test.Controller;

import Controller.UsersController;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class UsersControllerTest {

    @Test
    void registerAccount() throws SQLException {
        //database connection
        UsersController usersController = new UsersController("jdbc:mysql://localhost:3306/database_for_test","root","825310894");

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

        //clear all existed data
        usersController.deleteExpensesBalance();
        usersController.deleteAllData();

        //creazione account
        usersController.registerAccount("NewUser","polimi123",0);
        usersController.registerAccount("NewUser666","polimi123",0);

        //condizione
        assertTrue(usersController.usernameExists("NewUser"));
        assertTrue(usersController.usernameExists("NewUser666"));
        assertFalse(usersController.usernameExists("POP"));
    }

    @Test
    void login() throws SQLException {
        //database connection
        UsersController usersController = new UsersController("jdbc:mysql://localhost:3306/database_for_test","root","825310894");

        //clear all existed data
        usersController.deleteExpensesBalance();
        usersController.deleteAllData();

        //creazione account
        usersController.registerAccount("NewUser","polimi123",0);
        usersController.registerAccount("NewUser666","polimi123",0);

        //condizione
        assertTrue(usersController.login("NewUser", "polimi123"));
        assertTrue(usersController.login("NewUser666", "polimi123"));
        assertFalse(usersController.login("NewUser99999", "polimi123"));

    }

    @Test
    void getUserId() throws SQLException {
        //database connection
        UsersController usersController = new UsersController("jdbc:mysql://localhost:3306/database_for_test","root","825310894");

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

        //clear all existed data
        usersController.deleteExpensesBalance();
        usersController.deleteAllData();

        //creazione account
        usersController.registerAccount("NewUser","polimi123",0);
        usersController.registerAccount("NewUser666","polimi123",0);

        //cancellazione dei dati
        usersController.deleteAllData();

        //condizione
        assertTrue(usersController.isTableEmpty("users"));
    }

    @Test
    void deleteExpensesBalance() throws SQLException {
        //database connection
        UsersController usersController = new UsersController("jdbc:mysql://localhost:3306/database_for_test","root","825310894");

        //clear all existed data
        usersController.deleteExpensesBalance();
        usersController.deleteAllData();

        //creazione account
        usersController.registerAccount("NewUser","polimi123",0);
        usersController.registerAccount("NewUser666","polimi123",0);

        //cancellazione dei dati
        usersController.deleteExpensesBalance();

        //condizione
        assertTrue(usersController.isTableEmpty("expense"));
    }

    @Test
    void getUsersIdAndUsername() throws SQLException {
        //database connection
        UsersController usersController = new UsersController("jdbc:mysql://localhost:3306/database_for_test","root","825310894");

        //clear all existed data
        usersController.deleteExpensesBalance();
        usersController.deleteAllData();

        //creazione account
        usersController.registerAccount("NewUser","polimi123",0);
        usersController.registerAccount("NewUser666","polimi123",0);

        //condizione
        assertEquals("NewUser",usersController.getUsersIdAndUsername().get(usersController.getFirstUserId()));
        assertEquals("NewUser666",usersController.getUsersIdAndUsername().get(usersController.getFirstUserId()+1));
        assertFalse(usersController.getUsersIdAndUsername().entrySet().contains(999));

    }

    @Test
    void countRowsInColumn() throws SQLException {
        //database connection
        UsersController usersController = new UsersController("jdbc:mysql://localhost:3306/database_for_test","root","825310894");

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

        //clear all existed data
        usersController.deleteExpensesBalance();
        usersController.deleteAllData();

        //condizione
        assertTrue(usersController.isTableEmpty("users"));
    }
}