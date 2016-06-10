package server.core;

/**
 * Created by lukasz on 10.06.16.
 */
public class User {
    private String mNick;
    private Integer mConnectionId;
    private String mStatus;

    public User(String nick, Integer connectionId, String status) {
        mNick = nick;
        mConnectionId = connectionId;
        mStatus = status;
    }

    public String getNick() {
        return mNick;
    }

    public Integer getConnectionId() {
        return mConnectionId;
    }

    public String getStatus() {
        return mStatus;
    }
}
