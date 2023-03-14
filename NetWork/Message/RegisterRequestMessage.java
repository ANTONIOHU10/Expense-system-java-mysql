package NetWork.Message;

public class RegisterRequestMessage extends Message{
    private final String username;
    private final String password;
    private final int adminOrNot;

    /**
     *
     * @param username username chosen by client for registration
     * @param password password chosen by client for registration
     * @param adminOrNot 0 = User, 1 = Admin
     */
    public RegisterRequestMessage(String username, String password,int adminOrNot) {
        super(MessageType.REGISTER_REQUEST);
        this.username = username;
        this.password = password;
        this.adminOrNot = adminOrNot;
    }

    /**
     *
     * @return the password chosen
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @return the username chosen
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @return the type of account, 0 = User, 1 = Admin
     */
    public int getAdminOrNot() {
        return adminOrNot;
    }
}
