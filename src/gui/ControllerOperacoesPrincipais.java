package gui;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class ControllerOperacoesPrincipais {
	
	EventHandler<ActionEvent> eventos = (EventHandler<ActionEvent>) new Eventos();

	 @FXML
	    private Button editarTransacao;

	    @FXML
	    private Button adicionarCategoria;

	    @FXML
	    private Label labelSaldo;

	    @FXML
	    private Button removerTransacao;

	    @FXML
	    private Button removerCategoria;

	    @FXML
	    private Button adicionarTransacao;

	    @FXML
	    private Button editarCategoria;

	    @FXML
	    private Button botaoSair;

	    @FXML
	    private AnchorPane content;
    
    @FXML
	void initialize() {
    	adicionarTransacao.setOnAction(eventos);
    	removerTransacao.setOnAction(eventos);
    	editarTransacao.setOnAction(eventos);
    	adicionarCategoria.setOnAction(eventos);
    	removerCategoria.setOnAction(eventos);
    	editarCategoria.setOnAction(eventos);
    	botaoSair.setOnAction(eventos);
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
			} else if (evento.getSource() == adicionarCategoria) {
				try {
					content.getChildren().setAll(
							FXMLLoader.load(getClass().getResource(
									"TelaAdicionaCategoria.fxml")));
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else if (evento.getSource() == botaoSair) {
				try {
					content.getChildren().setAll(
							FXMLLoader.load(getClass().getResource(
									"TelaPrincipal.fxml")));
					ControllerTelaPrincipal.usuarioAtivo = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			} 
			
		}
    }
}
