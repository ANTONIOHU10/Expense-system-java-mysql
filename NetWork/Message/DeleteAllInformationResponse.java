package NetWork.Message;

/**
 * a response for deleting all user information
 */
public class DeleteAllInformationResponse extends Message{
    private final String message;

    /**
     *
     * @param message the message to be sent to Client
     */
    public DeleteAllInformationResponse(String message) {
        super(MessageType.DELETE_ALL_INFORMATION_RESPONSE);
        this.message = message;
    }

    /**
     *
     * @return the message to be sent to Client
     */
    public String getMessage() {
        return message;
    }
}
