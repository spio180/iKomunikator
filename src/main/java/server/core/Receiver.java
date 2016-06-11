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

    Receiver(Connection connection) {
        mConnection = connection;
        mConnectionSocket = connection.getClientSocket();
    }

    public Message read() throws IOException {
        String message = "";
        Message msg = null;
        BufferedReader bufferReader = new BufferedReader(new InputStreamReader(mConnectionSocket.getInputStream()));
        if (bufferReader.ready()) {
            message = bufferReader.readLine();
            //System.out.println("Received RAW data: " + message);
            msg = Serialization.DeSerializeMessage(message);
            System.out.println("Receiverd:" + msg.toString());
        }
        return msg;
    }

}


// <?xml version="1.0" encoding="UTF-8"?><java version="1.8.0_91" class="java.beans.XMLDecoder"> <object class="common.Message" id="Message0">  <void property="messageBody">   <void method="put">    <string>LOGIN</string>    <string>Lukasz</string>   </void>  </void>  <void property="receiver">   <string>####</string>  </void>  <void property="sender">   <string>Lukasz</string>  </void>  <void property="type">   <string>LOGIN_REQ</string>  </void> </object></java>
// <?xml version="1.0" encoding="UTF-8"?><java version="1.8.0_91" class="java.beans.XMLDecoder"> <object class="common.Message" id="Message0">  <void property="messageBody">   <void method="put">    <string>MESSAGE_BODY</string>    <string>Wiadomosc</string>   </void>  </void>  <void property="receiver">   <string>****</string>  </void>  <void property="sender">   <string>Lukasz</string>  </void>  <void property="type">   <string>SEND_MSG_ALL</string>  </void> </object></java>
// <?xml version="1.0" encoding="UTF-8"?><java version="1.8.0_91" class="java.beans.XMLDecoder"> <object class="common.Message" id="Message0">  <void property="messageBody">   <void method="put">    <string>MESSAGE_BODY</string>    <string>Wiadomosc</string>   </void>  </void>  <void property="receiver">   <string>KK</string>  </void>  <void property="sender">   <string>Lukasz</string>  </void>  <void property="type">   <string>SEND_MSG_USER</string>  </void> </object></java>