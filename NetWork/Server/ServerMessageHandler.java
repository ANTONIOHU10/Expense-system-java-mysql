package NetWork.Server;


import NetWork.Message.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * handle a single message, send, receive
 */
public class ServerMessageHandler {
    private final ObjectOutputStream output;
    private final ObjectInputStream input;
    private final Socket socket;
    //costruttore

    /**
     *
     * @param socket socket used for the client
     * @throws IOException error of I/O
     */
    public ServerMessageHandler(Socket socket) throws IOException {
        output = new ObjectOutputStream(socket.getOutputStream());
        input = new ObjectInputStream(socket.getInputStream());
        this.socket = socket;
    }

    //invio del messaggio

    /**
     *
     * @param message message to be sent
     * @throws IOException error of I/O
     */
    public void send(Message message) throws IOException {
        output.writeObject(message);
    }

    //riceve un messaggio

    /**
     *
     * @return the message received by client
     * @throws IOException error of I/O
     * @throws ClassNotFoundException error of the class used
     */
    public Message receive() throws IOException, ClassNotFoundException {
        return (Message) input.readObject();
    }

    /**
     *
     * @throws IOException error of I/O
     */
    // add other handler methods here
    public void closeSocket() throws IOException {
        output.close();
        if(input != null){
            input.close();
        }
        socket.close();
    }
}
