package gui;

import java.io.IOException;
import org.controlsfx.dialog.Dialogs;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class ControllerTelaPrincipal {

	EventHandler<ActionEvent> eventos = (EventHandler<ActionEvent>) new Eventos();

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
	void initialize() {
		botaoCadastrar.setOnAction(eventos);
		botaoConectar.setOnAction(eventos);
		PFsenha.setOnAction(eventos);
		TFemail.setOnAction(eventos);
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
				Dialogs.create().owner(null).title("MoneySaver").masthead(null)
						.message("Botão conectar!").showInformation();
			}
		}
	}
}
