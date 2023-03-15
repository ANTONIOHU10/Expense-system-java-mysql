package NetWork.Message;

/**
 * a response for login
 */
public class LoginResponseMessage extends Message{
    private final Boolean ifSuccess;
    private final String message;
    private final int clientId;
    private final int ifAdmin;

    /**
     *
     * @param result 0= failed, 1 = success
     * @param clientId the unique client id
     * @param message the message to be sent
     * @param ifAdmin 0 = User, 1 = Admin
     */
    public LoginResponseMessage(Boolean result,int clientId, String message,int ifAdmin) {
        super(MessageType.LOGIN_RESPONSE);
        ifSuccess = result;
        this.message = message;
        this.clientId = clientId;
        this.ifAdmin = ifAdmin;

    }

    /**
     *
     * @return the message to be sent
     */
    public String getMessage() {
        return message;
    }

    /**
     *
     * @return 0 = failed, 1 = success
     */
    public Boolean getIfSuccess() {
        return ifSuccess;
    }

    /**
     *
     * @return the unique client id
     */
    public int getClientId() {
        return clientId;
    }

    /**
     *
     * @return 0 = User, 1 = Admin
     */
    public int getIfAdmin() {
        return ifAdmin;
    }
}