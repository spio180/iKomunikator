package configurator.swing.core;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class XmlReader {
	
	private static String configFilePath;
	private String port;
	private String ip;
	private String limit;

	public static String getConfigFilePath() {
		return configFilePath;
	}

	public static void setConfigFilePath(String configFilePath) {
		XmlReader.configFilePath = configFilePath;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getLimit() {
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}
	
	public void deserializeConfigFromFile(String path) {
		if (path != null) {
			configFilePath = path;
		}

		try {

			File file = new File("server.cfg");
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(file);
			ip = document.getElementsByTagName("IP").item(0).getTextContent();
			port = document.getElementsByTagName("ServerPort").item(0).getTextContent();
			limit = document.getElementsByTagName("ConnectionsLimit").item(0).getTextContent();
		} catch (Exception e) {
			System.out.printf("Error reading the config file\n");
			e.printStackTrace();
		}

	}

}
