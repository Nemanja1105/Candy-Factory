package util;

import java.io.IOException;

import application.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class GuiUtil {
	public static void showErrorDialog(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }
	
	public static void showScene(String fxml,String title)throws IOException {
		   FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml));
	        Scene scene;
			scene = new Scene(fxmlLoader.load());
	        Stage stage = new Stage();
	        stage.setTitle(title);
	        stage.setScene(scene);
	        stage.show();
	}

}
