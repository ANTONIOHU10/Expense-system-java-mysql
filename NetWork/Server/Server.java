package NetWork.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * Server + ClientHandler(socket) + ServerMessageHandler(invia + riceve) = part Server
 */

public class Server {
    private final int PORT = 8080;
    private ServerSocket serverSocket;
    private Connection dbConnection;
    public List<ClientHandler> activeClientHandlers;


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
            System.out.println("                                                     ");
            System.out.println("Server avviato sulla porta " + PORT);

            //Lista dei client che sono connessi
            activeClientHandlers= new ArrayList<ClientHandler>();
            // Loop infinito per accettare le connessioni dei client
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Nuova connessione da " + clientSocket.getInetAddress());

                // Crea un nuovo thread per gestire la connessione del client
                ClientHandler clientHandler = new ClientHandler(clientSocket, dbConnection,this);

                // Aggiungere un ClientHandler nella lista
                activeClientHandlers.add(clientHandler);
                //System.out.println("Ci sono " + activeClientHandlers.size()+" client connessi");
                clientHandler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeClientHandler(ClientHandler handler) {
        activeClientHandlers.remove(handler);
    }
}

