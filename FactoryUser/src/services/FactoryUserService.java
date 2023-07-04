package services;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.security.KeyStore;
import java.util.logging.Level;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import org.unibl.etf.model.Order;
import org.unibl.etf.server.Protocol;
import org.unibl.etf.util.MyLogger;

import utils.ConfigReader;

public class FactoryUserService {
	private Socket socket;
	private InetAddress address;
	private int port;
	private BufferedReader reader;
	private PrintWriter writer;

	public FactoryUserService() {
		try {
			ConfigReader configReader = ConfigReader.getInstance();
			String trustStorePath = configReader.getTrustStorePath();
			String trustStorePassword = configReader.getTrustStorePassword();
			TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
			trustStore.load(new FileInputStream(trustStorePath), trustStorePassword.toCharArray());
			trustManagerFactory.init(trustStore);
			SSLContext sslContext = SSLContext.getInstance("TLS");
			sslContext.init(null, trustManagerFactory.getTrustManagers(), null);
			SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

			this.address = InetAddress.getByName(configReader.getServerIp());
			this.port = Integer.parseInt(configReader.getServerPort());
			this.socket = sslSocketFactory.createSocket(address, port);
			this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
		} catch (Exception e) {
			MyLogger.logger.log(Level.SEVERE,e.getMessage());
			e.printStackTrace();
			throw new RuntimeException("Desila se greska prilikom povezivanja na server");
		}
	}
	
	public boolean checkLogin(String username)throws IOException {
		writer.println(Protocol.LOGIN.getMessage()+Protocol.MESSAGE_SEPARATOR.getMessage()+username);
		String response=reader.readLine();
		if(Protocol.INVALID_REQUEST.getMessage().equals(response))
			throw new IllegalArgumentException();
		return Protocol.LOGIN_OK.getMessage().equals(response);
	}
	
	public boolean sendOrderDetails(Order orders) throws IOException{
		writer.println(Protocol.FINISH_ORDER.getMessage()+Protocol.MESSAGE_SEPARATOR.getMessage()+orders.toString().replace("\n", "$"));
		String response=reader.readLine();
		return Protocol.OK.getMessage().equals(response);
		
	}
	
	public void close()throws IOException {
		writer.println(Protocol.END.getMessage());
		this.writer.close();
		this.reader.close();
		this.socket.close();
	}

}