package client.core;

import common.Const;
import common.Message;
import common.Serialization;
import javafx.collections.FXCollections;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class TcpClient {
    public List<String> listOfCom = new ArrayList<String>();
    public ChatWindowController chatController = null;
    private InetAddress connectedAddress;
    private int connectedPort = 0;
    private Socket tcpSocket = null;
    private BufferedReader inBuff;
    private PrintWriter outPrint;
    private Thread listennigThread;
    private volatile boolean running = true;

    public TcpClient() {

    }

    public static String getCurrentIPAddress() {
        InetAddress localaddr;
        try {
            localaddr = InetAddress.getLocalHost();
            System.out.println("Local Address : " + localaddr);
            System.out.println("Local IP: " + localaddr.getHostAddress());
            System.out.println("Local hostname   : " + localaddr.getHostName());
            return localaddr.getHostAddress();

        } catch (UnknownHostException e) {
            System.out.println("getCurrentIPAddress method - UnknownHostException occured");
            e.printStackTrace();
            return "";
        }
    }

    public int connectToServer(String host, int port) {
        try {
            connectedAddress = Inet4Address.getByName(host);
            connectedPort = port;
            this.tcpSocket = new Socket(connectedAddress, connectedPort);
            this.inBuff = new BufferedReader(new InputStreamReader(tcpSocket.getInputStream()));
            this.outPrint = new PrintWriter(tcpSocket.getOutputStream());

			/* Starting listenning thread */
            listennigThread = new Thread(new TcpListeningThread());

            try {
                listennigThread.join();
            } catch (InterruptedException e) {
                System.out.println("Listenning thread could not be joined");
                e.printStackTrace();
            }
            listennigThread.start();
            running = true;

        } catch (SocketException e) {
            System.out.println("Socket Exception occured");
            e.printStackTrace();
            return 1;
        } catch (UnknownHostException e) {
            System.out.println("UnknownHostException occured");
            e.printStackTrace();
            return 2;
        } catch (IOException e) {
            System.out.println("IOException occured");
            e.printStackTrace();
            return 3;
        }

        return 0;
    }

    public void sendMessage(Message message) {
        synchronized (this) {
            if (!this.tcpSocket.isConnected()) {
                System.out.println("No connection to server");
                return;
            }
            System.out.println(message);
            String serializedMessage = Serialization.SerializeMessage(message);
            this.outPrint.println(serializedMessage);
            this.outPrint.flush();
        }
    }

    public void terminateListenningThread() {
        running = false;
    }

    public void setChatController(ChatWindowController chat) {
        chatController = chat;

        if (chatController != null) {
            listOfCom.add("iKomunikator - WITAMY !");
            chatController.textChat.setItems(FXCollections.observableArrayList(listOfCom));

        }
    }

    public void closeSocket() {
        if (tcpSocket != null)
            try {
                tcpSocket.close();
            } catch (IOException e) {
                System.out.println("TCP socket could not be closed");
                e.printStackTrace();

            }
    }

    private class TcpListeningThread implements Runnable {

        public TcpListeningThread() {
            // Nothing to do...
        }

        public void run() {

            while (!running)
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e1) {
                    System.out.println("Listenning thread could not be started - running not set");
                    e1.printStackTrace();
                }
            System.out.println("Listenning thread - Start signal received");

            while (running) {
                try {
                    if (inBuff.ready()) {
                        String serializedMessage = inBuff.readLine();
                        Message message = Serialization.DeSerializeMessage(serializedMessage);
                        System.out.println(message);
                        if (message.getType().equals(Const.MSG_LOGOWANIE_OK)) {
                            this.LogowanieOK(message);
                        }

                        if (message.getType().equals(Const.MSG_DO_UZYTKOWNIKA)) {
                            this.MessageDoWszystkich(message);
                        }

                        if (message.getType().equals(Const.MSG_DO_WSZYSTKICH)) {
                            this.MessageDoWszystkich(message);
                        }

                        if (message.getType().equals(Const.MSG_LOGOWANIE_BLAD)) {

                        }
                    }

                } catch (IOException e) {
                    System.err.println("Connection problem");

                }
            }

            System.out.println("Listenning thread stopped");
        }

        private void LogowanieOK(Message message) {

            System.out.println(message.toString());
            ChatWindowController.loggedUserName = message.getReceiver();
            String messageText = "Zalogowano na serwerze: " + ChatWindowController.loggedUserName;
            listOfCom.add(messageText);

            if (chatController != null) {
                chatController.textChat.setItems(FXCollections.observableArrayList(listOfCom));

            }
        }

        private void MessageDoWszystkich(Message message) {
            if (message.getMessageBody().containsKey(Const.BODY)) {
                String messageText = message.getSender().concat(" - ").concat(message.getMessageBody().get(Const.BODY)).replace("\\n", "").replace("\n", "");

                System.out.println(messageText);
                listOfCom.add(messageText);

                if (chatController != null) {
                    chatController.textChat.setItems(FXCollections.observableArrayList(listOfCom));

                }
            }
        }
    }
}
