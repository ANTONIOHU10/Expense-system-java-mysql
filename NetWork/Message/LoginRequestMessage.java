package NetWork.Message;

public class LoginRequestMessage extends Message{
    private final String username;
    private final String password;

    /**
     *
     * @param username username inserted by the client
     * @param password password inserted by the client
     */
    public LoginRequestMessage(String username, String password) {
        super(MessageType.LOGIN_REQUEST);
        this.username = username;
        this.password = password;
    }

    /**
     *
     * @return password inserted
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @return username inserted
     */
    public String getUsername() {
        return username;
    }

}
