package common;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;

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

    public static Boolean SerializeServerConfig(ServerConfig config_to_serialize, String config_file_path) {
        Boolean result = false;

        try {
            DocumentBuilderFactory documentBuilderFactory= DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder= documentBuilderFactory.newDocumentBuilder();
            Document document= documentBuilder.newDocument();
            Element element= document.createElement("ServerConfig");
            document.appendChild(element);

            Element ip = document.createElement("IP");
            ip.appendChild(document.createTextNode(config_to_serialize.getServerIp()));
            element.appendChild(ip);

            Element port = document.createElement("ServerPort");
            port.appendChild(document.createTextNode(Integer.toString(config_to_serialize.getPortNumber())));
            element.appendChild(port);

            Element connectionLimit = document.createElement("ConnectionsLimit");
            connectionLimit.appendChild(document.createTextNode(Integer.toString(config_to_serialize.getConnectionLimit())));
            element.appendChild(connectionLimit);

            TransformerFactory transformerFactory= TransformerFactory.newInstance();
            Transformer transformer= transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);

            StreamResult streamResult = new StreamResult(new File(config_file_path));
            transformer.transform(source, streamResult);
        }
        catch(Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

        return result;
    }

    public static Boolean DeserializeServerConfig(String config_file_path, ServerConfig configuration) {
        Boolean result = false;
        configuration = new ServerConfig();

        try
        {
            DocumentBuilderFactory documentBuilderFactory= DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(config_file_path);

            NodeList ipNodeList = document.getElementsByTagName("IP");
            for (int i=0; i< ipNodeList.getLength(); i++) {
                Node node = ipNodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    configuration.setServerIp(element.getNodeValue());
                }
            }

            NodeList serverPortNodeList = document.getElementsByTagName("SERVERPORT");
            for (int i=0; i< serverPortNodeList.getLength(); i++) {
                Node node = serverPortNodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    configuration.setPortNumber(Integer.parseInt(element.getNodeValue()));
                }
            }

            NodeList connectionsLimitNodeList = document.getElementsByTagName("CONNECTIONSLIMIT");
            for (int i=0; i< connectionsLimitNodeList.getLength(); i++) {
                Node node = connectionsLimitNodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    configuration.setConnectionLimit(Integer.parseInt(element.getNodeValue()));
                }
            }

            result = true;
        }
        catch(IOException ex) {
            return false;
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

        return result;
    }



}
