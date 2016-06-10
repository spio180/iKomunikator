package server.core;

import common.Message;

import java.io.IOException;
import java.net.Socket;

public class Connection implements Runnable {
    private final Integer mConnectionId;
    private final Server mServer;
    private final Socket mClientSocket;
    private final Receiver mListener;
    private final Transmitter mTransmitter;


    public Connection(Server server, Socket clientSocket) {
        mServer = server;
        mClientSocket = clientSocket;
        mConnectionId = mClientSocket.getPort();
        mListener = new Receiver(this, mClientSocket);
        mTransmitter = new Transmitter(this, mClientSocket);

    }

    public void run() {
        mServer.addConnection(this);
        System.out.print("New Client Connected: " + mServer.getConnectionList().size());
        Message message = null;
        while (true) {
            try {
                Thread.sleep(10L);
                message = mListener.read();
                if (message != null) {
                    if(mServer.getUserList().userIsConnected(message.getSender()) == false){
                        User newUser = new User(message.getSender(), mConnectionId, Status.ONLINE);
                        mServer.getUserList().addUser(newUser);
                    } else {
                        //TODO: Ping if user is still active.
                    }
                    new RouteMessage(message);
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
