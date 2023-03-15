package NetWork.Message;

/**
 * a request for deleting all user information
 */
public class DeleteAllInformationRequest extends Message{
    public DeleteAllInformationRequest() {
        super(MessageType.DELETE_ALL_INFORMATION_REQUEST);
    }
}
