package org.unibl.etf.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;

import javax.enterprise.inject.New;
import javax.net.ssl.SSLServerSocketFactory;

import org.unibl.etf.util.ConfigReader;
import org.unibl.etf.util.MyLogger;

public class Server extends Thread {
	public void run()
	{
		System.out.println("cekam");
		ConfigReader configReader = ConfigReader.getInstance();
		System.setProperty("javax.net.ssl.keyStore", configReader.getKeyStorePath());
		System.setProperty("javax.net.ssl.keyStorePassword", configReader.getKeyStorePassword());
		SSLServerSocketFactory sslSocketFactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
		int port = Integer.parseInt(configReader.getFactoryServerPort());
		try (ServerSocket serverSocket = sslSocketFactory.createServerSocket(port)) {
			while(true) {
				Socket socket=serverSocket.accept();
				new ServerThread(socket);
			}
		}
		catch(IOException e) {
			MyLogger.logger.log(Level.SEVERE,e.getMessage());
			e.printStackTrace();
		}

	}

}
