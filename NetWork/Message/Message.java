package NetWork.Message;

import java.awt.*;
import java.io.Serializable;

public class Message implements Serializable {
    private MessageType type;
    private String sender;
    private String receiver;

    public Message(MessageType messageType) {
        this.type = messageType;

    }

    public MessageType getType() {
        return type;
    }


    public void setType(MessageType type) {
        this.type = type;
    }

}
