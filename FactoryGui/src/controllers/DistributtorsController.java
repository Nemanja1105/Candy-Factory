package controllers;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;

import org.unibl.etf.model.Material;

import Service.DistributorService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import utils.GuiUtil;
import utils.MyLogger;

public class DistributtorsController implements Initializable{

    @FXML
    private Button buyButton;

    @FXML
    private ListView<String> distributorsListView;

    @FXML
    private TableColumn<Material, Integer> idColumn;

    @FXML
    private TableView<Material> materialTable;

    @FXML
    private TableColumn<Material, String> nameColumn;

    @FXML
    private Label nameTxt;

    @FXML
    private Pane pane;
    
    @FXML
    private Button refreshButton;

    @FXML
    private TableColumn<Material, Integer> quantityColumn;
    
    private DistributorService service;
    private ObservableList<String> distibutors=FXCollections.observableArrayList();
    private ObservableList<Material> materials=FXCollections.observableArrayList();
    private String currDistributor;
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	pane.setDisable(true);
    	idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		quantityColumn.setCellValueFactory(new PropertyValueFactory<>("availableQuantity"));
		materialTable.setItems(materials);
		distributorsListView.setItems(distibutors);
		try {
			this.service=new DistributorService();
			distibutors.addAll(service.getDistributorsList());
		} catch (Exception e) {
			MyLogger.logger.log(Level.SEVERE,e.getMessage());
			e.printStackTrace();
			GuiUtil.showDialog("Obavjestenje o gresci", "Greska prilikom dobavljanja svih distributera", AlertType.ERROR);
		}
	}
    
    

    @FXML
    void buyButtonClicked(ActionEvent event) {
    	var selected=materialTable.getSelectionModel().getSelectedItem();
    	if(selected==null)
    		return;
    	TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Unos kolicine");
		dialog.setHeaderText("Unesite kolicinu za " + selected.getName());
		dialog.setContentText("Kolicina:");
		dialog.showAndWait().ifPresent(quantity -> {
			try {
				int tmp=Integer.parseInt(quantity);
				if(this.service.buy(selected.getId(), tmp))
				{
					GuiUtil.showDialog("Obavjestenje", "Sirovina je uspjesno kupljena", AlertType.INFORMATION);
					selected.setAvailableQuantity(selected.getAvailableQuantity()-tmp);
					materialTable.refresh();
				}
				else
					GuiUtil.showDialog("Obavjestenje", "Sirovina nije kupljena", AlertType.INFORMATION);
				
			}
			catch(NumberFormatException e) {
				MyLogger.logger.log(Level.WARNING,e.getMessage());
				GuiUtil.showDialog("Obavjestenje o gresci", "Kolicina mora da bude broj!!", AlertType.ERROR);
			}
			catch(Exception e) {
				MyLogger.logger.log(Level.SEVERE,e.getMessage());
				GuiUtil.showDialog("Obavjestenje o gresci", "Greska prilikom kupovine sirovine", AlertType.ERROR);
				return;
			}	
		});
    }
    
    @FXML
    void distributorsListClicked(MouseEvent event) {
    	String selected=distributorsListView.getSelectionModel().getSelectedItem();
    	if(selected==null)
    		return;
    	if(selected.equals(currDistributor))
    		return;
    	try
    	{
    		currDistributor=selected;
    		this.service.connect(selected);
    		materials.clear();
    		materials.addAll(this.service.getAll());
    		pane.setDisable(false);
    		this.nameTxt.setText(selected);
    	}
    	catch(Exception e) {
    		MyLogger.logger.log(Level.SEVERE,e.getMessage());
    		e.printStackTrace();
    		GuiUtil.showDialog("Obavjestenje o gresci", "Greska prilikom komunikacije sa distributerom", AlertType.ERROR);
    	}

    }
    
    @FXML
    void refreshButtonClicked(ActionEvent event) {
    	try {
    		distibutors.clear();
			distibutors.addAll(service.getDistributorsList());
		} catch (Exception e) {
			MyLogger.logger.log(Level.SEVERE,e.getMessage());
			e.printStackTrace();
			GuiUtil.showDialog("Obavjestenje o gresci", "Greska prilikom dobavljanja svih distributera", AlertType.ERROR);
		}

    }


}