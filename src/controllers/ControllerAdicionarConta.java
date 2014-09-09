package controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialog.Actions;
import org.controlsfx.dialog.Dialogs;
import fonte.GerenteDeUsuarios;
import fonte.Usuario;

public class ControllerAdicionarConta {

	private final EventHandler<ActionEvent> eventos = (EventHandler<ActionEvent>) new Eventos();
	private final GerenteDeUsuarios gerente = new GerenteDeUsuarios();
	private Usuario usuarioAtivo;

	@FXML
	private Button botaoAdicionar;

	@FXML
	private Button botaoVoltar;

	@FXML
	private Label labelAviso;

	@FXML
	private AnchorPane content;

	@FXML
	private TextField tfNome;

	@FXML
	void initialize() {
		botaoAdicionar.setOnAction(eventos);
		botaoVoltar.setOnAction(eventos);
	}

	public void inicializa(Usuario usuario) {
		usuarioAtivo = usuario;
	}

	private class Eventos implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent evento) {
			if (evento.getSource() == botaoVoltar) {
				try {
					FXMLLoader fxmlLoader = new FXMLLoader(getClass()
							.getResource("../gui/TelaOperacoesPrincipais.fxml"));
					Parent root = (Parent) fxmlLoader.load();
					ControllerOperacoesPrincipais controller = fxmlLoader
							.<ControllerOperacoesPrincipais> getController();
					controller.inicializa(usuarioAtivo);
					content.getChildren().setAll(root);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else if (evento.getSource() == botaoAdicionar) {
				try {
					usuarioAtivo.adicionaConta(tfNome.getText());

					gerente.atualizaSistema(usuarioAtivo);

					Dialog.Actions resposta = (Actions) Dialogs
							.create()
							.owner(null)
							.title("MoneySaver")
							.masthead(null)
							.message(
									"Conta adicionada. Deseja adicionar uma nova conta?")
							.showConfirm();

					if (resposta == Dialog.Actions.YES) {
						try {
							FXMLLoader fxmlLoader = new FXMLLoader(getClass()
									.getResource(
											"../gui/TelaAdicionarConta.fxml"));
							Parent root = (Parent) fxmlLoader.load();
							ControllerAdicionarConta controller = fxmlLoader
									.<ControllerAdicionarConta> getController();
							controller.inicializa(usuarioAtivo);
							content.getChildren().setAll(root);
						} catch (IOException e) {
							e.printStackTrace();
						}
					} else if (resposta == Dialog.Actions.NO) {
						try {
							FXMLLoader fxmlLoader = new FXMLLoader(
									getClass()
											.getResource(
													"../gui/TelaOperacoesPrincipais.fxml"));
							Parent root = (Parent) fxmlLoader.load();
							ControllerOperacoesPrincipais controller = fxmlLoader
									.<ControllerOperacoesPrincipais> getController();
							controller.inicializa(usuarioAtivo);
							content.getChildren().setAll(root);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				} catch (Exception e) {
					labelAviso.setText(e.getMessage());
					labelAviso.setVisible(true);
				}
			}
		}
	}
}