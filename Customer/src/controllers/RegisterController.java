package controllers;

import java.util.logging.Level;

import org.unibl.etf.model.User;

import exceptions.InvalidLoginException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.UserService;
import util.GuiUtil;
import util.MyLogger;

public class RegisterController {

	@FXML
	private TextField addressTxt;

	@FXML
	private TextField companyTxt;

	@FXML
	private TextField emailTxt;

	@FXML
	private PasswordField password1Txt;

	@FXML
	private PasswordField password2Txt;

	@FXML
	private TextField phoneTxt;

	@FXML
	private Button registerButton;

	@FXML
	private TextField usernameTxt;

	private UserService service = new UserService();

	@FXML
	void registerButtonClicked(ActionEvent event) {
		String companyName = companyTxt.getText();
		String address = addressTxt.getText();
		String phone = phoneTxt.getText();
		String email = emailTxt.getText();
		String username = usernameTxt.getText();
		String password1 = password1Txt.getText();
		String password2 = password2Txt.getText();
		if (companyName.isBlank() || address.isBlank() || phone.isBlank() || email.isBlank() || username.isBlank()
				|| password1.isBlank() || password2.isBlank()) {
			GuiUtil.showErrorDialog("Greska", "Niste unijeli sve podatke za registraciju");
			return;
		}
		if (!password1.equals(password2)) {
			GuiUtil.showErrorDialog("Greska", "Unijete lozinke se ne poklapaju");
			return;
		}
		User user = new User(companyName, address, phone, email, username, password2);
		try {
			if (this.service.register(user)) {
				Stage stage = (Stage) registerButton.getScene().getWindow();
				stage.close();
			} else
				GuiUtil.showErrorDialog("Greska", "Registracija neuspjesna! Pokusajte ponovo");
		} 
		catch(InvalidLoginException e) {
			MyLogger.logger.log(Level.WARNING,e.getMessage());
			GuiUtil.showErrorDialog("Greska",e.getMessage());
		}
		catch (RuntimeException e) {
			MyLogger.logger.log(Level.SEVERE,e.getMessage());
			GuiUtil.showErrorDialog("Greska", "Desila se greska prilikom komunikacije sa serverom!!");
		}

	}

}
