package org.unibl.etf.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;

import javax.servlet.jsp.tagext.TryCatchFinally;

import org.unibl.etf.service.FactoryUserService;
import org.unibl.etf.util.MyLogger;

public class ServerThread extends Thread {

	private Socket socket;
	private BufferedReader reader;
	private PrintWriter writer;
	private FactoryUserService service;

	public ServerThread(Socket socket) {
		try {
			this.socket = socket;
			this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
			this.service = new FactoryUserService();
			start();

		} catch (IOException e) {
			MyLogger.logger.log(Level.SEVERE,e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			String response = "";
			boolean auth = false;
			while (!auth) {
				response = reader.readLine();
				System.out.println(response);
				if (response.startsWith(Protocol.LOGIN.getMessage())) {
					try {
						var tmp = response.split(Protocol.MESSAGE_SEPARATOR.getMessage());
						if (this.service.checkLogin(tmp[1])) {
							auth = true;
							writer.println(Protocol.LOGIN_OK.getMessage());
						} else
							writer.println(Protocol.LOGIN_NOT_OK.getMessage());
					} catch (IndexOutOfBoundsException e) {
						writer.println(Protocol.INVALID_REQUEST);
						MyLogger.logger.log(Level.WARNING,e.getMessage());
						e.printStackTrace();
					}
				} else
					writer.println(Protocol.INVALID_REQUEST.getMessage());
			}

			while (!Protocol.END.getMessage().equals(response = reader.readLine())) {
				if (response.startsWith(Protocol.FINISH_ORDER.getMessage())) {
					try {
						var tmp = response.split(Protocol.MESSAGE_SEPARATOR.getMessage());
						service.saveOrder(tmp[1]);
						writer.println(Protocol.OK.getMessage());
					} catch (IndexOutOfBoundsException e) {
						writer.println(Protocol.INVALID_REQUEST.getMessage());
						MyLogger.logger.log(Level.WARNING,e.getMessage());
						e.printStackTrace();
					} catch (IOException e) {
						writer.println(Protocol.SERVER_ERROR.getMessage());
						MyLogger.logger.log(Level.WARNING,e.getMessage());
						e.printStackTrace();
					}
				} else
					writer.println(Protocol.INVALID_REQUEST.getMessage());

			}
			this.reader.close();
			this.writer.close();
			this.socket.close();

		} catch (Exception e) {
			MyLogger.logger.log(Level.SEVERE,e.getMessage());
			e.printStackTrace();
		}

	}
}
