package server.core;

import common.Const;
import common.Message;
import common.Serialization;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class Transmitter {
    private Server mServer;
    private Connection mConnection;
    private Socket mSenderConnectionSocket;

    public Transmitter(Connection connection) {
        mConnection = connection;
        mServer = connection.getServer();
        mSenderConnectionSocket = connection.getClientSocket();
    }

    public void sendBack(Message message) throws IOException {
        System.out.println("Sent from server: " + message + " to Socket: " + mSenderConnectionSocket.getRemoteSocketAddress());
        DataOutputStream outToClient = new DataOutputStream(mSenderConnectionSocket.getOutputStream());
        outToClient.writeBytes(Serialization.SerializeMessage(message));
        String type = Const.MSG_DO_WSZYSTKICH;
        String sender = Const.USER_SERVER;
        String receiver = Const.USER_ALL;

        HashMap<String,String> bodyMsg = new HashMap<String,String>();
        bodyMsg.put(Const.BODY,"Zalogowanio użytkownika: " + message.getReceiver());

        Message notify = new Message(type,sender,receiver,bodyMsg);
        sendToAll(notify);
    }

    public void sendToAll(Message message) throws IOException {
        ArrayList<Connection> connectionList = mConnection.getServer().getConnectionList();
        DataOutputStream outToClient = null;
        for (Connection connection : connectionList) {
            if (connection.getClientSocket() != mSenderConnectionSocket) {
                System.out.println("Sent: " + message + " to Socket: " + connection.getClientSocket().getRemoteSocketAddress());
                outToClient = new DataOutputStream(connection.getClientSocket().getOutputStream());
                outToClient.writeBytes(Serialization.SerializeMessage(message));
            }
        }
    }
    public void sendTo(String receiver, Message message) throws IOException {
        ArrayList<Connection> connectionList = mConnection.getServer().getConnectionList();
        DataOutputStream outToClient = null;
        Connection outgoingConnection = mServer.getUser(receiver).getConnection();

        System.out.println("Message from user: " + message.getSender() + " Msg to user: " + receiver +" body: "+ message + " Socket: " + outgoingConnection.getClientSocket().getRemoteSocketAddress());
        outToClient = new DataOutputStream(outgoingConnection.getClientSocket().getOutputStream());
        outToClient.writeBytes(Serialization.SerializeMessage(message));
    }
}
