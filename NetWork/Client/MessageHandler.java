package NetWork.Client;

import NetWork.Message.LoginResponseMessage;
import NetWork.Message.Message;
import NetWork.Message.MessageType;
import NetWork.Message.RegisterResponseMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class MessageHandler {
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private Socket socket;
    //costruttore
    public MessageHandler(Socket socket) throws IOException {
        output = new ObjectOutputStream(socket.getOutputStream());
        input = new ObjectInputStream(socket.getInputStream());
        this.socket = socket;
    }

    //invio del messaggio
    public void send(Message message) throws IOException {
        output.writeObject(message);
    }

    //riceve un messaggio
    public Message receive() throws IOException, ClassNotFoundException {
        return (Message) input.readObject();
    }

    //elabora il messaggio ricevuto
    public void handle(Message message) {
        MessageType type = message.getType();
        switch (type) {

            case LOGIN_RESPONSE:
                handleLoginResponse((LoginResponseMessage) message);
                break;
            case REGISTER_RESPONSE:
                handleRegisterResponse((RegisterResponseMessage) message);
                break;

            // handle other message types here
        }
    }

    private void handleLoginResponse(LoginResponseMessage message) {
        System.out.println("Hai ricevuto un messaggio:  >>>   "+ message.getMessage());
        // handle data reply message
    }
    private void handleRegisterResponse(RegisterResponseMessage message){
        System.out.println("Hai ricevuto un messaggio: >>>    "+ message.getMessage());
    }

    public ObjectInputStream getInput() {
        return input;
    }

    public ObjectOutputStream getOutput() {
        return output;
    }

    public Socket getSocket() {
        return socket;
    }
    // add other handler methods here
}
