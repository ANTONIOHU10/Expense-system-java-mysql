package NetWork.Message;

public class RegisterResponseMessage extends Message{
    private Boolean ifSuccess;
    private String message;

    public RegisterResponseMessage(Boolean result, String message){
        super(MessageType.REGISTER_RESPONSE);
        ifSuccess = result;
        this.message = message;
    }

    public Boolean getIfSuccess() {
        return ifSuccess;
    }

    public String getMessage() {
        return message;
    }
}
