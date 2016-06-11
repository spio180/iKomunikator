package server.core;

import common.ConfigurationLoader;
import common.ServerConfig;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Server {
    private static Server mServer;
    private ServerConfig mServerConfig;
    private ServerSocket mServerSocket;
    private final ExecutorService mConnectionPool = Executors.newCachedThreadPool();
    private ArrayList<Connection> mConnectionList = new ArrayList<Connection>();

    private boolean mRunning = true;

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

    private void init() throws ConfigurationException {
        System.out.println("Starting TCP Server ");
        mServer = this;
        //Load ServerConfig from xml file
        mServerConfig = ConfigurationLoader.getInstance()
                .filePath(ServerConfig.FILE_PATH)
                .load();
    }

    public void stopServer() {
        mRunning = false;
    }

    private void startServer() {

        Runnable serverTask = new Runnable() {

            public void run() {
                mServerSocket = null;
                try {
                    mServerSocket = new ServerSocket(mServerConfig.getPortNumber());
                    System.out.println("Waiting for clients to connect");

                    while (mRunning) {
                        Socket newConnection = mServerSocket.accept();
                        mConnectionPool.submit(new Connection(mServer, newConnection));
                    }

                } catch (IOException e) {
                    System.out.println("Unable to process client request");
                    e.printStackTrace();
                    System.exit(2);
                } finally {
                    terminateServer();
                }
            }
        };
        Thread serverThread = new Thread(serverTask);
        serverThread.start();
    }

    private void terminateServer() {
        try {
            mConnectionPool.shutdown();
            mConnectionPool.awaitTermination(1, TimeUnit.DAYS);
            System.out.println("Connections closed successfully");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            mServerSocket.close();
            System.out.println("Server closed");
        } catch (IOException e) {
            System.out.println("Server socket could not be closed");
            e.printStackTrace();
            System.exit(2);
        }
    }

    public void addConnection(Connection newConnection) {
        mConnectionList.add(newConnection);
    }

    /**
     *
     * getters and setters
     *
     */

    public ServerConfig getServerConfig() {
        return mServerConfig;
    }

    public ArrayList<Connection> getConnectionList() {
        return mConnectionList;
    }
}
