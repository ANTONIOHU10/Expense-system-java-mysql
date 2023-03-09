package NetWork.Message;

public class DataResponseMessage extends Message{
    private Object data;

    public DataResponseMessage(Object data) {
        super(MessageType.DATA_RESPONSE);
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

