package common;

import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;


/**
 * Created by lukasz on 03.06.16.
 * <p>
 * ServerConfig Builder Class
 */
public class ConfigurationLoader {

    private String mConfigFilePath = ServerConfig.FILE_PATH;
    private static ConfigurationLoader mInstance = null;

    public static ConfigurationLoader getInstance() {
        if (mInstance == null) {
            mInstance = new ConfigurationLoader();
        }
        return mInstance;
    }

    private ConfigurationLoader() {
    }

    public ConfigurationLoader filePath(String path) {
        this.mConfigFilePath = path;
        return this;
    }

    /**
     * Load server configuration from xml file from given @path.
     */
    public ServerConfig load() throws ConfigurationException {

        ServerConfig config = new ServerConfig();

        Parameters params = new Parameters();
        FileBasedConfigurationBuilder<XMLConfiguration> builder =
                new FileBasedConfigurationBuilder<XMLConfiguration>(XMLConfiguration.class)
                        .configure(params.xml().setFileName(mConfigFilePath));

        XMLConfiguration readConfig;

        readConfig = builder.getConfiguration();
        config.setPortNumber(readConfig.getInt("ServerPort"));
        config.setServerIp(readConfig.getString("IP"));
        config.setConnectionLimit(readConfig.getInt("ConnectionsLimit"));
//        config.setForbiddenWordList((ArrayList)readConfig.getStringArray("ForbiddenWords"));
        return config;
    }

    public String getConfigFilePath() {
        return mConfigFilePath;
    }
}
