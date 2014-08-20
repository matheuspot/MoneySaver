package controllers;

import java.io.IOException;
import fonte.GerenteDeUsuarios;
import fonte.Usuario;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

public class ControllerTelaPrincipal {

	private EventHandler<ActionEvent> eventos = (EventHandler<ActionEvent>) new Eventos();
	private EventHandler<KeyEvent> eventosTeclado = (EventHandler<KeyEvent>) new EventosTeclado();
	private Usuario usuarioAtivo;
	private GerenteDeUsuarios gerente = new GerenteDeUsuarios();

	@FXML
	private Button botaoCadastrar;
	@FXML
	private Button botaoConectar;
	@FXML
	private PasswordField PFsenha;
	@FXML
	private TextField TFemail;
	@FXML
	private AnchorPane content;
	@FXML
	private Label labelAviso;
	

	@FXML
	void initialize() {
		botaoCadastrar.setOnAction(eventos);
		botaoConectar.setOnAction(eventos);
		PFsenha.setOnAction(eventos);
		TFemail.setOnAction(eventos);
		
		labelAviso.setVisible(false);
		
		TFemail.setOnKeyPressed(eventosTeclado);
		PFsenha.setOnKeyPressed(eventosTeclado);
	}

	private class Eventos implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent evento) {
			if (evento.getSource() == botaoCadastrar) {
				try {
					content.getChildren().setAll(
							FXMLLoader.load(getClass().getResource(
									"TelaCadastrar.fxml")));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			else if (evento.getSource() == botaoConectar) {
				try {
					usuarioAtivo = gerente.login(TFemail.getText(), PFsenha.getText());
					try {
						FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TelaDeOperacoesPrincipais.fxml"));     
						Parent root = (Parent)fxmlLoader.load();          
						ControllerOperacoesPrincipais controller = fxmlLoader.<ControllerOperacoesPrincipais>getController();
						controller.setUsuario(usuarioAtivo);
						content.getChildren().setAll(root);
					} catch (IOException e) {
						e.printStackTrace();
					}
				} catch (Exception e) {
					labelAviso.setText(e.getMessage());
					labelAviso.setVisible(true);
				}
			}
		}
	}
	
	private class EventosTeclado implements EventHandler<KeyEvent> {
		@Override
		public void handle(KeyEvent keyEvent) {
	         if (keyEvent.getCode() == KeyCode.ENTER) {
	        	 try {
						usuarioAtivo = gerente.login(TFemail.getText(), PFsenha.getText());
						try {
							FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TelaDeOperacoesPrincipais.fxml"));     
							Parent root = (Parent)fxmlLoader.load();          
							ControllerOperacoesPrincipais controller = fxmlLoader.<ControllerOperacoesPrincipais>getController();
							controller.setUsuario(usuarioAtivo);
							content.getChildren().setAll(root);
						} catch (IOException e) {
							e.printStackTrace();
						}
					} catch (Exception e) {
						labelAviso.setText(e.getMessage());
						labelAviso.setVisible(true);
					}
	         }
		}
	}
}
