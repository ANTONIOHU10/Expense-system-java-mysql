package NetWork.Message;

public class LoginMessage extends Message{
    private String username;
    private String password;

    public LoginMessage(String username, String password) {
        super(MessageType.LOGIN_REQUEST);
        this.username = username;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
