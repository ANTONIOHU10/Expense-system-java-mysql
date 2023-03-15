package Controller;


import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Users controller.
 */
public class UsersController {
    private final Connection dbConnection;

    /**
     * Instantiates a new Users controller.
     *
     * @param url      "jdbc:mysql//localhost:3306/test"
     * @param username "root"
     * @param password "825310894"
     * @throws SQLException the sql exception
     */
    public UsersController(String url, String username, String password) throws SQLException {
        dbConnection = DriverManager.getConnection(url, username, password);

    }

    /**
     * Register account.
     *
     * @param username username chosen
     * @param password password chosen
     * @param ifAdmin  0 = User, 1 = Admin
     * @throws SQLException error of MySQL connection
     */
    public void registerAccount(String username, String password, int ifAdmin) throws SQLException {

        //Il PreparedStatement è utilizzato per inviare una query SQL al database.
            //Questa query inserisce una nuova riga nella tabella "users" del database "test", con i valori "username" e "password" passati come parametri.
        PreparedStatement preparedStatement = dbConnection.prepareStatement("INSERT INTO users (username, password, role) VALUES (?, ?, ?)");

        //Il metodo "setString" imposta il valore del parametro specificato (in questo caso, "?") con il valore della stringa specificata.
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        if(ifAdmin==1){
            preparedStatement.setString(3, "Admin");
        } else {
            preparedStatement.setString(3, "User");
        }
        // Il valore restituito da questo metodo è il numero di righe modificate dalla query (in questo caso, il numero di righe inserite nella tabella "users").

            preparedStatement.executeUpdate();
        // Il metodo restituisce "true" se il numero di righe inserite è maggiore di zero, altrimenti restituisce "false". Ciò indica se l'inserimento nella tabella è riuscito o meno.
    }

    /**
     * Username exists boolean.
     *
     * @param username the name to be checked
     * @return true = exists, false = not exists
     * @throws SQLException error of MySQL connection
     */
    public boolean usernameExists(String username) throws SQLException {
        //Questa query seleziona tutte le righe dalla tabella "users" del database "test"
        // dove il valore della colonna "username" corrisponde al parametro fornito.
        PreparedStatement preparedStatement = dbConnection.prepareStatement("SELECT * FROM users WHERE username = ?");
        preparedStatement.setString(1, username);
        //Il metodo "executeQuery" restituisce un oggetto ResultSet che contiene i risultati della query.
        ResultSet resultSet = preparedStatement.executeQuery();
        //il metodo restituisce "true" se il ResultSet contiene almeno una riga, altrimenti restituisce "false".
        // Ciò indica se l'username fornito esiste già nella tabella "users".
            // La chiamata "resultSet.next()" restituisce "true" se il ResultSet contiene almeno una riga.
            // Se il ResultSet è vuoto, restituirà "false".
        return resultSet.next();
    }

    /**
     * Login boolean.
     *
     * @param username username for login
     * @param password password for login
     * @return true = success, false = failed
     * @throws SQLException error of MySQL connection
     */
    public boolean login(String username, String password) throws SQLException {
        PreparedStatement preparedStatement = dbConnection.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }

    /**
     * Gets user id.
     *
     * @param username username inserted to get id
     * @return unique id of the user
     * @throws SQLException error of MySQL connection
     */
    public int getUserId(String username) throws SQLException {
        PreparedStatement preparedStatement = dbConnection.prepareStatement("SELECT id FROM users WHERE username = ?");
        preparedStatement.setString(1, username);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt("id");
        } else {
            throw new SQLException("Username non trovato");
        }
    }

    /**
     * Gets user role by id.
     *
     * @param id unique id of the user to be serched
     * @return possible values = "Admin" or "User"
     * @throws SQLException error of MySQL connection
     */
    public String getUserRoleById(int id) throws SQLException {
        String querySql = "SELECT role FROM users WHERE id = ?";
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

    /**
     * Delete all data.
     *
     * @throws SQLException error of MySQL connection
     */
    public void deleteAllData() throws SQLException {
        String deleteUsersSql = "DELETE FROM users";
        String deleteBalanceSql = "DELETE FROM balance";
        String deleteExpenseSql = "DELETE FROM expense";
        try (Statement statement = dbConnection.createStatement()) {
            statement.executeUpdate(deleteUsersSql);
            statement.executeUpdate(deleteBalanceSql);
            statement.executeUpdate(deleteExpenseSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete expenses balance.
     *
     * @throws SQLException error of MySQL connection
     */
    public void deleteExpensesBalance() throws SQLException {
        String deleteBalanceSqlNew = "DELETE FROM balance";
        String deleteExpenseSqlNew = "DELETE FROM expense";
        try (Statement statement = dbConnection.createStatement()) {
            statement.executeUpdate(deleteBalanceSqlNew);
            statement.executeUpdate(deleteExpenseSqlNew);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets users id and username.
     *
     * @return map that contains all usernames and id
     * @throws SQLException error of MySQL connection
     */
    public Map<Integer, String> getUsersIdAndUsername() throws SQLException {
        Map<Integer, String> idUsernameMap = new HashMap<>();
        String querySql = "SELECT id, username FROM users";
        try (Statement queryStatement = dbConnection.createStatement()) {
            ResultSet resultSet = queryStatement.executeQuery(querySql);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String username = resultSet.getString("username");
                idUsernameMap.put(id, username);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idUsernameMap;
    }
    //-------------------------------------------metodi ausiliari per test---------------------------------------------

    /**
     * Count rows in column int.
     *
     * @param tableName  the name of the table in databse
     * @param columnName the name of the column to be consulted
     * @return number of rows
     * @throws SQLException error of database exception query
     */
    public int countRowsInColumn(String tableName, String columnName) throws SQLException {
        String query = "SELECT COUNT(" + columnName + ") FROM " + tableName;
        try (PreparedStatement statement = dbConnection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                return 0;
            }
        }
    }

    /**
     * Gets first user id.
     *
     * @return the unique id of the user
     * @throws SQLException error of database exception query
     */
    public int getFirstUserId() throws SQLException {
        String query = "SELECT id FROM users ORDER BY id  LIMIT 1";
        try (PreparedStatement statement = dbConnection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            } else {
                throw new SQLException("No users found.");
            }
        }
    }

    /**
     * Is table empty boolean.
     *
     * @param tableName name of the table in database
     * @return true = table is empty, false = table is not empty
     * @throws SQLException error of database exception query
     */
    public boolean isTableEmpty(String tableName) throws SQLException {
        String query = "SELECT COUNT(*) FROM " + tableName;
        try (PreparedStatement statement = dbConnection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count == 0;
            } else {
                return true; // Non ci sono righe
            }
        }
    }


}
