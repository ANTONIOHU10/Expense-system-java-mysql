package NetWork.Message;

import java.util.List;
import java.util.Map;

public class ViewUsernamesResponse extends Message {
    Map<Integer, String> usernames;

    public ViewUsernamesResponse(Map<Integer, String> usernames) {
        super(MessageType.VIEW_USERNAMES_RESPONSE);
        this.usernames = usernames;
    }

    public Map<Integer, String> getUsernames() {
        return usernames;
    }

    public void setUsernames(Map<Integer, String> usernames) {
        this.usernames = usernames;
    }
}
