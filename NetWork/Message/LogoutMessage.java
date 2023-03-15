package NetWork.Message;

/**
 * a request for logout
 */
public class LogoutMessage extends Message{
    public LogoutMessage(){
        super(MessageType.LOGOUT);
    }
}
