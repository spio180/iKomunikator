package server.core;

import common.Message;

import java.util.LinkedList;

/**
 * Created by lukasz on 10.06.16.
 */
public class MessageList {
    private LinkedList<Message> mMessages = new LinkedList<Message>();

    public void pushMessage(Message msg) {
        mMessages.add(msg);
    }

    public Message popMessage() {
        return mMessages.getFirst();
    }
}