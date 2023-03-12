package NetWork.Message;

public class DeleteAllInformationResponse extends Message{
    private String message;
    public DeleteAllInformationResponse(String message) {
        super(MessageType.DELETE_ALL_INFORMATION_RESPONSE);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
