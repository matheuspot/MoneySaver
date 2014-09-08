package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ControllerTelaSobre {

    @FXML
    private Button botaoOk;
    
    @FXML
    void fecharJanela(ActionEvent event) {
    	if (event.getSource() == botaoOk)
    		stage.close();
    }
    
    private Stage stage;
    
    public void setStage(Stage stage){
    	this.stage = stage;
    }
}
