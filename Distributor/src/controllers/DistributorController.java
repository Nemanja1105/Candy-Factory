package controllers;

import java.net.URL;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ResourceBundle;
import java.util.logging.Level;

import org.unibl.etf.model.Material;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import rmi.DistributorInterface;
import rmi.impl.DistributorImpl;
import utils.ConfigReader;
import utils.GuiUtil;
import utils.MyLogger;

public class DistributorController implements Initializable {

	@FXML
	private Button addMaterialButton;

	@FXML
	private TableColumn<Material, Integer> idColumn;

	@FXML
	private TableView<Material> materialTable;

	@FXML
	private TextField materialTxt;

	@FXML
	private TableColumn<Material, String> nameColumn;

	@FXML
	private TextField nameTxt;

	@FXML
	private Pane panel;

	@FXML
	private TableColumn<Material, Integer> quantityColumn;

	@FXML
	private TextField quantityTxt;

	@FXML
	private Button registerButton;

	private ObservableList<Material> materials = FXCollections.observableArrayList();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		materialTable.getItems().clear();
		materialTable.setItems(materials);

		idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		quantityColumn.setCellValueFactory(new PropertyValueFactory<>("availableQuantity"));

	}

	@FXML
	void addMaterialButtonClicked(ActionEvent event) {
		String name = materialTxt.getText();
		String quantityStr = quantityTxt.getText();
		if (name.isBlank() || quantityStr.isBlank()) {
			GuiUtil.showDialog("Obavjestenje o gresci", "Polja za dodavanje materijala ne mogu biti prazna",
					AlertType.ERROR);
			return;
		}
		int quantity = 0;
		try {
			quantity = Integer.parseInt(quantityStr);

		} catch (NumberFormatException e) {
			MyLogger.logger.log(Level.WARNING,e.getMessage());
			GuiUtil.showDialog("Obavjestenje o gresci", "Nepravilan format broja", AlertType.ERROR);
			return;
		}
		materialTxt.clear();
		quantityTxt.clear();
		synchronized (materials) {
			materials.add(new Material(name, quantity));
		}
	}

	@FXML
	void registerButtonClicked(ActionEvent event) {
		String name = nameTxt.getText();
		if (name.isBlank()) {
			GuiUtil.showDialog("Obavjestenje o gresci", "Polje za naziv distributera ne moze da bude prazno",
					AlertType.ERROR);
			return;
		}
		try {
			ConfigReader reader = ConfigReader.getInstance();
			System.setProperty("java.security.policy", reader.getClientPolicy());
			if (System.getSecurityManager() == null)
				System.setSecurityManager(new SecurityManager());
			DistributorImpl distributorImpl = new DistributorImpl(this.materials);
			DistributorInterface stub = (DistributorInterface) UnicastRemoteObject.exportObject(distributorImpl, 0);
			Registry registry = LocateRegistry.getRegistry(Integer.parseInt(reader.getRMIPort()));
			registry.rebind(name, stub);
			registerButton.setDisable(true);
			GuiUtil.showDialog("Obavjestenje", "Distributer uspjesno povezan na rmi registar", AlertType.INFORMATION);
		} catch (Exception e) {
			MyLogger.logger.log(Level.SEVERE,e.getMessage());
			e.printStackTrace();
			GuiUtil.showDialog("Obavjestenje o gresci", "Greska prilikom pokretanja rmi servera", AlertType.ERROR);
			return;
		}

	}

}
