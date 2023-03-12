package NetWork.Message;

public class RegisterRequestMessage extends Message{
    private String username;
    private String password;
    private int adminOrNot;


    public RegisterRequestMessage(String username, String password,int adminOrNot) {
        super(MessageType.REGISTER_REQUEST);
        this.username = username;
        this.password = password;
        this.adminOrNot = adminOrNot;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public int getAdminOrNot() {
        return adminOrNot;
    }
}
