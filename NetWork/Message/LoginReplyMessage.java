package NetWork.Message;

public class LoginReplyMessage extends Message{
    private String reply;

    public LoginReplyMessage(String reply) {
        super(MessageType.LOGIN_RESPONSE);
        this.reply = reply;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

}