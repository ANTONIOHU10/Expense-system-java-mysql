package Controller;

import Model.Expense;

import java.sql.*;
import java.util.*;

public class ExpenseController {
    private Connection dbConnection;

    /**
     *
     * @param url "jdbc:mysql//localhost:3306/test"
     * @param username "root"
     * @param password "825310894"
     */
    public ExpenseController(String url, String username, String password) throws SQLException {
        dbConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "825310894");

    }
    /*
    * In questa tabella, per ogni spesa puoi avere più righe, una per ogni pagamento effettuato da un utente al beneficiario della spesa. Ad esempio, se Alice paga $20 per una spesa e Bob e Charlie devono ancora pagare la loro parte ($15 ciascuno), nella tabella "payments" ci sarebbero tre righe:

        id	expense_id	payer_id	payee_id	amount
        1	1	Alice	Bob	15
        2	1	Alice	Charlie	15
        3	1	Bob	Charlie	15

        In questo modo puoi tenere traccia di tutti i pagamenti effettuati
        * e di quelli ancora da effettuare, e calcolare facilmente il saldo di ogni utente per ogni spesa.
    * */
    public void insertExpense(int idUser, double amount, int day, int month, int year, String description) throws SQLException {
        // Get the list of usernames from the users table
        List<String> usernames = getUsernames();

        // Calculate the payee amount per user
        int numUsers = usernames.size() - 1; // exclude payer from count
        double payeeAmount = amount / numUsers;

        // Insert new expense record for each user
        int expenseId = getNextExpenseId();
        for (String username : usernames) {
            if (!username.equals(getUsernameById(idUser))) {
                // payee record
                //fixme -> se voglio utilizzare username, basta manipolare sui metodi getIdByUsername e getUsernameById
                insertExpenseRecord(expenseId, idUser, getIdByUsername(username), amount, payeeAmount, day, month, year, description);


                // payer record
                //insertExpenseRecord(expenseId, idUser, idUser, amount, 0, day, month, year, description);
            }
        }
    }

    //metodo ausiliare:
    private void insertExpenseRecord(int expenseId, int payerId, int payeeId, double payerAmount, double payeeAmount, int day, int month, int year, String description) throws SQLException {
        String insertSql = "INSERT INTO expense (expense_id, payer_id, payee_id, payer_amount, payee_amount, day, month, year, description) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement insertStatement = dbConnection.prepareStatement(insertSql);
        insertStatement.setInt(1, expenseId);
        insertStatement.setInt(2, payerId);
        insertStatement.setInt(3, payeeId);
        insertStatement.setDouble(4, payerAmount);
        insertStatement.setDouble(5, payeeAmount);
        insertStatement.setInt(6, day);
        insertStatement.setInt(7, month);
        insertStatement.setInt(8, year);
        insertStatement.setString(9, description);
        insertStatement.executeUpdate();
    }

    //metodo ausiliare
    private int getNextExpenseId() throws SQLException {
        String querySql = "SELECT MAX(expense_id) FROM expense";
        Statement queryStatement = dbConnection.createStatement();
        ResultSet resultSet = queryStatement.executeQuery(querySql);
        if (resultSet.next()) {
            return resultSet.getInt(1) + 1;
        } else {
            return 1;
        }
    }

    //metodo ausiliare
    private int getIdByUsername(String username) throws SQLException {
        String querySql = "SELECT id FROM users WHERE username = ?";
        PreparedStatement queryStatement = dbConnection.prepareStatement(querySql);
        queryStatement.setString(1, username);
        ResultSet resultSet = queryStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt(1);
        } else {
            throw new SQLException("User with username " + username + " not found.");
        }
    }

    //metodo ausiliare
    private String getUsernameById(int id) throws SQLException {
        String querySql = "SELECT username FROM users WHERE id = ?";
        PreparedStatement queryStatement = dbConnection.prepareStatement(querySql);
        queryStatement.setInt(1, id);
        ResultSet resultSet = queryStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getString(1);
        } else {
            throw new SQLException("User with ID " + id + " not found.");
        }
    }

    //metodo ausiliare
    //questo metodo recupera una lista di tutti gli username presenti nella tabella "users".
    public List<String> getUsernames() throws SQLException {
        List<String> usernames = new ArrayList<>();

        String selectSql = "SELECT username FROM users";
        Statement selectStatement = dbConnection.createStatement();
        ResultSet resultSet = selectStatement.executeQuery(selectSql);

        while (resultSet.next()) {
            String username = resultSet.getString("username");
            usernames.add(username);
        }

        return usernames;
    }



}
