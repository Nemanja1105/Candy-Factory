package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;

import org.unibl.etf.util.MyLogger;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.FactoryUserService;
import utils.GuiUtil;

public class LoginController implements Initializable {

    @FXML
    private Button prijavaButton;

    @FXML
    private TextField usernameTxt;
    
    private FactoryUserService factoryUserService;
   

    @FXML
    void loginButtonClicked(ActionEvent event) {
    	String username=usernameTxt.getText();
    	if(username.isBlank())
    		GuiUtil.showDialog("Obavjestenje o gresci", "Polje za username mora biti popunjeno", AlertType.ERROR);
    	try {
    		if(factoryUserService.checkLogin(username))
    		{
    			FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("factoryUser.fxml"));
    			Scene scene;
    			FactoryUserController factoryController = new FactoryUserController(factoryUserService);
    			fxmlLoader.setController(factoryController);
    			scene = new Scene(fxmlLoader.load());
    			Stage stage1 = new Stage();
    			stage1.setTitle("Rad sa narudzbama");
    			stage1.setScene(scene);
    			stage1.setOnCloseRequest(e->{
    				try {
						factoryUserService.close();
					} catch (IOException e1) {
						MyLogger.logger.log(Level.SEVERE,e1.getMessage());
						e1.printStackTrace();
					}
    			});
    			stage1.show();
    			Stage stage=(Stage)prijavaButton.getScene().getWindow();
    			stage.close();
    		
    		}
    		else
    			GuiUtil.showDialog("Obavjestenje o prijavi", "Nije moguce pronaci korisnicki nalog sa datim imenom",AlertType.ERROR);
			
		} catch (Exception e) {
			MyLogger.logger.log(Level.SEVERE,e.getMessage());
			e.printStackTrace();
			GuiUtil.showDialog("Obavjestenje o gresci","Desila se greska prilikom prijave",AlertType.ERROR);
		}
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
		 factoryUserService=new FactoryUserService();
		}
		catch(Exception e) {
			MyLogger.logger.log(Level.SEVERE,e.getMessage());
			GuiUtil.showDialog("Obavjestenje o gresci",e.getMessage(),AlertType.ERROR);
			Stage stage=(Stage)prijavaButton.getScene().getWindow();
			stage.close();
		}
		
	}

}
