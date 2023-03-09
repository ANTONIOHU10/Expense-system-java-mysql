package NetWork.Server;

import Controller.DatabaseController;
import NetWork.Message.*;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


//per Server -> gestisce tutte le connessioni
public class ClientHandler extends Thread {
    private Socket clientSocket;
    private Connection dbConnection;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private DatabaseController databaseController;

    //Serve l'oggetto server per fare delle operazioni sulla lista
    private Server server;

    public ClientHandler(Socket clientSocket, Connection dbConnection, Server server) {
        this.clientSocket = clientSocket;
        this.dbConnection = dbConnection;
        this.server = server;
    }

    public void run() {
        try {
            //inizializzo il handler per manipolare i messaggi
            ServerMessageHandler serverMessageHandler = new ServerMessageHandler(clientSocket);

            //connessione al database
            try {
                //dbConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","825310894");
                connectToDatabase("jdbc:mysql://localhost:3306/test","root","825310894");
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Connessione al database fallita");
            }

            // Riceve i dati dal client e li elabora
            while (true) {
                try{
                    Message request = serverMessageHandler.receive();
                    Message response = null;


                    //######### ... elabora il messaggio ricevuto...##########
                    //TODO,  lo switch che elabora i dati
                    System.out.println("ricevuto un messaggio di tipo >>>>"+ request.getType());
                    switch(request.getType()) {
                        case ERROR:
                            System.out.println("esegue operazione del caso ERROR");
                            break;
                        case LOGIN_REQUEST:
                            System.out.println("esegue operazione del caso LOGIN");
                            //casting il message base -> loginRequestMessage
                            LoginRequestMessage loginMessage = (LoginRequestMessage) request;
                            String usernameLogin = loginMessage.getUsername();
                            String passwordLogin = loginMessage.getPassword();

                            //verifico se l'account esiste
                            boolean loginSuccess = false;

                            //invio della risposta la client
                            if(loginSuccess){
                                serverMessageHandler.send(new LoginResponseMessage(true,"Login successful!"));
                            } else {
                                serverMessageHandler.send(new LoginResponseMessage(false,"invalid username or password"));
                            }
                            break;
                        case REGISTER_REQUEST:
                            System.out.println("esegue operazione del caso REGISTER_REQUEST");
                            //casting il message base -> loginRequestMessage
                            RegisterRequestMessage registerMessage = (RegisterRequestMessage) request;
                            String usernameRegister = registerMessage.getUsername();
                            String passwordRegister = registerMessage.getPassword();

                            //verifico se l'account esiste
                            boolean registerSuccess=databaseController.usernameExists(usernameRegister);
                            //invio della risposta la client
                            if(registerSuccess){
                                serverMessageHandler.send(new RegisterResponseMessage(true,"Register successful"));
                            } else {
                                serverMessageHandler.send(new RegisterResponseMessage(false,"username already taken"));
                            }

                            break;
                        case LOGIN_RESPONSE:
                            System.out.println("esegue operazione del caso LOGIN_RESPONSE");
                            break;
                        case EXPENSE:
                            System.out.println("esegue operazione del caso EXPENSE");
                            break;
                        case DISCONNECT:
                            System.out.println("esegue operazione del caso DISCONNECT");
                            break;
                        case LOGOUT:
                            System.out.println("esegue operazione del caso LOGOUT");
                            break;
                        case MESSAGE:
                            System.out.println("esegue operazione del caso MESSAGE");
                            break;
                        case DATA_RESPONSE:
                            System.out.println("esegue operazione del caso DATA_REPLY_MESSAGE");
                            break;
                        case DATA_REQUEST:
                            System.out.println("esegue operazione del caso DATA_REQUEST_MESSAGE");
                            break;
                    }

                    //########...invia il messagggio di reply/response...#########
                    if(response != null){
                        out.writeObject(response);
                        out.flush();
                    }

                } catch (SocketException e) {
                    // Eccezione sollevata quando il client si disconnette, quando chiudo il client direttamente
                    System.out.println("Il client "+ clientSocket.getInetAddress()+"si è disconnesso");
                    server.removeClientHandler(this);
                    serverMessageHandler.closeSocket();
                    break;
                    //per evitare quando termino il client il Server va in shout down
                } catch (EOFException e) {
                    System.out.println("Il client"+ clientSocket.getInetAddress()+ " si è disconnesso(terminato)");
                    break;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void connectToDatabase(String url, String username, String password) throws SQLException {
        databaseController= new DatabaseController(url,username,password);
    }

}

