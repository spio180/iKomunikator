package server;

import common.ServerConfig;
import org.apache.commons.configuration2.ex.ConfigurationException;
import server.core.Server;

import java.util.Scanner;

public class Main {

    public static final Boolean DEBUG = Boolean.FALSE;

    /**
     * @param args no args required
     */
    public static void main(String[] args) throws ConfigurationException {

        Server server = Server.getInstance();
        ServerConfig conf = server.getServerConfig();
        System.out.printf("Server address %s:%d | Limit %d\n", conf.getServerIp(), conf.getPortNumber(), conf.getConnectionLimit());

        System.out.println("Press return to turn off the server: ");
        Scanner scan = new Scanner(System.in);
        scan.nextLine();
        server.stopServer();
    }
}

