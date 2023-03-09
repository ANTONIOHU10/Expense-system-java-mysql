package NetWork.Message;

public class LoginRequestMessage extends Message{
    private String username;
    private String password;

    public LoginRequestMessage(String username, String password) {
        super(MessageType.LOGIN_REQUEST);
        this.username = username;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

}
