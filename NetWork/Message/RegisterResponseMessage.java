package NetWork.Message;

public class RegisterResponseMessage extends Message{
    private Boolean ifSuccess;
    private String message;
    private int ifAdmin;

    public RegisterResponseMessage(Boolean result, String message,int ifAdmin){
        super(MessageType.REGISTER_RESPONSE);
        ifSuccess = result;
        this.message = message;
        this.ifAdmin = ifAdmin;
    }

    public Boolean getIfSuccess() {
        return ifSuccess;
    }

    public String getMessage() {
        return message;
    }

    public int getIfAdmin() {
        return ifAdmin;
    }
}
