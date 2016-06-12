package iKomunikator_server;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XmlWriter {
	private int serverPort;
	private String serverIP;
	private int connectionsLimit;
	private String[] forbiddenWords;

	public XmlWriter(String ip, int port, int connections_limit) {
		serverIP = ip;
		serverPort = port;
		connectionsLimit = connections_limit;

	}

	public XmlWriter(String ip, int port, int connections_limit, String[] fWords) {
		serverIP = ip;
		serverPort = port;
		connectionsLimit = connections_limit;
		forbiddenWords = fWords;

	}

	public Boolean serializeServerConfig() throws Exception {
		Boolean result = false;

		try {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

			Document document = documentBuilder.newDocument();

			Element element = document.createElement("ServerConfig");
			document.appendChild(element);

			Element ip = document.createElement("IP");
			ip.appendChild(document.createTextNode(serverIP));
			element.appendChild(ip);

			Element port = document.createElement("ServerPort");
			port.appendChild(document.createTextNode(Integer.toString(serverPort)));
			element.appendChild(port);

			Element connectionLimit = document.createElement("ConnectionsLimit");
			connectionLimit.appendChild(document.createTextNode(Integer.toString(connectionsLimit)));
			element.appendChild(connectionLimit);
			
			if (forbiddenWords != null) {
//			Element words = document.createElement("fWords");
			for (int i = 0; i < forbiddenWords.length; i++) {
				Element words = document.createElement("fWords"+i);
				words.appendChild(document.createTextNode(forbiddenWords[i]));
//				words.appendChild(document.createTextNode(","));
				element.appendChild(words);
			}

			}

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(document);

			StreamResult streamResult = new StreamResult(new File("Server.cfg"));
			transformer.transform(source, streamResult);
		} catch (Exception ex) {
			return false;
		}

		return result;

	}

}
