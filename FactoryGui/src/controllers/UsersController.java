package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import org.unibl.etf.model.User;
import org.unibl.etf.service.UserService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import utils.GuiUtil;

public class UsersController implements Initializable {

	@FXML
	private TableColumn<User, Boolean> activeStatusColumn;

	@FXML
	private Button activiteButton;

	@FXML
	private TableColumn<User, String> addressColumn;

	@FXML
	private Button blockButon;

	@FXML
	private TableColumn<User, Boolean> blockColumn;

	@FXML
	private TableColumn<User, String> companyNameColumn;

	@FXML
	private Button deleteButton;

	@FXML
	private TableColumn<User, String> emailColumn;

	@FXML
	private TableColumn<User, String> phoneColumn;

	@FXML
	private TableView<User> userTable;

	@FXML
	private Button unblockButton;

	@FXML
	private TableColumn<User, String> usernameColumn;

	private ObservableList<User> users = FXCollections.observableArrayList();

	private UserService userService = new UserService();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		users.addAll(userService.getAll());
		userTable.getItems().clear();
		userTable.setItems(users);
		activeStatusColumn.setCellValueFactory(new PropertyValueFactory<>("activated"));
		addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
		blockColumn.setCellValueFactory(new PropertyValueFactory<>("blocked"));
		companyNameColumn.setCellValueFactory(new PropertyValueFactory<>("companyName"));
		emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
		phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
		usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

	}

	@FXML
	void activiteButtonClicked(ActionEvent event) {
		User user = this.userTable.getSelectionModel().getSelectedItem();
		if (user == null)
			return;
		boolean status = this.userService.activateUser(user.getUsername());
		if (status) {
			user.setActivated(true);
			userTable.refresh();
			GuiUtil.showDialog("Obavjestenje", "Aktiviranje je uspjesno obavljeno!!", AlertType.INFORMATION);
		} else
			GuiUtil.showDialog("Obavjestenje o gresci", "Desila se greska prilikom aktiviranja", AlertType.ERROR);
	}

	@FXML
	void blockButtonClicked(ActionEvent event) {
		User user = this.userTable.getSelectionModel().getSelectedItem();
		if (user == null)
			return;
		boolean status = userService.blockUser(user.getUsername());
		System.out.println(status);
		if (status) {
			user.setBlocked(true);
			userTable.refresh();
			GuiUtil.showDialog("Obavjestenje", "Blokiranje je uspjesno obavljeno!!", AlertType.INFORMATION);
		} else
			GuiUtil.showDialog("Obavjestenje o gresci", "Desila se greska prilikom blokiranja", AlertType.ERROR);

	}

	@FXML
	void deleteButtonClicked(ActionEvent event) {
		User user = this.userTable.getSelectionModel().getSelectedItem();
		if (user == null)
			return;
		boolean status = this.userService.deleteUser(user.getUsername());
		if (status) {
			this.users.remove(user);
			GuiUtil.showDialog("Obavjestenje", "Brisanje je uspjesno obavljeno!!", AlertType.INFORMATION);
		} else
			GuiUtil.showDialog("Obavjestenje o gresci", "Desila se greska prilikom brisanja", AlertType.ERROR);
	}

	@FXML
	void unblockButtonClicked(ActionEvent event) {
		User user = this.userTable.getSelectionModel().getSelectedItem();
		if (user == null)
			return;
		boolean status = this.userService.unblockUser(user.getUsername());
		if (status)
		{
			user.setBlocked(false);
			userTable.refresh();
			GuiUtil.showDialog("Obavjestenje", "Deblokiranje je uspjesno obavljeno!!", AlertType.INFORMATION);
		}
		else
			GuiUtil.showDialog("Obavjestenje o gresci", "Desila se greska prilikom deblokiranje", AlertType.ERROR);
	}

}