package server.core;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class Transmitter {
    private Connection mConnection;
    private Socket mSenderConnectionSocket;

    public Transmitter(Connection context, Socket outgoingConnectionSocket) {
        mConnection = context;
        mSenderConnectionSocket = outgoingConnectionSocket;
    }

    public void sendBack(String message) throws IOException {
        DataOutputStream outToClient = new DataOutputStream(mSenderConnectionSocket.getOutputStream());
        outToClient.writeBytes(message + '\n');
    }

    public void sendToAll(String message) throws IOException {
        ArrayList<Connection> connectionList = mConnection.getServer().getConnectionList();
        DataOutputStream outToClient = null;
        for (Connection connection : connectionList) {
//            if (connection.getClientSocket() != mSenderConnectionSocket) {
                System.out.println("Message: " + message + " Socket: " + connection.getClientSocket().getRemoteSocketAddress());
                outToClient = new DataOutputStream(connection.getClientSocket().getOutputStream());
                outToClient.writeBytes(message + '\n');
//            }
        }
    }
}
