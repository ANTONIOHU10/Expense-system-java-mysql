package NetWork.Message;

import java.io.Serializable;

public class Message implements Serializable {
    private final MessageType type;

    /**
     *
     * @param messageType defined by the class MesssageType
     */
    public Message(MessageType messageType) {
        this.type = messageType;
    }

    /**
     *
     * @return one of the type of the Message
     */
    public MessageType getType() {
        return type;
    }


}
