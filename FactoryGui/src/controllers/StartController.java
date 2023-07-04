package controllers;

import java.util.logging.Level;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import utils.GuiUtil;
import utils.MyLogger;

public class StartController {

    @FXML
    private Button distributorsButton;

    @FXML
    private Button productButton;

    @FXML
    private Button promotionButton;

    @FXML
    private Button userButton;

    @FXML
    void distributorsButtonClicked(ActionEvent event) {
    	Stage stage = (Stage) userButton.getScene().getWindow();
		stage.hide();
		try {
			GuiUtil.showScene("distributors.fxml", "Rad sa distibuterima");
			stage.show();
		}
		catch(Exception e) {
			MyLogger.logger.log(Level.SEVERE,e.getMessage());
			e.printStackTrace();
			stage.show();
		}
    	
    }

    @FXML
    void productButtonClicked(ActionEvent event) {
    	Stage stage = (Stage) userButton.getScene().getWindow();
		stage.hide();
		try {
			GuiUtil.showScene("products.fxml", "Rad sa proizvodima");
			stage.show();
		}
		catch(Exception e) {
			MyLogger.logger.log(Level.SEVERE,e.getMessage());
			e.printStackTrace();
			stage.show();
		}
    }

    @FXML
    void promotionButtonClicked(ActionEvent event) {
    	Stage stage = (Stage) userButton.getScene().getWindow();
		stage.hide();
		try {
			GuiUtil.showScene("promotion.fxml", "Rad sa promocijama");
			stage.show();
		}
		catch(Exception e) {
			MyLogger.logger.log(Level.SEVERE,e.getMessage());
			e.printStackTrace();
			stage.show();
		}
    }

    @FXML
    void userButtonClicked(ActionEvent event) {

    	Stage stage = (Stage) userButton.getScene().getWindow();
		stage.hide();
		try {
			GuiUtil.showScene("users.fxml", "Rad sa korisnicima");
			stage.show();
		}
		catch(Exception e) {
			MyLogger.logger.log(Level.SEVERE,e.getMessage());
			e.printStackTrace();
			stage.show();
		}
    }

}
