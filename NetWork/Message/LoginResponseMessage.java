package NetWork.Message;

public class LoginResponseMessage extends Message{
    private Boolean ifSuccess;
    private String message;

    public LoginResponseMessage(Boolean result, String message) {
        super(MessageType.LOGIN_RESPONSE);
        ifSuccess = result;
        this.message= message;
    }

    public String getMessage() {
        return message;
    }

    public Boolean getIfSuccess() {
        return ifSuccess;
    }
}