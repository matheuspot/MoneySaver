package controllers;

import java.io.IOException;

import org.controlsfx.dialog.Dialogs;

import fonte.GerenteDeUsuarios;
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

public class ControllerCadastro {

	private EventHandler<ActionEvent> eventos = (EventHandler<ActionEvent>) new Eventos();
	private EventHandler<KeyEvent> eventosTeclado = (EventHandler<KeyEvent>) new EventosTeclado();
	private GerenteDeUsuarios gerenteUsuario = new GerenteDeUsuarios();

	@FXML
	private PasswordField PFsenha;
	@FXML
	private TextField TFdicaSenha;
	@FXML
	private PasswordField PFconfirmacaoSenha;
	@FXML
	private TextField TFnome;
	@FXML
	private TextField TFemail;
	@FXML
	private Button botaoCadastrar;
	@FXML
	private Button botaoVoltar;
	@FXML
	private Label labelAviso;
	@FXML
	private AnchorPane content;
	@FXML
	private TextField tfConta;

	@FXML
	void initialize() {
		TFnome.setOnAction(eventos);
		TFemail.setOnAction(eventos);
		PFsenha.setOnAction(eventos);
		TFdicaSenha.setOnAction(eventos);
		PFconfirmacaoSenha.setOnAction(eventos);
		botaoCadastrar.setOnAction(eventos);
		botaoVoltar.setOnAction(eventos);
		tfConta.setOnAction(eventos);
		
		labelAviso.setVisible(false);
		
		TFdicaSenha.setOnKeyPressed(eventosTeclado);
		TFemail.setOnKeyPressed(eventosTeclado);
		TFnome.setOnKeyPressed(eventosTeclado);
		tfConta.setOnKeyPressed(eventosTeclado);
		PFsenha.setOnKeyPressed(eventosTeclado);
		PFconfirmacaoSenha.setOnKeyPressed(eventosTeclado);
	}
	
	private class EventosTeclado implements EventHandler<KeyEvent> {
		@Override
		public void handle(KeyEvent keyEvent) {
	         if (keyEvent.getCode() == KeyCode.ENTER) {
	        	 try {
	        		 gerenteUsuario.adicionaUsuario(TFnome.getText(),
								TFemail.getText(), PFsenha.getText(),
								PFconfirmacaoSenha.getText(), TFdicaSenha.getText(), tfConta.getText());
						
	        		 Dialogs.create().owner(null).title("MoneySaver")
								.masthead(null).message("Cadastro efetuado!")
								.showInformation();
	        		 	try {
							content.getChildren().setAll(
									FXMLLoader.load(getClass().getResource(
											"../gui/TelaPrincipal.fxml")));
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

	private class Eventos implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent evento) {
			if (evento.getSource() == botaoCadastrar) {

				try {
					gerenteUsuario.adicionaUsuario(TFnome.getText(),
							TFemail.getText(), PFsenha.getText(),
							PFconfirmacaoSenha.getText(), TFdicaSenha.getText(), tfConta.getText());
					Dialogs.create().owner(null).title("MoneySaver")
							.masthead(null).message("Cadastro efetuado!")
							.showInformation();
					try {
						content.getChildren().setAll(
								FXMLLoader.load(getClass().getResource(
										"../gui/TelaPrincipal.fxml")));
					} catch (IOException e) {
						e.printStackTrace();
					}

				} catch (Exception e) {
					labelAviso.setText(e.getMessage());
					labelAviso.setVisible(true);
				}
			}

			if (evento.getSource() == botaoVoltar) {
				try {
					content.getChildren().setAll(
							FXMLLoader.load(getClass().getResource(
									"../gui/TelaPrincipal.fxml")));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
