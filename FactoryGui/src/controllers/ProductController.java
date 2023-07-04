package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;

import org.unibl.etf.model.Product;
import org.unibl.etf.model.User;
import org.unibl.etf.service.ProductService;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import utils.GuiUtil;
import utils.MyLogger;

public class ProductController implements Initializable {

	@FXML
	private Button addButton;

	@FXML
	private Button deleteButton;

	@FXML
	private TableColumn<Product, Integer> idColumn;

	@FXML
	private TableColumn<Product, String> nameColumn;

	@FXML
	private TableColumn<Product, Double> priceColumn;

	@FXML
	private TableView<Product> productsTable;

	@FXML
	private TableColumn<Product, Integer> quantityColumn;

	@FXML
	private Button updateButton;

	private ObservableList<Product> data = FXCollections.observableArrayList();
	private ProductService service = new ProductService();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			data.addAll(service.getAll());
			productsTable.getItems().clear();
			productsTable.setItems(data);
		} catch (Exception e) {
			MyLogger.logger.log(Level.SEVERE,e.getMessage());
			GuiUtil.showDialog("Obavjestenje o gresci", "Desila se greska prilikom komunikacije sa bazom",
					AlertType.ERROR);
		}
		
		idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
		quantityColumn.setCellValueFactory(new PropertyValueFactory<>("availableQuantity"));

	}

	@FXML
	void addButtonClicked(ActionEvent event) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("productInsertUpdate.fxml"));
			Scene scene;
			ProductInsertUpdateController productsController = new ProductInsertUpdateController();
			fxmlLoader.setController(productsController);
			scene = new Scene(fxmlLoader.load());
			Stage stage1 = new Stage();
			stage1.setTitle("Proizvod dodavanje");
			stage1.setScene(scene);
			stage1.showAndWait();
			if (productsController.result != null)
				data.add(productsController.result);
		} catch (Exception e) {
			MyLogger.logger.log(Level.SEVERE,e.getMessage());
			e.printStackTrace();
		}
	}

	@FXML
	void deleteButtonClicked(ActionEvent event) {
		Product product = this.productsTable.getSelectionModel().getSelectedItem();
		if (product == null)
			return;
		try {
			this.service.delete(product.getId());
			this.data.remove(product);
			GuiUtil.showDialog("Obavjestenje", "Brisanje je uspjesno obavljeno!!", AlertType.INFORMATION);
		} catch (Exception e) {
			MyLogger.logger.log(Level.SEVERE,e.getMessage());
			GuiUtil.showDialog("Obavjestenje o gresci", "Desila se greska prilikom brisanja", AlertType.ERROR);
		}
	}

	@FXML
	void updateButtonClicked(ActionEvent event) {
		Product selectedItem = this.productsTable.getSelectionModel().getSelectedItem();
		if (selectedItem == null)
			return;
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("productInsertUpdate.fxml"));
			Scene scene;
			ProductInsertUpdateController productsController = new ProductInsertUpdateController(selectedItem);
			fxmlLoader.setController(productsController);
			scene = new Scene(fxmlLoader.load());
			Stage stage1 = new Stage();
			stage1.setTitle("Proizvod dodavanje");
			stage1.setScene(scene);
			stage1.showAndWait();
			productsTable.refresh();
		} catch (Exception e) {
			MyLogger.logger.log(Level.SEVERE,e.getMessage());
			e.printStackTrace();
		}
	}

}
