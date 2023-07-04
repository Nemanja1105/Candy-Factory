package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;

public class ConfigReader {

	private static final String CONFIG_PATH = "./resources/config.properties";
	private static ConfigReader instance = null;

	private Properties properties = new Properties();

	private ConfigReader() {

		try (FileInputStream input = new FileInputStream(CONFIG_PATH)) {
			this.properties.load(input);
		} catch (IOException e) {
			MyLogger.logger.log(Level.SEVERE,e.getMessage());
			e.printStackTrace();
		}
	}

	public static ConfigReader getInstance() {
		if (instance == null)
			instance = new ConfigReader();
		return instance;
	}
	
	public String getClientPolicy() {
		return this.properties.getProperty("SERVER_POLICY_PATH");
	}
	
	public String getRMIPort() {
		return this.properties.getProperty("RMI_PORT");
	}
}
