package NetWork;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Server {
    private final int PORT = 8080;
    private ServerSocket serverSocket;
    private Connection dbConnection;

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }

    public void start() {
        try {
            // Inizializza la connessione al database
            dbConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "825310894");

            // Crea una nuova socket del server
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server avviato sulla porta " + PORT);

            // Loop infinito per accettare le connessioni dei client
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Nuova connessione da " + clientSocket.getInetAddress());

                // Crea un nuovo thread per gestire la connessione del client
                ClientHandler clientHandler = new ClientHandler(clientSocket, dbConnection);
                clientHandler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

