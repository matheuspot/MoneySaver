package gui;

import java.io.IOException;

import fonte.GerenteDeUsuarios;
import fonte.Usuario;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class ControllerTelaPrincipal {

	EventHandler<ActionEvent> eventos = (EventHandler<ActionEvent>) new Eventos();
	public static Usuario usuarioAtivo;

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
	}

	private class Eventos implements EventHandler<ActionEvent> {
		
		GerenteDeUsuarios gerente = new GerenteDeUsuarios();

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
					gerente.login(TFemail.getText(), PFsenha.getText());
					try {
						content.getChildren().setAll(
								FXMLLoader.load(getClass().getResource(
										"TelaDeOperacoesPrincipais.fxml")));
						usuarioAtivo = (Usuario) botaoConectar.getUserData();
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
