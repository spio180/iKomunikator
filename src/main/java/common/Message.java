package common;

import java.io.Serializable;
import java.util.HashMap;

public class Message implements Serializable {
    private static final long serialVersionUID = 3053534860334056800L;
    private final Long messageId = System.currentTimeMillis();
    private String type;
    private String sender;
    private String receiver;
    private HashMap<String, String> messageBody = new HashMap<String, String>();


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public HashMap<String, String> getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(HashMap<String, String> messageBody) {
        this.messageBody = messageBody;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Message() { }

    public Message(String type, String sender, String receiver, HashMap<String, String> messagebody) {
        this.type = type;
        this.sender = sender;
        this.receiver = receiver;
        this.messageBody = messagebody;
    }

    @Override
    public String toString() {
        return "Message{" +
                "type='" + type + '\'' +
                ", sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", messageBody=" + messageBody.toString() +
                '}';
    }

    public Long getMessageId() {
        return messageId;
    }
}
