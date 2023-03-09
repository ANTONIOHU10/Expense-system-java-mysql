package NetWork.Message;

public class ErrorMessage extends Message{
    private String error;

    public ErrorMessage(String error) {
        super(MessageType.ERROR);
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
