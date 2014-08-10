package gui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;

public class ControllerAdicionarTransacao {
	
	EventHandler<ActionEvent> eventos = (EventHandler<ActionEvent>) new Eventos();

	@FXML
    private RadioButton RBprovento;

    @FXML
    private Button botaoCancelar;
    
    @FXML
    private Button botaoAdicionar;

    @FXML
    private AnchorPane content;

    @FXML
    private RadioButton RBdespesa;
    
    private ToggleGroup group = new ToggleGroup();
    
    @FXML
	void initialize() {
    	
    	botaoCancelar.setOnAction(eventos);
    	botaoAdicionar.setOnAction(eventos);
    	RBdespesa.setToggleGroup(group);
    	RBprovento.setToggleGroup(group);
    }
    	
    private class Eventos implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent evento) {
			if (evento.getSource() == botaoCancelar) {
				try {
					content.getChildren().setAll(
							FXMLLoader.load(getClass().getResource(
									"TelaDeOperacoesPrincipais.fxml")));
				} catch (IOException e) {
					e.printStackTrace();
				}				
			}
			
			if (evento.getSource() == botaoAdicionar) {
				
			}
		}
    }
}
