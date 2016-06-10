package server.core;

import common.Message;

import java.io.IOException;
import java.net.Socket;

public class Connection implements Runnable {
    private final Server mServer;
    private final Socket mClientSocket;
    private final Receiver mReader;
    private final Transmitter mSender;


    public Connection(Server server, Socket clientSocket) {
        mServer = server;
        mClientSocket = clientSocket;
        mReader = new Receiver(this, mClientSocket);
        mSender = new Transmitter(this, mClientSocket);

    }

    public void run() {
        mServer.addConnection(this);
        System.out.println("New Client Connected: " + mServer.getConnectionList().size());
        Message message = null;
        while (true) {
            try {
                Thread.sleep(10L);
                message = mReader.read();
                if (message != null) {

                    mSender.sendToAll("Nowy uzytkownik: " + message.toString());
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
