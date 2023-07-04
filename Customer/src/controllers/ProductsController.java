package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;

import org.unibl.etf.model.Product;
import org.unibl.etf.model.User;

import com.fasterxml.jackson.core.JsonProcessingException;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.beans.property.SimpleStringProperty;
import models.Order;
import models.ProductOrder;
import service.OrderService;
import service.ProductService;
import util.GuiUtil;
import util.MyLogger;

public class ProductsController implements Initializable {

	private User logUser;

	public ProductsController(User user) {
		this.logUser = user;
	}

	@FXML
	private Label countLbl;

	@FXML
	private TableColumn<ProductOrder, String> orderNameColumn;

	@FXML
	private TableColumn<ProductOrder, Integer> orderQuantityColumn;

	@FXML
	private TableView<ProductOrder> orderTable;

	@FXML
	private TableColumn<Product, Integer> idColumn;

	@FXML
	private TableColumn<Product, String> nameColumn;

	@FXML
	private Button narudzbaButton;

	@FXML
	private TableColumn<Product, Double> priceColumn;

	@FXML
	private TableView<Product> productsTable;

	@FXML
	private TableColumn<Product, Integer> quantityColumn;

	@FXML
	private TextArea promotionTxt;

	private ProductService service = new ProductService();

	private ObservableList<Product> data = FXCollections.observableArrayList();
	private ObservableList<ProductOrder> orders = FXCollections.observableArrayList();

	private OrderService orderService = new OrderService();;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		new PromotionController(promotionTxt).start();
		
		try {
			data.addAll(service.getAll());
		}catch(Exception e) {
			MyLogger.logger.log(Level.SEVERE,e.getMessage());
			GuiUtil.showErrorDialog("Obavjestenje o gresci", "Desila se greska prilikom dobavljanja proizvoda");
		}
		
		
		
		productsTable.getItems().clear();
		productsTable.setItems(data);
		idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
		quantityColumn.setCellValueFactory(new PropertyValueFactory<>("availableQuantity"));

		orderTable.getItems().clear();
		orderTable.setItems(orders);
		orderNameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
		orderQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

		ContextMenu contextMenu = new ContextMenu();
		MenuItem addMenuItem = new MenuItem("Dodaj u narudzbu");
		addMenuItem.setOnAction(e -> addNarudzbaContextMenu());
		contextMenu.getItems().addAll(addMenuItem);

		ContextMenu orderContextMenu = new ContextMenu();
		MenuItem deleteMenuItem = new MenuItem("Obrisi sa narudzbe");
		deleteMenuItem.setOnAction(e -> deleteNarudzbaContextMenu());
		orderContextMenu.getItems().addAll(deleteMenuItem);

		orderTable.setOnMouseClicked(event -> {
			if (event.getButton() == MouseButton.SECONDARY) {
				orderContextMenu.show(productsTable, event.getScreenX(), event.getScreenY());
			}
		});

		productsTable.setOnMouseClicked(event -> {
			if (event.getButton() == MouseButton.SECONDARY) {
				contextMenu.show(productsTable, event.getScreenX(), event.getScreenY());
			}
		});

		countLbl.textProperty().bind(Bindings.createStringBinding(() -> "Veličina liste: " + orders.size(), orders));

	}

	@FXML
	void viewNarudzbaButtonClick(ActionEvent event) {
		Order order = new Order(new ArrayList<>(this.orders), this.logUser.getUsername(), this.logUser.getEmail());
		try {
			this.orderService.sendOrder(order);
			this.orders.clear();
		} catch (Exception e) {
			MyLogger.logger.log(Level.SEVERE,e.getMessage());
			GuiUtil.showErrorDialog("Greska", "Greska prilikom obrade narudze");
		}
	}

	private void deleteNarudzbaContextMenu() {
		ProductOrder order = orderTable.getSelectionModel().getSelectedItem();
		this.orders.remove(order);
	}

	private void addNarudzbaContextMenu() {
		Product selectedProduct = productsTable.getSelectionModel().getSelectedItem();
		if (selectedProduct == null)
			return;
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Unos kolicine");
		dialog.setHeaderText("Unesite kolicinu za " + selectedProduct.getName());
		dialog.setContentText("Kolicina:");

		dialog.showAndWait().ifPresent(quantity -> {
			try {
				int quantityValue = Integer.parseInt(quantity);
				var order = new ProductOrder(selectedProduct, quantityValue);
				int index = this.orders.indexOf(order);
				if (index != -1) {
					var tmp = this.orders.get(index);
					order.setQuantity(tmp.getQuantity() + quantityValue);
					orders.remove(index);
					orders.add(order);
				} else
					orders.add(order);
			} catch (NumberFormatException a) {
				MyLogger.logger.log(Level.WARNING,a.getMessage());
				GuiUtil.showErrorDialog("Greska", "Pogresan format za kolicinu");
			}
		});
	}

}
