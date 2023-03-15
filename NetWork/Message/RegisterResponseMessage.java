package NetWork.Message;

/**
 * a response for login
 */
public class RegisterResponseMessage extends Message{
    private final String message;

    /**
     *
     * @param result false = registration failed (name taken), true = registration successful
     * @param message message to be sent to client
     * @param ifAdmin 0 = User, 1 = Admin
     */
    public RegisterResponseMessage(Boolean result, String message,int ifAdmin){
        super(MessageType.REGISTER_RESPONSE);
        this.message = message;
    }

    /**
     *
     * @return the message to be sent
     */
    public String getMessage() {
        return message;
    }

}
