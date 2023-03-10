package Controller;


import java.sql.*;

public class DatabaseController {
    private Connection dbConnection;

    /**
     *
     * @param url "jdbc:mysql//localhost:3306/test"
     * @param username "root"
     * @param password "825310894"
     */
    public DatabaseController(String url, String username, String password) throws SQLException {
        dbConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "825310894");

    }

    /*
    public void createTable() throws SQLException {
        Statement statement = dbConnection.createStatement();
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS users (" +
                "id INT NOT NULL AUTO_INCREMENT," +
                "username VARCHAR(50) NOT NULL," +
                "password VARCHAR(50) NOT NULL," +
                "PRIMARY KEY (id)," +
                "UNIQUE KEY (username)" +
                ")");
    }
     */

    public boolean registerAccount(String username, String password) throws SQLException {
        PreparedStatement preparedStatement = dbConnection.prepareStatement("INSERT INTO test.users (username, password) VALUES (?, ?)");
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        int rowsInserted = preparedStatement.executeUpdate();
        return rowsInserted > 0;
    }

    public boolean usernameExists(String username) throws SQLException {
        PreparedStatement preparedStatement = dbConnection.prepareStatement("SELECT * FROM test.users WHERE username = ?");
        preparedStatement.setString(1, username);
        ResultSet resultSet = preparedStatement.executeQuery();
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
}
