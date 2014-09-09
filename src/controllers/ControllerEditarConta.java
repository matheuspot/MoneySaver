package controllers;

import java.io.IOException;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialog.Actions;
import org.controlsfx.dialog.Dialogs;
import fonte.GerenteDeUsuarios;
import fonte.Usuario;

public class ControllerEditarConta {

	private final EventHandler<ActionEvent> eventos = (EventHandler<ActionEvent>) new Eventos();
	private final GerenteDeUsuarios gerente = new GerenteDeUsuarios();
	private Usuario usuarioAtivo;

	@FXML
	private Button botaoVoltar;

	@FXML
	private Label labelAviso;

	@FXML
	private ComboBox<String> cbContas;

	@FXML
	private Button botaoEditar;

	@FXML
	private AnchorPane content;

	@FXML
	private TextField tfNome;

	private ChangeListener<String> changeListener = (ov, t, t1) -> tfNome
			.setText(t1);

	@FXML
	void initialize() {
		botaoEditar.setOnAction(eventos);
		botaoVoltar.setOnAction(eventos);
	}

	public void inicializa(Usuario usuario) {
		usuarioAtivo = usuario;
		cbContas.getItems().addAll(usuarioAtivo.listaNomeContas());
		cbContas.valueProperty().addListener(changeListener);
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
			} else if (evento.getSource() == botaoEditar) {

				if (cbContas.getSelectionModel().getSelectedItem() == null) {
					labelAviso.setText("Selecione uma conta.");
					labelAviso.setVisible(true);

				} else {
					labelAviso.setVisible(false);

					Dialog.Actions resposta = (Actions) Dialogs.create()
							.owner(null).title("MoneySaver").masthead(null)
							.message("Deseja editar a conta?").showConfirm();

					if (resposta == Dialog.Actions.YES) {
						try {
							usuarioAtivo.editaConta(usuarioAtivo
									.pesquisaConta(cbContas.getSelectionModel()
											.getSelectedItem()), tfNome
									.getText());

							gerente.atualizaSistema(usuarioAtivo);

							try {
								FXMLLoader fxmlLoader = new FXMLLoader(
										getClass().getResource(
												"../gui/TelaEditarConta.fxml"));
								Parent root = (Parent) fxmlLoader.load();
								ControllerEditarConta controller = fxmlLoader
										.<ControllerEditarConta> getController();
								controller.inicializa(usuarioAtivo);
								content.getChildren().setAll(root);
							} catch (IOException e) {
								e.printStackTrace();
							}
						} catch (Exception e) {
							labelAviso.setText(e.getMessage());
							labelAviso.setVisible(true);
						}
					} else if (resposta == Dialog.Actions.NO) {
						try {
							FXMLLoader fxmlLoader = new FXMLLoader(getClass()
									.getResource("../gui/TelaEditarConta.fxml"));
							Parent root = (Parent) fxmlLoader.load();
							ControllerEditarConta controller = fxmlLoader
									.<ControllerEditarConta> getController();
							controller.inicializa(usuarioAtivo);
							content.getChildren().setAll(root);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
}
