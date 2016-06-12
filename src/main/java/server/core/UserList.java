package server.core;

import java.util.HashMap;
import java.util.Set;

/**
 * Created by lukasz on 10.06.16.
 */
public class UserList {
    private HashMap<String, User> mUserList = new HashMap<String, User>();

    public User getUser(String nick) {
        return mUserList.get(nick);
    }

    public void addUser(User user) {
        mUserList.put(user.getNick(), user);
    }

    public void removeUser(User user) {
        mUserList.remove(user.getNick());
    }

    public void removeUser(Connection connection) {
        for(User user : mUserList.values()) {
            if(user.getConnection().equals(connection)) {
                mUserList.remove(user.getNick());
            }
        }
    }

    public Boolean userIsConnected(String nick) {
        return mUserList.containsKey(nick);
    }

    public Set<String> getKeys() {
        return mUserList.keySet();
    }

    @Override
    public String toString() {
        return mUserList.keySet().toString();
    }
}