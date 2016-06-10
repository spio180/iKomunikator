package server.core;

import common.Message;
import common.Serialization;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Receiver {
    private Connection mConnection;
    private Socket mConnectionSocket;

    Receiver(Connection context, Socket incommingConnectionSocket) {
        mConnection = context;
        mConnectionSocket = incommingConnectionSocket;
    }

    public Message read() throws IOException {
        String message = "";
        Message msg = null;
        BufferedReader bufferReader = new BufferedReader(new InputStreamReader(mConnectionSocket.getInputStream()));
        if (bufferReader.ready()) {
            message = bufferReader.readLine();
            System.out.println("Received RAW data: " + message);
            msg = Serialization.DeSerializeMessage(message);
            System.out.println("Deserialized message: " + msg.toString());
        }
        return msg;
    }

}
