package NetWork.Server;

import NetWork.Message.Message;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.sql.Connection;


//per Server -> gestisce tutte le connessioni
public class ClientHandler extends Thread {
    private Socket clientSocket;
    private Connection dbConnection;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    //Serve l'oggetto server per fare delle operazioni sulla lista
    private Server server;

    public ClientHandler(Socket clientSocket, Connection dbConnection, Server server) {
        this.clientSocket = clientSocket;
        this.dbConnection = dbConnection;
        this.server = server;
    }

    public void run() {
        try {
            // Inizializza gli stream di input e output per comunicare con il client
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream());

            // Riceve i dati dal client e li elabora
            while (true) {
                try{
                    Message request = (Message) in.readObject();
                    Message response = null;


                    //######### ... elabora il messaggio ricevuto...##########
                    //TODO,  lo switch che elabora i dati
                    System.out.println("rivetuo un messaggio di tipo"+ request.getType());
                    switch(request.getType()) {
                        case ERROR:
                            System.out.println("esegue operazione del caso ERROR");
                            break;
                        case LOGIN_REQUEST:
                            System.out.println("esegue operazione del caso LOGIN");
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
                    break;
                    //per evitare quando termino il client il Server va in shout down
                } catch (EOFException e) {
                    System.out.println("Il client"+ clientSocket.getInetAddress()+ " si è disconnesso(terminato)");
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            // Chiude gli stream e la connessione del client
            try {
                in.close();
                out.close();
                server.removeClientHandler(this);

                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

