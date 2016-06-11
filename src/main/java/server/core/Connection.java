package server.core;

import common.Const;
import common.Message;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

public class Connection implements Runnable {
    private final Integer mConnectionId;
    private final Server mServer;
    private final Socket mClientSocket;
    private final Receiver mListener;
    private final Transmitter mTransmitter;
    private UserList mUserList;


    public Connection(Server server, Socket clientSocket) {
        mServer = server;
        mClientSocket = clientSocket;
        mConnectionId = mClientSocket.getPort();
        mListener = new Receiver(this);
        mTransmitter = new Transmitter(this);
        mUserList = mServer.getRouter().getUserList();

    }

    public void run() {
        mServer.addConnection(this);
        System.out.println("New Client Connected: " + mServer.getConnectionList().size());
        Message message = null;
        while (true) {
            try {
                Thread.sleep(10L);
                message = mListener.read();
                if (message != null) {
                    if (mUserList.userIsConnected(message.getSender()) == false) {
                        User newUser = new User(message.getSender(), this, Status.ONLINE);
                        mUserList.addUser(newUser);
                    } else {
                        //TODO: Ping if user is still active.
                    }
                    if (Const.MSG_LOGOWANIE.equals(message.getType())) {
                        HashMap<String, String> bodyMessage = new HashMap<String, String>();
                        bodyMessage.put("response", "Poprawnie zalogowano.");
                        Message response = new Message(Const.MSG_LOGOWANIE_OK, Const.USER_SERVER, message.getSender(), bodyMessage);
                        mTransmitter.sendBack(response);
                    } else if (Const.MSG_DO_WSZYSTKICH.equals(message.getType())) {
                        HashMap<String, String> bodyMessage = new HashMap<String, String>();
                        bodyMessage.put(Const.BODY, message.getMessageBody().get(Const.BODY));
                        Message response = new Message(Const.MSG_DO_WSZYSTKICH, message.getSender(), Const.USER_ALL , bodyMessage);
                        mTransmitter.sendToAll(response);
                    } else if (Const.MSG_DO_UZYTKOWNIKA.equals(message.getType())) {
                        HashMap<String, String> bodyMessage = new HashMap<String, String>();
                        bodyMessage.put(Const.BODY, message.getMessageBody().get(Const.BODY));
                        Message response = new Message(Const.MSG_DO_WSZYSTKICH, message.getSender(), Const.USER_ALL , bodyMessage);
                        mTransmitter.sendTo(message.getReceiver(),response);
                    } else {
                        HashMap<String, String> bodyMessage = new HashMap<String, String>();
                        bodyMessage.put("response", "Błędny komunikat.");
                        Message response = new Message(Const.MSG_LOGOWANIE_OK, Const.USER_SERVER, message.getSender(), bodyMessage);
                        mTransmitter.sendBack(response);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void close() {
        try {
            mClientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Server getServer() {
        return mServer;
    }

    public Socket getClientSocket() {
        return mClientSocket;
    }
}
