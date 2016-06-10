package server;

import common.ServerConfig;
import org.apache.commons.configuration2.ex.ConfigurationException;
import server.core.Server;

public class Main {

    public static final Boolean DEBUG = Boolean.TRUE;

    /**
     * @param args no args required
     */
    public static void main(String[] args) throws ConfigurationException {

        Server server = Server.getInstance();
        ServerConfig conf = server.getServerConfig();
        System.out.printf("Server address %s:%d | Limit %d\n", conf.getServerIp(), conf.getPortNumber(), conf.getConnectionLimit());
    }
}

