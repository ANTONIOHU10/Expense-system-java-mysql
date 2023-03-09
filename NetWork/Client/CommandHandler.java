package NetWork.Client;

import NetWork.Message.DataRequestMessage;
import NetWork.Message.LoginRequestMessage;
import NetWork.Message.RegisterRequestMessage;

import java.io.IOException;

public class CommandHandler {
    private MessageHandler messageHandler;

    //costruttore
    public CommandHandler(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    //opearazioni da fare

    //operazione login ->
    public void loginRequest(String username, String password) throws IOException {
        LoginRequestMessage message = new LoginRequestMessage(username, password);
        messageHandler.send(message);
    }

    //operazione register ->
    public void registerRequest(String username, String password) throws IOException {
        RegisterRequestMessage message = new RegisterRequestMessage(username, password);
        messageHandler.send(message);
    }

    public void fetchData() throws IOException {
        DataRequestMessage message = new DataRequestMessage("Hello");
        messageHandler.send(message);
    }

    // add other command methods here

    public void closeClient() throws IOException {
        messageHandler.getOutput().close();
        if(messageHandler.getInput() != null){
            messageHandler.getInput().close();
        }
        messageHandler.getSocket().close();
    }
}
