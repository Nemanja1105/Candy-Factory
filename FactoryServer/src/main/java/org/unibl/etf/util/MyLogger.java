package org.unibl.etf.util;

import java.util.logging.*;

public class MyLogger {
	public static Logger logger=Logger.getLogger("FactoryLogger");
	public static final String LOG_FILE="loger.log";
	
	static {
		FileHandler fHandler;
		try {
			fHandler=new FileHandler(LOG_FILE,true);
			logger.addHandler(fHandler);
			SimpleFormatter formatter=new SimpleFormatter();
			fHandler.setFormatter(formatter);
			logger.setUseParentHandlers(false);
		} catch (Exception e) {
			System.exit(-1);
		}
	}	
}
