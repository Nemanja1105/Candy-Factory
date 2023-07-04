package org.unibl.etf.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.security.PublicKey;
import java.util.Properties;
import java.util.logging.Level;

public class ConfigReader {
	private static ConfigReader instance = null;
	private static final String CONFIG_PATH2="./resources/config.properties";
	private static final String CONFIG_PATH="../config.properties";
	
	private Properties properties=new Properties();

	private ConfigReader() {
		String path="";
		var str= System.getProperty("user.dir");
		if(str.endsWith("FactoryGui"))
			path=CONFIG_PATH2;
		else
		{
			URL url=getClass().getClassLoader().getResource(CONFIG_PATH);
			path=url.getFile();
		}
		
		try (FileInputStream input = new FileInputStream(path)) {
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

	public String getUserPath() {
		return this.properties.getProperty("USERS_PATH");
	}
	
	public String getFactoryUserPath() {
		return this.properties.getProperty("FACTORY_USERS_PATH");
	}
	
	public String getRedisConnection() {
		return this.properties.getProperty("REDIS");
	}
	
	public String getMulticastAddress() {
		return this.properties.getProperty("MULTICAST_IP");
	}
	
	public String getMulticastPort() {
		return this.properties.getProperty("MULTICAST_SERVER_PORT");
	}public String getKeyStorePath() {
		return this.properties.getProperty("KEYSTORE_PATH");
	}
	
	public String getKeyStorePassword() {
		return this.properties.getProperty("KEYSTORE_PASSWORD");
	}public String getFactoryServerPort() {
		return this.properties.getProperty("FACTORY_SERVER_PORT");
	}
	
	public String getOrdersPath() {
		return this.properties.getProperty("ORDERS_PATH");
	}
	
	public String getRMIPort() {
		return this.properties.getProperty("RMI_PORT");
	}
	
	public String getRMIPolicy() {
		return this.properties.getProperty("RMI_POLICY_CLIENT");
	}

}
