package NetWork.Message;

public class RegisterResponseMessage extends Message{
    private final Boolean ifSuccess;
    private final String message;
    private final int ifAdmin;

    /**
     *
     * @param result false = registration failed (name taken), true = registration successful
     * @param message message to be sent to client
     * @param ifAdmin 0 = User, 1 = Admin
     */
    public RegisterResponseMessage(Boolean result, String message,int ifAdmin){
        super(MessageType.REGISTER_RESPONSE);
        ifSuccess = result;
        this.message = message;
        this.ifAdmin = ifAdmin;
    }

    /**
     *
     * @return the message to be sent
     */
    public String getMessage() {
        return message;
    }

}
