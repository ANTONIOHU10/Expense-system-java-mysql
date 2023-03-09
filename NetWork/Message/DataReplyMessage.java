package NetWork.Message;

public class DataReplyMessage extends Message{
    private Object data;

    public DataReplyMessage(Object data) {
        super(MessageType.DATA_REPLY_MESSAGE);
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

