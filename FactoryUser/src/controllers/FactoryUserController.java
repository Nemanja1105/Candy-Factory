package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;

import org.unibl.etf.model.Order;
import org.unibl.etf.model.ProductOrder;
import org.unibl.etf.util.MyLogger;

import exceptions.InvalidXMLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import services.FactoryUserService;
import services.MailService;
import services.OrderService;
import utils.GuiUtil;

public class FactoryUserController implements Initializable {

	@FXML
	private Button acceptButton;

	@FXML
	private Button declineButton;

	@FXML
	private Button downloadButton;

	@FXML
	private Label emailLabel;

	@FXML
	private TableColumn<ProductOrder, Integer> idColumn;

	@FXML
	private TableColumn<ProductOrder, String> nameColumn;

	@FXML
	private TableColumn<ProductOrder, Double> priceColumn;

	@FXML
	private TableView<ProductOrder> productsTable;

	@FXML
	private TableColumn<ProductOrder, Integer> quantityColumn;

	@FXML
	private Label usernameLabel;

	@FXML
	private Pane orderPanel;

	private OrderService orderService = new OrderService();
	private FactoryUserService factoryUserService;
	private MailService mailService=new MailService();

	private ObservableList<ProductOrder> orderItems = FXCollections.observableArrayList();

	private Order currOrder;

	public FactoryUserController(FactoryUserService factoryUserService) {
		this.factoryUserService = factoryUserService;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		orderPanel.setDisable(true);
		idColumn.setCellValueFactory(new PropertyValueFactory<>("productId"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<>("productPrice"));
		quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		productsTable.getItems().clear();
		productsTable.setItems(orderItems);
	}

	@FXML
	void acceptButtonClicked(ActionEvent event) {
		if (currOrder == null)
			return;
		currOrder.setStatus(true);
		sendOrderToServer();

	}

	@FXML
	void declineButtonClicked(ActionEvent event) {
		if (currOrder == null)
			return;
		currOrder.setStatus(false);
		sendOrderToServer();
	}

	private void sendOrderToServer() {
		try {
			if (factoryUserService.sendOrderDetails(currOrder)) {
				orderItems.clear();
				usernameLabel.setText("??");
				emailLabel.setText("??");
				orderPanel.setDisable(true);
				GuiUtil.showDialog("Obavjestenje", "Narudzba uspjesno sacuvana", AlertType.INFORMATION);
			}
			else
				GuiUtil.showDialog("Obavjestenje o gresci", "Cuvanje narudzbe nije uspjesno", AlertType.ERROR);

		} catch (Exception e) {
			MyLogger.logger.log(Level.SEVERE,e.getMessage());
			GuiUtil.showDialog("Obavjestenje o gresci", "Desila se greska prilikom cuvanja narudzbe", AlertType.ERROR);
		}
		
		try {
			mailService.sendMail(currOrder);
		} catch (Exception e) {
			MyLogger.logger.log(Level.SEVERE,e.getMessage());
			e.printStackTrace();
			GuiUtil.showDialog("Obavjestenje o gresci", "Desila se greska prilikom slanja mail-a", AlertType.ERROR);
		}
		currOrder = null;
	}

	@FXML
	void downloadButtonClicked(ActionEvent event) {
		try {
			currOrder = orderService.popOrder();
			if (currOrder == null) {
				orderPanel.setDisable(true);
				GuiUtil.showDialog("Obavjestenje", "Trenutno nema novih narudzbi", AlertType.INFORMATION);
				return;
			}
			orderPanel.setDisable(false);
			usernameLabel.setText(currOrder.getClientUsername());
			emailLabel.setText(currOrder.getEmail());
			orderItems.clear();
			orderItems.addAll(currOrder.getOrders());
		}
		catch(InvalidXMLException e) {
			MyLogger.logger.log(Level.SEVERE,e.getMessage());
			GuiUtil.showDialog("Obavjestenje o gresci", "XML dokument nije u skladu sa definisanom semom",
					AlertType.ERROR);
		}
		catch (Exception e) {
			MyLogger.logger.log(Level.SEVERE,e.getMessage());
			e.printStackTrace();
			GuiUtil.showDialog("Obavjestenje o gresci", "Desila se greska prilikom dobavljanja narudzbe",
					AlertType.ERROR);
		}
	}

}
