package NetWork.Client;

import NetWork.Message.DataRequestMessage;
import NetWork.Message.LoginMessage;

import java.io.IOException;

public class CommandHandler {
    private MessageHandler messageHandler;

    //costruttore
    public CommandHandler(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    //opearazioni da fare

    //operazione login ->
    public void login(String username, String password) throws IOException {
        LoginMessage message = new LoginMessage(username, password);
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
