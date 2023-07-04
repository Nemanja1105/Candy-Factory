package utils;

import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class MyLogger {
	public static Logger logger=Logger.getLogger("CustomerLogger");
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
