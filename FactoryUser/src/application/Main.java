package application;
	
import java.util.logging.Level;

import org.unibl.etf.util.MyLogger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import utils.ConfigReader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent loginForm=FXMLLoader.load(getClass().getResource("login.fxml"));
			Scene scene=new Scene(loginForm);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			MyLogger.logger.log(Level.SEVERE,e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
	//	ConfigReader reader=ConfigReader.getInstance();
	//	 System.setProperty("javax.net.ssl.trustStore",reader.getTrustStorePath());
	 //System.setProperty("javax.net.ssl.trustStorePassword",reader.getTrustStorePassword());
		launch(args);
	}
}
