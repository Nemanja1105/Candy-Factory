package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;

import org.unibl.etf.util.MyLogger;


public class ConfigReader {
	private static final String CONFIG_PATH = ".\\resources\\config.properties";
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
	
	public String getProperty(String key) {
		return this.properties.getProperty(key);
	}
	
	public String getServerIp() {
		return this.properties.getProperty("SERVER_IP");
	}
	
	public String getServerPort() {
		return this.properties.getProperty("SERVER_PORT");
	}
	
	public String getTrustStorePath() {
		return this.properties.getProperty("TRUST_STORE_PATH");
	}
	
	public String getTrustStorePassword() {
		return this.properties.getProperty("TRUST_STORE_PASSWORD");
	}
	
	public String getMqHost() {
		return this.properties.getProperty("MQ_HOST");
	}
	
	public String getMqUsername() {
		return this.properties.getProperty("MQ_USERNAME");
	}
	
	public String getMqPassword() {
		return this.properties.getProperty("MQ_PASSWORD");
	}
	
	public String getMqQueueName() {
		return this.properties.getProperty("MQ_QUEUE_NAME");
	}
	
	public String getSchemaPath() {
		return this.properties.getProperty("SCHEMA_PATH");
	}
}