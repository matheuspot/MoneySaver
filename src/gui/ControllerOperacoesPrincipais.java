package gui;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;

public class ControllerOperacoesPrincipais {
	
	EventHandler<ActionEvent> eventos = (EventHandler<ActionEvent>) new Eventos();

    @FXML
    private MenuItem adicionarTransacao;
    @FXML
	private AnchorPane content;
    
    @FXML
	void initialize() {
    	adicionarTransacao.setOnAction(eventos);
    }
    	
    private class Eventos implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent evento) {
			if (evento.getSource() == adicionarTransacao) {
				try {
					content.getChildren().setAll(
							FXMLLoader.load(getClass().getResource(
									"TelaAdicionarTransacao.fxml")));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
    }
}
