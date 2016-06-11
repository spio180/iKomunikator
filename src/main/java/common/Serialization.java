package common;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class Serialization {

    public static String SerializeMessage(Message msg) {
        String result;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        XMLEncoder xmlEncoder = new XMLEncoder(baos);
        xmlEncoder.writeObject(msg);
        xmlEncoder.close();
        result = baos.toString();
        result = result.replace("\\n", "").replace("\n", "");
        return result + "\n";
    }

    public static Message DeSerializeMessage(String msg) {
        Message result = new Message();
        InputStream stream = new ByteArrayInputStream(msg.getBytes());
        XMLDecoder decoder;

        try {
            decoder = new XMLDecoder(stream);
            result = (Message)decoder.readObject();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

        return result;
    }

}
