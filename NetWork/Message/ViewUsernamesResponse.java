package NetWork.Message;

import java.util.Map;

public class ViewUsernamesResponse extends Message {
    Map<Integer, String> usernames;

    /**
     *
     * @param usernames the usernames and user id map to be sent to Client
     */
    public ViewUsernamesResponse(Map<Integer, String> usernames) {
        super(MessageType.VIEW_USERNAMES_RESPONSE);
        this.usernames = usernames;
    }

    /**
     *
     * @return the map containing username and id
     */
    public Map<Integer, String> getUsernames() {
        return usernames;
    }
}
