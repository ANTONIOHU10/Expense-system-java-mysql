package NetWork.Message;

/**
 * a request for viewing all usernames
 */
public class ViewUsernamesRequest extends Message{
    public ViewUsernamesRequest() {
        super(MessageType.VIEW_USERNAMES_REQUEST);
    }
}
