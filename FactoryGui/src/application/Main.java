package application;
	
import java.util.logging.Level;

import org.unibl.etf.util.ConfigReader;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import utils.MyLogger;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent loginForm=FXMLLoader.load(getClass().getResource("start.fxml"));
			Scene scene=new Scene(loginForm);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			MyLogger.logger.log(Level.SEVERE,e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		ConfigReader reader=ConfigReader.getInstance();
		System.setProperty("java.security.policy", reader.getRMIPolicy());
		if (System.getSecurityManager() == null)
			System.setSecurityManager(new SecurityManager());
		launch(args);
	}
}
