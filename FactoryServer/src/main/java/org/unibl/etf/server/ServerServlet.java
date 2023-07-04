package org.unibl.etf.server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.unibl.etf.util.ConfigReader;
import org.unibl.etf.util.MyLogger;

public class ServerServlet implements ServletContextListener {

	private static Registry registry;

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		new Server().start();
		ConfigReader configReader = ConfigReader.getInstance();
		try {
			ConfigReader reader = ConfigReader.getInstance();
			registry = LocateRegistry.createRegistry(Integer.parseInt(configReader.getRMIPort()));
		} catch (Exception e) {
			MyLogger.logger.log(Level.SEVERE,e.getMessage());
			e.printStackTrace();
		}

	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}

}
