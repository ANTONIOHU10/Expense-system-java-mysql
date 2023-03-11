package NetWork.Message;

public class LoginResponseMessage extends Message{
    private Boolean ifSuccess;
    private String message;
    private int clientId;
    public LoginResponseMessage(Boolean result,int clientId, String message) {
        super(MessageType.LOGIN_RESPONSE);
        ifSuccess = result;
        this.message = message;
        this.clientId = clientId;

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
}