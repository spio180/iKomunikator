package server.core;

/**
 * Created by lukasz on 10.06.16.
 */
public class User {
    private String mNick;
    private Connection mConnection;
    private String mStatus;

    public User(String nick, Connection connection, String status) {
        mNick = nick;
        mConnection = connection;
        mStatus = status;
    }

    public String getNick() {
        return mNick;
    }

    public Connection getConnection() {
        return mConnection;
    }

    public String getStatus() {
        return mStatus;
    }
}
