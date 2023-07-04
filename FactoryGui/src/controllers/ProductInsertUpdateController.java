package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;

import org.unibl.etf.model.Product;
import org.unibl.etf.service.ProductService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import utils.GuiUtil;
import utils.MyLogger;

public class ProductInsertUpdateController implements Initializable {

	public Product result;
	private boolean type;

	public ProductInsertUpdateController() {
		this.type = true;// insert
	}

	public ProductInsertUpdateController(Product in) {
		this.type = false;// update
		this.result = in;
	}

	@FXML
	private Button addButton;

	@FXML
	private TextField nameTxt;

	@FXML
	private TextField priceTxt;

	@FXML
	private TextField quantityTxt;

	private ProductService service = new ProductService();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		if (type)
			addButton.setText("Dodaj");
		else {
			addButton.setText("Azuraj");
			nameTxt.setText(result.getName());
			priceTxt.setText(Double.toString(result.getPrice()));
			quantityTxt.setText(Integer.toString(result.getAvailableQuantity()));
		}
	}

	@FXML
	void addInsertButtonClicked(ActionEvent event) {
		String name = nameTxt.getText();
		String priceStr = priceTxt.getText();
		String quantityStr = quantityTxt.getText();
		if (name.isBlank() || priceStr.isBlank() || quantityStr.isBlank()) {

			GuiUtil.showDialog("Obavjestenje o gresci", "Morate unijeti sva polja!!1", AlertType.ERROR);
			return;
		}
		double price = 0;
		int quantity = 0;
		try {
			price = Double.parseDouble(priceStr);
			quantity = Integer.parseInt(quantityStr);
		} catch (NumberFormatException e) {
			MyLogger.logger.log(Level.WARNING,e.getMessage());
			GuiUtil.showDialog("Obavjestenje o gresci", "Neispravan format broja!!", AlertType.ERROR);
			return;
		}
		try {
			Product temp = new Product(name, price, quantity);
			if (type) 
			{
				this.service.insert(temp);
				this.result=temp;
			}
			else
			{
				this.result.setName(name);
				this.result.setPrice(price);
				this.result.setAvailableQuantity(quantity);
				this.service.update(this.result);
			}
			
		} catch (Exception e) {
			MyLogger.logger.log(Level.SEVERE,e.getMessage());
			GuiUtil.showDialog("Obavjestenje o gresci", "Desila se greska prilikom komunikacije sa bazom", AlertType.ERROR);
			return;
		}
		Stage stage = (Stage) addButton.getScene().getWindow();
		stage.close();
	}

}
