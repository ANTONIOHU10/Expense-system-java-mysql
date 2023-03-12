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

        // Calcola il numero di persone che devono pagare
        // Calcola l'importo da pagare per ognuno
        //se voglio che anche il payer paga un importo-> tolgo -1/ aggiungo -1, così tutti pagano un certo importo
        int numUsers = usernames.size(); // exclude payer from count
        double payeeAmount = amount / numUsers;

        // Ottiene expense_id della nuova spesa
        int expenseId = getNextExpenseId();
        // Per ogni utente
        for (String username : usernames) {
            //quando non è l'utente che ha pagato la spesa
            if (!username.equals(getUsernameById(idUser))) {
                // payee record
                //se voglio utilizzare username, basta manipolare sui metodi getIdByUsername e getUsernameById
                insertExpenseRecord(expenseId, idUser, getIdByUsername(username), amount, payeeAmount, day, month, year, description);

            }
        }
        //! non metto dentro il for di sopra, crea problemi
        updateBalanceFromExpense(expenseId);
        setBalance();
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
    //il codice cerca il valore massimo della colonna "expense_id" nella tabella "expense" del database,
    // e restituisce il valore successivo per creare un nuovo ID univoco per l'aggiunta di una nuova spesa alla tabella.
    private int getNextExpenseId() throws SQLException {
        //La query seleziona il valore massimo della colonna "expense_id" dalla tabella "expense".
        String querySql = "SELECT MAX(expense_id) FROM expense";
        Statement queryStatement = dbConnection.createStatement();
        ResultSet resultSet = queryStatement.executeQuery(querySql);
        //Viene utilizzato il metodo "next()" dell'oggetto ResultSet per spostare il cursore sulla prima riga dei risultati.
        // Se ci sono risultati disponibili, la riga successiva verrà eseguita.
        if (resultSet.next()) {
            //La chiamata al metodo "getInt()" dell'oggetto ResultSet restituisce il valore intero corrispondente al risultato della query.
            return resultSet.getInt(1) + 1;
        } else {
            //Se la query non ha restituito risultati (ad esempio, la tabella "expense" è vuota), viene restituito il valore 1 come valore di ritorno del metodo.
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
        //1 = il primo parametro, id = parametro di "?"
        queryStatement.setInt(1, id);
        ResultSet resultSet = queryStatement.executeQuery();
        //La riga "if (resultSet.next())" controlla se l'oggetto ResultSet
        // ha almeno una riga di risultati, che si sposta sulla prima riga del risultato se presente
        if (resultSet.next()) {
            //Il metodo "getString(1)" dell'oggetto ResultSet restituisce il valore della prima colonna della riga corrente come una stringa.
            // In questo caso, la query seleziona solo una colonna "username" -> perché gli altri sono uguali
            // Quindi il valore restituito dalla riga è il nome utente corrispondente all'ID passato come parametro al metodo.
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

    //---------------------------------------aggiornamento tabella balance--------------------------------------------------------
    public void updateBalanceFromExpense(int expenseId) throws SQLException {
        // Ottieni i payee_id per la spesa con l'id specificato
        List<Integer> payeeIds = new ArrayList<>();
        List<Integer> allId = new ArrayList<>();

        String query = "SELECT DISTINCT payee_id FROM expense WHERE expense_id = ?";
        try (PreparedStatement statement = dbConnection.prepareStatement(query)) {
            statement.setInt(1, expenseId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                payeeIds.add(resultSet.getInt("payee_id"));
            }
        }

        allId = payeeIds;
        allId.add(getPayerIdForExpense(expenseId));

        // Verifica se gli id dei payee(appena estratti) sono presenti nella tabella balance
        for (int id : allId) {
            query = "SELECT * FROM balance WHERE id = ?";
            try (PreparedStatement statement = dbConnection.prepareStatement(query)) {
                statement.setInt(1, id);
                ResultSet resultSet = statement.executeQuery();
                if (!resultSet.next()) {
                    // Il payee non è presente nella tabella balance, lo aggiungi
                    query = "INSERT INTO balance (id, amount_paid) VALUES (?, ?)";
                    //per ogni istruzione MySQL devo stabilire una connessione
                    try (PreparedStatement insertStatement = dbConnection.prepareStatement(query)) {
                        insertStatement.setInt(1, id);
                        insertStatement.setDouble(2, 0.0);
                        insertStatement.executeUpdate();
                    }
                }
            }
        }

        query = "UPDATE balance SET amount_owed = amount_owed + ? WHERE id = ?";
        try (PreparedStatement statement = dbConnection.prepareStatement(query)) {
            //utilizzo del metodo ausiliare
            statement.setDouble(1, getPayeeAmountForExpense(expenseId));
            //versione originale di *

            for (int payeeId : payeeIds) {
                statement.setInt(2, payeeId);
                statement.executeUpdate();
            }

        }

        // Aggiorna la quantità "amount_owed" per il payer nella tabella balance
        query = "UPDATE balance SET amount_paid = amount_paid+ ? WHERE id = ?";
        try (PreparedStatement statement = dbConnection.prepareStatement(query)) {
            statement.setDouble(1, getPayerAmountForExpense(expenseId));
            statement.setInt(2, getPayerIdForExpense(expenseId));
            statement.executeUpdate();
        }
    }

    private double getPayeeAmountForExpense(int expenseId) throws SQLException {
        String query = "SELECT payee_amount FROM expense WHERE expense_id = ? LIMIT 1";
        try (PreparedStatement statement = dbConnection.prepareStatement(query)) {
            statement.setInt(1, expenseId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getDouble("payee_amount");
            } else {
                return 0.0;
            }
        }
    }

    private double getPayerAmountForExpense(int expenseId) throws SQLException {
        String query = "SELECT payer_amount FROM expense WHERE expense_id = ? LIMIT 1";
        try (PreparedStatement statement = dbConnection.prepareStatement(query)) {
            statement.setInt(1, expenseId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getDouble("payer_amount");
            } else {
                return 0.0;
            }
        }
    }

    private int getPayerIdForExpense(int expenseId) throws SQLException {
        String query = "SELECT payer_id FROM expense WHERE expense_id = ? LIMIT 1";
        try (PreparedStatement statement = dbConnection.prepareStatement(query)) {
            statement.setInt(1, expenseId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("payer_id");
            } else {
                return 0;
            }
        }
    }
    public void setBalance() throws SQLException {
        String query = "UPDATE balance SET balance = amount_paid - amount_owed";
        try (PreparedStatement statement = dbConnection.prepareStatement(query)) {
            statement.executeUpdate();
        }
    }





}
