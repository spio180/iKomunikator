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


    public Connection(Server server, Socket clientSocket) {
        mServer = server;
        mClientSocket = clientSocket;
        mConnectionId = mClientSocket.getPort();
        mListener = new Receiver(this);
        mTransmitter = new Transmitter(this);
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
                    if (mServer.userIsConnected(message.getSender()) == false) {
                        User newUser = new User(message.getSender(), this, Status.ONLINE);
                        mServer.addUser(newUser);
                    }
                    if (Const.MSG_LOGOWANIE.equals(message.getType())) {
                        HashMap<String, String> bodyMessage = new HashMap<String, String>();
                        bodyMessage.put(Const.BODY, "Poprawnie Zalogowany do Serwera.");
                        Message response = new Message(Const.MSG_LOGOWANIE_OK, Const.USER_SERVER, message.getSender(), bodyMessage);
                        mTransmitter.sendBack(response);

                        HashMap<String, String> bodyMsg = new HashMap<String, String>();
                        bodyMsg.put(Const.BODY, "Zalogowano użytkownika: " + message.getSender());
                        Message notify = new Message(Const.MSG_DO_WSZYSTKICH, Const.USER_SERVER, Const.USER_ALL, bodyMsg);
                        mTransmitter.sendToAllOthers(notify);

                        HashMap<String, String> userListMessage = new HashMap<String, String>();
                        for (String nick : mServer.getUsers()) {
                            userListMessage.put(nick, nick);
                        }
                        Message userList = new Message(Const.MSG_LISTA_UZ, Const.USER_SERVER, Const.USER_ALL, userListMessage);
                        mTransmitter.sendToAll(userList);

                        HashMap<String, String> wordList = new HashMap<String, String>();

                        //TODO: Lista ma byc czytana z konfiguracji
                        wordList.put("DUPA", "DUPA");
                        wordList.put("KUWA", "KUWA");
                        Message forbidenWordsList = new Message(Const.MSG_LISTA_WYRAZEN_ZABRONIONYCH, Const.USER_SERVER,
                                message.getSender(), wordList);
                        mTransmitter.sendToAll(forbidenWordsList);

                    } else if (Const.MSG_DO_WSZYSTKICH.equals(message.getType())) {
                        HashMap<String, String> bodyMessage = new HashMap<String, String>();
                        bodyMessage.put(Const.BODY, message.getMessageBody().get(Const.BODY));
                        Message response = new Message(Const.MSG_DO_WSZYSTKICH, message.getSender(), Const.USER_ALL, bodyMessage);
                        mTransmitter.sendToAllOthers(response);
                    } else if (Const.MSG_DO_UZYTKOWNIKA.equals(message.getType())) {
                        HashMap<String, String> bodyMessage = new HashMap<String, String>();
                        bodyMessage.put(Const.BODY, message.getMessageBody().get(Const.BODY));
                        Message response = new Message(Const.MSG_DO_UZYTKOWNIKA, message.getSender(), message.getReceiver(), bodyMessage);
                        mTransmitter.sendTo(message.getReceiver(), response);
                    } else if (Const.MSG_WYLOGOWANIE.equals(message.getType())) {
                        //TODO: Verify and remove connection - stop thread?
                        String userLogout =  message.getSender();
                        System.out.println("User logout: " + userLogout);

                        mServer.getUsers().remove(userLogout);

                        if (!mServer.getUsers().isEmpty()) {

                            HashMap<String, String> userListMessage = new HashMap<String, String>();
                            for (String nick : mServer.getUsers()) {
                                userListMessage.put(nick, nick);
                            }
                            Message userList = new Message(Const.MSG_LISTA_UZ, Const.USER_SERVER, Const.USER_ALL, userListMessage);
                            mTransmitter.sendToAllOthers(userList);
                        }

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
