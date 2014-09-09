package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ControllerTelaSobre {

	private Stage stage;

	@FXML
	private Button botaoOk;

	@FXML
	void fecharJanela(ActionEvent event) {
		if (event.getSource() == botaoOk)
			stage.close();
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}
}
