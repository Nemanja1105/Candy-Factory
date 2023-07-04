package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.PublicKey;
import java.util.Properties;
import java.util.logging.Level;


public class ConfigReader {
	private static final String CONFIG_PATH = "./resources/config.properties";
	private static ConfigReader instance = null;

	private Properties properties=new Properties();

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
	
	public String getLoginURL() {
		return this.properties.getProperty("LOGIN_URL");
	}
	
	public String getRegisterURL() {
		return this.properties.getProperty("REGISTER_URL");
	}
	
	public String getProductsURL() {
		return this.properties.getProperty("PRODUCTS_URL");
	}
	
	public String getMQHost() {
		return this.properties.getProperty("MQ_HOST");
	}
	
	public String getMQUsername() {
		return this.properties.getProperty("MQ_USERNAME");
	}
	
	public String getMQPassword() {
		return this.properties.getProperty("MQ_PASSWORD");
	}
	
	public String getMQQueueName() {
		return this.properties.getProperty("MQ_QUEUE_NAME");
	}
	
	public String getMulticastIp() {
		return this.properties.getProperty("MULTICAST_IP");
	}
	
	public String getMulticastServerPort() {
		return this.properties.getProperty("MULTICAST_SERVER_PORT");
	}
	
	public String getProperty(String key) {
		return this.properties.getProperty(key);
	}

}
