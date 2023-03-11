package NetWork.Message;

import java.util.List;

public class ViewUsernamesResponse extends Message{
    List<String> usernames;

    public ViewUsernamesResponse(List<String> usernames) {
        super(MessageType.VIEW_USERNAMES_RESPONSE);
        this.usernames = usernames;
    }

    public List<String> getUsernames() {
        return usernames;
    }

    public void setUsernames(List<String> usernames) {
        this.usernames = usernames;
    }
}
