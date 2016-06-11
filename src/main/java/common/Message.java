package common;

import java.io.Serializable;
import java.util.HashMap;

public class Message implements Serializable {
    private final Long mMessageId = System.currentTimeMillis();
    private String mType;
    private String mSender;
    private String mReceiver;
    private HashMap<String, String> mMessageBody = new HashMap<String, String>();

    public Message() { }

    public Message(String type, String sender, String receiver, HashMap<String, String> messagebody) {
        mType = type;
        mSender = sender;
        mReceiver = receiver;
        mMessageBody = messagebody;
    }

    /**
     *
     * getters and setters
     *
     */

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        this.mType = type;
    }

    public String getSender() {
        return mSender;
    }

    public void setSender(String sender) {
        this.mSender = sender;
    }

    public String getReceiver() {
        return mReceiver;
    }

    public void setReceiver(String receiver) {
        this.mReceiver = receiver;
    }

    public HashMap<String, String> getMessageBody() {
        return mMessageBody;
    }

    public void setMessageBody(HashMap<String, String> messageBody) {
        this.mMessageBody = messageBody;
    }

    public void addLineToMessageBody(String key, String value) {
        this.mMessageBody.put(key,value);
    }

    public Long getMessageId() {
        return mMessageId;
    }

    @Override
    public String toString() {
        return "Message{" +
                "mType='" + mType + '\'' +
                ", mSender='" + mSender + '\'' +
                ", mReceiver='" + mReceiver + '\'' +
                ", mMessageBody=" + mMessageBody.toString() +
                '}';
    }
}
