package server.core;

import common.ServerConfig;
import common.ConfigurationLoader;
import common.Message;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.*;

public class Server {
    private static Server mServer;
    private ServerConfig mServerConfig;
    private ArrayList<Connection> mConnectionList = new ArrayList<Connection>();
    private LinkedList<Message> mMessageList = new LinkedList<Message>();

    private Server() throws ConfigurationException {
        init();
        startServer();
    }

    public static Server getInstance() throws ConfigurationException {
        if(mServer == null) {
            mServer = new Server();
        }
        return mServer;
    }

    public void init() throws ConfigurationException {
        System.out.println("Starting TCP Server ");
        mServer = this;

        //Load ServerConfig from xml file
        mServerConfig = ConfigurationLoader.getInstance()
                .filePath(ServerConfig.FILE_PATH)
                .load();
    }

    public void startServer() {
        final ExecutorService connectionPool = Executors.newCachedThreadPool();

        Runnable serverTask = new Runnable() {

            public void run() {
                ServerSocket serverSocket = null;
                try {
                    serverSocket = new ServerSocket(mServerConfig.getPortNumber());
                    System.out.println("Waiting for clients to connect");

                    while (true) {
                        Socket newConnection = serverSocket.accept();
                        connectionPool.submit(new Connection(mServer, newConnection));
                    }

                } catch (IOException e) {
                    System.out.println("Unable to process client request");
                    e.printStackTrace();
                    System.exit(2);
                }

                try {
                    connectionPool.shutdown();
                    connectionPool.awaitTermination(1, TimeUnit.DAYS);
                    System.out.println("Connections closed successfully");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                try {
                    serverSocket.close();
                    System.out.println("Server closed");
                } catch (IOException e) {
                    System.out.println("Server socket could not be closed");
                    e.printStackTrace();
                    System.exit(2);
                }

            }
        };
        Thread serverThread = new Thread(serverTask);
        serverThread.start();
    }


    public ServerConfig getServerConfig() {
        return mServerConfig;
    }

    public ArrayList<Connection> getConnectionList() {
        return mConnectionList;
    }

    public void addConnection(Connection newConnection) {
        mConnectionList.add(newConnection);
    }

    public Connection getConnection(Integer id) {
        return mConnectionList.get(id);
    }

    public void pushMessage(Message msg) {
        mMessageList.add(msg);
    }

    public Message popMessage() {
        return mMessageList.getFirst();
    }
}
