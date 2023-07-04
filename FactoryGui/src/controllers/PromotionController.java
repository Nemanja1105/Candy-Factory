package controllers;

import java.util.logging.Level;

import org.unibl.etf.service.PromotionService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import utils.GuiUtil;
import utils.MyLogger;

public class PromotionController {

    @FXML
    private TextArea promotionTxt;

    @FXML
    private Button publishButton;
    
    private PromotionService promotionService=new PromotionService();

    @FXML
    void publishButtonClicked(ActionEvent event) {
    	String text=promotionTxt.getText();
    	try {
			promotionService.sendPromotion(text);
			promotionTxt.clear();
		} catch (Exception e) {
			MyLogger.logger.log(Level.SEVERE,e.getMessage());
			GuiUtil.showDialog("Obavjestenje o gresci", "Desila se greska prilikom slanja obavjestenja", AlertType.ERROR);
		}
    	
    }

}
