package controllers;

import java.io.IOException;

import fonte.Usuario;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class ControllerOperacoesPrincipais {
	
	private EventHandler<ActionEvent> eventos = (EventHandler<ActionEvent>) new Eventos();
	private Usuario usuarioAtivo;

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
    
    public void setUsuario(Usuario usuario){
    	usuarioAtivo = usuario;
    	labelSaldo.setText(usuarioAtivo.getConta().toString());
    }
    	
    private class Eventos implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent evento) {
			if (evento.getSource() == adicionarTransacao) {
				try {
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../gui/TelaAdicionarTransacao.fxml"));     
					Parent root = (Parent)fxmlLoader.load();          
					ControllerAdicionarTransacao controller = fxmlLoader.<ControllerAdicionarTransacao>getController();
					controller.setUsuario(usuarioAtivo);
					content.getChildren().setAll(root);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else if (evento.getSource() == botaoSair) {
				try {
					content.getChildren().setAll(
							FXMLLoader.load(getClass().getResource(
									"../gui/TelaPrincipal.fxml")));
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else if (evento.getSource() == adicionarCategoria) {
				try {
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../gui/TelaAdicionarCategoria.fxml"));     
					Parent root = (Parent)fxmlLoader.load();          
					ControllerAdicionaCategoria controller = fxmlLoader.<ControllerAdicionaCategoria>getController();
					controller.setUsuario(usuarioAtivo);
					content.getChildren().setAll(root);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else if (evento.getSource() == removerCategoria) {
				try {
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../gui/TelaRemoverCategoria.fxml"));     
					Parent root = (Parent)fxmlLoader.load();          
					ControllerRemoverCategoria controller = fxmlLoader.<ControllerRemoverCategoria>getController();
					controller.setUsuario(usuarioAtivo);
					content.getChildren().setAll(root);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
    }
}
