package NetWork.Message;

public class RegisterRequestMessage extends Message{
    private String username;
    private String password;


    public RegisterRequestMessage(String username, String password) {
        super(MessageType.REGISTER_REQUEST);
        this.username = username;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
