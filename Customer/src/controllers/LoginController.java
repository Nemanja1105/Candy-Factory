package controllers;

import java.io.IOException;
import java.util.logging.Level;

import org.unibl.etf.model.User;
import org.unibl.etf.model.UserLoginDTO;

import application.Main;
import exceptions.InvalidLoginException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.UserService;
import util.GuiUtil;
import util.MyLogger;

public class LoginController {

	@FXML
	private PasswordField passwordTxt;

	@FXML
	private Button prijavaButton;

	@FXML
	private Hyperlink registerLink;

	@FXML
	private TextField usernameTxt;

	private UserService service = new UserService();

	@FXML
	void loginButtonClicked(ActionEvent event) {
		String username = usernameTxt.getText();
		String password = passwordTxt.getText();
		if (username.isBlank() || password.isBlank()) {
			GuiUtil.showErrorDialog("Greska prilikom prijave", "Polja username i password ne mogu da budu prazna");
			return;
		}
		User user = null;
		try {
			user = this.service.checkLogin(new UserLoginDTO(username, password));

			FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("products.fxml"));
			Scene scene;
			ProductsController productsController = new ProductsController(user);
            fxmlLoader.setController(productsController);
			scene = new Scene(fxmlLoader.load());
			Stage stage1 = new Stage();
			stage1.setTitle("Proizvodi");
			stage1.setScene(scene);
			stage1.show();

			Stage stage = (Stage) prijavaButton.getScene().getWindow();
			stage.close();
			
		} 
		catch(InvalidLoginException e) {
			MyLogger.logger.log(Level.WARNING,e.getMessage());
			GuiUtil.showErrorDialog("Obavjestenje o gresci", e.getMessage());
		}
		catch (RuntimeException e) {
			MyLogger.logger.log(Level.SEVERE,e.getMessage());
			GuiUtil.showErrorDialog("Obavjestenje o gresci","Desila se greska prilikom komunikacije sa serverom!!");
		} catch (IOException e) {
			MyLogger.logger.log(Level.SEVERE,e.getMessage());
			e.printStackTrace();
		}

	}

	@FXML
	void onRegisterButtonClick(ActionEvent event) {
		try {
			GuiUtil.showScene("register.fxml", "registracija");
		} catch (Exception e) {
			MyLogger.logger.log(Level.SEVERE,e.getMessage());
			e.printStackTrace();
		}
	}

}