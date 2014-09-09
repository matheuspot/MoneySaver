package controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.dialog.Dialogs;
import fonte.GerenteDeUsuarios;

public class ControllerCadastro {

	private final EventHandler<ActionEvent> eventos = (EventHandler<ActionEvent>) new Eventos();
	private final EventHandler<KeyEvent> eventosTeclado = (EventHandler<KeyEvent>) new EventosTeclado();
	private final GerenteDeUsuarios gerenteUsuario = new GerenteDeUsuarios();

	@FXML
	private PasswordField pfSenha;
	@FXML
	private TextField tfDicaSenha;
	@FXML
	private PasswordField pfConfirmacaoSenha;
	@FXML
	private TextField tfNome;
	@FXML
	private TextField tfEmail;
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
		tfNome.setOnAction(eventos);
		tfEmail.setOnAction(eventos);
		pfSenha.setOnAction(eventos);
		tfDicaSenha.setOnAction(eventos);
		pfConfirmacaoSenha.setOnAction(eventos);
		botaoCadastrar.setOnAction(eventos);
		botaoVoltar.setOnAction(eventos);
		tfConta.setOnAction(eventos);

		tfDicaSenha.setOnKeyPressed(eventosTeclado);
		tfEmail.setOnKeyPressed(eventosTeclado);
		tfNome.setOnKeyPressed(eventosTeclado);
		tfConta.setOnKeyPressed(eventosTeclado);
		pfSenha.setOnKeyPressed(eventosTeclado);
		pfConfirmacaoSenha.setOnKeyPressed(eventosTeclado);
	}

	private class EventosTeclado implements EventHandler<KeyEvent> {
		@Override
		public void handle(KeyEvent keyEvent) {
			if (keyEvent.getCode() == KeyCode.ENTER) {
				try {
					gerenteUsuario.adicionaUsuario(tfNome.getText(),
							tfEmail.getText(), pfSenha.getText(),
							pfConfirmacaoSenha.getText(),
							tfDicaSenha.getText(), tfConta.getText());

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
					gerenteUsuario.adicionaUsuario(tfNome.getText(),
							tfEmail.getText(), pfSenha.getText(),
							pfConfirmacaoSenha.getText(),
							tfDicaSenha.getText(), tfConta.getText());

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
