package common;

import java.util.ArrayList;

/**
 * ServerConfig object that holds server configuration options.
 * <p>
 * String mServerIp: Server Ip Address Optin
 * int mPortNumber: Server Port Number option
 * int mConnectionLimit: Connetion Timeout
 * String mConfigFilePath: Relative path to server config file Default value: "server.cfg";
 * <p>
 * <p>
 * <p>
 * Created by lukasz on 24.04.16.
 */
public class ServerConfig {

    public final static String FILE_PATH = "src/main/resources/server/server.cfg";

    private String mServerIp;
    private int mPortNumber;
    private int mConnectionLimit;
    public ArrayList<String> mForbiddenWords;

    public ServerConfig(String serverIp, int portNumber, int connectionLimit, ArrayList<String> forbiddenWords) {
        mServerIp = serverIp;
        mPortNumber = portNumber;
        mConnectionLimit = connectionLimit;
        mForbiddenWords = forbiddenWords;
    }

    public ServerConfig() { }

    public String getServerIp() {
        return mServerIp;
    }

    public void setServerIp(String serverIp) {
        mServerIp = serverIp;
    }

    public int getPortNumber() {
        return mPortNumber;
    }

    public void setPortNumber(int portNumber) {
        mPortNumber = portNumber;
    }

    public int getConnectionLimit() {
        return mConnectionLimit;
    }

    public void setConnectionLimit(int connectionLimit) {
        mConnectionLimit = connectionLimit;
    }

    public ArrayList<String> getForbiddenWordList() {
        return mForbiddenWords;
    }

    public void setForbiddenWordList(ArrayList<String> forbiddenWords) {
        mForbiddenWords = forbiddenWords;
    }

    public void addForbiddenWord(String nastyWord) {
        mForbiddenWords.add(nastyWord);
    }

    @Override
    public String toString() {
        return "ServerConfig{" +
                "mServerIp='" + mServerIp + ':' + mPortNumber +
                ", mConnectionLimit=" + mConnectionLimit +
                ", mForbiddenWords=" + mForbiddenWords.toString() +
                '}';
    }
}