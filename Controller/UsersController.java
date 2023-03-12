package Controller;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsersController {
    private Connection dbConnection;

    /**
     *
     * @param url "jdbc:mysql//localhost:3306/test"
     * @param username "root"
     * @param password "825310894"
     */
    public UsersController(String url, String username, String password) throws SQLException {
        dbConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "825310894");

    }

    public boolean registerAccount(String username, String password,int ifAdmin) throws SQLException {

        //Il PreparedStatement è utilizzato per inviare una query SQL al database.
            //Questa query inserisce una nuova riga nella tabella "users" del database "test", con i valori "username" e "password" passati come parametri.
        PreparedStatement preparedStatement = dbConnection.prepareStatement("INSERT INTO test.users (username, password, role) VALUES (?, ?, ?)");

        //Il metodo "setString" imposta il valore del parametro specificato (in questo caso, "?") con il valore della stringa specificata.
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        if(ifAdmin==1){
            preparedStatement.setString(3, "Admin");
        } else {
            preparedStatement.setString(3, "User");
        }
        // Il valore restituito da questo metodo è il numero di righe modificate dalla query (in questo caso, il numero di righe inserite nella tabella "users").
        int rowsInserted = preparedStatement.executeUpdate();
        // Il metodo restituisce "true" se il numero di righe inserite è maggiore di zero, altrimenti restituisce "false". Ciò indica se l'inserimento nella tabella è riuscito o meno.
        return rowsInserted > 0;
    }

    public boolean usernameExists(String username) throws SQLException {
        //Questa query seleziona tutte le righe dalla tabella "users" del database "test"
        // dove il valore della colonna "username" corrisponde al parametro fornito.
        PreparedStatement preparedStatement = dbConnection.prepareStatement("SELECT * FROM test.users WHERE username = ?");
        preparedStatement.setString(1, username);
        //Il metodo "executeQuery" restituisce un oggetto ResultSet che contiene i risultati della query.
        ResultSet resultSet = preparedStatement.executeQuery();
        //il metodo restituisce "true" se il ResultSet contiene almeno una riga, altrimenti restituisce "false".
        // Ciò indica se l'username fornito esiste già nella tabella "users".
            // La chiamata "resultSet.next()" restituisce "true" se il ResultSet contiene almeno una riga.
            // Se il ResultSet è vuoto, restituirà "false".
        return resultSet.next();
    }
    public boolean login(String username, String password) throws SQLException {
        PreparedStatement preparedStatement = dbConnection.prepareStatement("SELECT * FROM test.users WHERE username = ? AND password = ?");
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }
    public int getUserId(String username) throws SQLException {
        PreparedStatement preparedStatement = dbConnection.prepareStatement("SELECT id FROM test.users WHERE username = ?");
        preparedStatement.setString(1, username);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt("id");
        } else {
            throw new SQLException("Username non trovato");
        }
    }

    public List<String> getUsernames() throws SQLException {
        List<String> usernames = new ArrayList<>();
        String selectSql = "SELECT username FROM test.users";
        Statement selectStatement = dbConnection.createStatement();

        //La query SQL può selezionare dati da una o più tabelle del database,
        // e i dati risultanti verranno restituiti come un oggetto ResultSet.
        ResultSet resultSet = selectStatement.executeQuery(selectSql);

        //La chiamata a "next()" restituisce "true" se ci sono ulteriori righe nel ResultSet e sposta il cursore alla prima riga disponibile.
            //Il blocco di codice all'interno del ciclo "while" viene eseguito finché ci sono ulteriori righe nel ResultSet.
        while (resultSet.next()) {
            // il metodo "getString" dell'oggetto ResultSet per recuperare il valore della colonna "username" della riga corrente
                //Il valore viene assegnato alla variabile "username".
            String username = resultSet.getString("username");
            usernames.add(username);
        }
        return usernames;
    }
    public String getUserRoleById(int id) throws SQLException {
        String querySql = "SELECT role FROM test.users WHERE id = ?";
        PreparedStatement queryStatement = dbConnection.prepareStatement(querySql);
        queryStatement.setInt(1, id);
        ResultSet resultSet = queryStatement.executeQuery();
        if (resultSet.next()) {
            String role = resultSet.getString("role");
            if (role.equals("Admin") || role.equals("User")) {
                return role;
            } else {
                throw new SQLException("Invalid user role.");
            }
        } else {
            throw new SQLException("User with ID " + id + " not found.");
        }
    }

    public void deleteAllData() throws SQLException {
        String deleteUsersSql = "DELETE FROM test.users";
        String deleteBalanceSql = "DELETE FROM test.balance";
        String deleteExpenseSql = "DELETE FROM test.expense";
        try (Statement statement = dbConnection.createStatement()) {
            statement.executeUpdate(deleteUsersSql);
            statement.executeUpdate(deleteBalanceSql);
            statement.executeUpdate(deleteExpenseSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteExpensesBalance() throws SQLException {
        String deleteBalanceSqlNew = "DELETE FROM test.balance";
        String deleteExpenseSqlNew = "DELETE FROM test.expense";
        try (Statement statement = dbConnection.createStatement()) {
            statement.executeUpdate(deleteBalanceSqlNew);
            statement.executeUpdate(deleteExpenseSqlNew);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
