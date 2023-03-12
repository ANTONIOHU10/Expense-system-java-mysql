package NetWork.Message;

public class LoginResponseMessage extends Message{
    private Boolean ifSuccess;
    private String message;
    private int clientId;
    private int ifAdmin;
    public LoginResponseMessage(Boolean result,int clientId, String message,int ifAdmin) {
        super(MessageType.LOGIN_RESPONSE);
        ifSuccess = result;
        this.message = message;
        this.clientId = clientId;
        this.ifAdmin = ifAdmin;

    }

    public String getMessage() {
        return message;
    }

    public Boolean getIfSuccess() {
        return ifSuccess;
    }

    public int getClientId() {
        return clientId;
    }

    public int getIfAdmin() {
        return ifAdmin;
    }
}