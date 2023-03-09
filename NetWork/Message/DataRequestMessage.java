package NetWork.Message;

public class DataRequestMessage extends Message{
    private String dataId;

    public DataRequestMessage(String dataId) {
        super(MessageType.DATA_REQUEST);
        this.dataId = dataId;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }
}
