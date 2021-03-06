package controllers;

import java.io.IOException;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialog.Actions;
import org.controlsfx.dialog.Dialogs;
import fonte.Categoria;
import fonte.GerenteDeUsuarios;
import fonte.Usuario;

public class ControllerEditarCategoria {

	private final EventHandler<ActionEvent> eventos = (EventHandler<ActionEvent>) new Eventos();
	private final GerenteDeUsuarios gerente = new GerenteDeUsuarios();
	private Usuario usuarioAtivo;

	@FXML
	private Button botaoVoltar;

	@FXML
	private ColorPicker cor;

	@FXML
	private ComboBox<Categoria> cbCategoria;

	@FXML
	private TextField tfNome;

	@FXML
	private Button botaoEditar;

	@FXML
	private AnchorPane content;

	@FXML
	private Label labelAviso;

	private List<Categoria> categorias;

	private ChangeListener<Categoria> changeListener = (ov, t, t1) -> {
		tfNome.setText(t1.getNome());
		cor.setValue(Color.valueOf(t1.getCor()));
	};

	@FXML
	void initialize() {
		botaoVoltar.setOnAction(eventos);
		botaoEditar.setOnAction(eventos);
	}

	public void inicializa(Usuario usuario) {
		usuarioAtivo = usuario;

		categorias = usuarioAtivo.getCategorias();
		cbCategoria.valueProperty().addListener(changeListener);
		cbCategoria.getItems().addAll(categorias);
		cbCategoria
				.setCellFactory(new Callback<ListView<Categoria>, ListCell<Categoria>>() {
					@Override
					public ListCell<Categoria> call(ListView<Categoria> param) {
						final ListCell<Categoria> cell = new ListCell<Categoria>() {
							@Override
							public void updateItem(Categoria item, boolean empty) {
								super.updateItem(item, empty);
								if (item != null) {
									setText(item.getNome());
									setTextFill(Color.valueOf(item.getCor()));
								}
							}
						};
						return cell;
					}
				});
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

				if (cbCategoria.getSelectionModel().getSelectedItem() == null) {
					labelAviso.setText("Selecione uma categoria.");
					labelAviso.setVisible(true);

				} else {
					labelAviso.setVisible(false);

					Dialog.Actions resposta = (Actions) Dialogs.create()
							.owner(null).title("MoneySaver").masthead(null)
							.message("Deseja editar a categoria?")
							.showConfirm();

					if (resposta == Dialog.Actions.YES) {
						try {
							usuarioAtivo
									.editaCategoria(cbCategoria
											.getSelectionModel()
											.getSelectedItem(), tfNome
											.getText(), cor.getValue()
											.toString());
							gerente.atualizaSistema(usuarioAtivo);

							try {
								FXMLLoader fxmlLoader = new FXMLLoader(
										getClass()
												.getResource(
														"../gui/TelaEditarCategoria.fxml"));
								Parent root = (Parent) fxmlLoader.load();
								ControllerEditarCategoria controller = fxmlLoader
										.<ControllerEditarCategoria> getController();
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
									.getResource(
											"../gui/TelaEditarCategoria.fxml"));
							Parent root = (Parent) fxmlLoader.load();
							ControllerEditarCategoria controller = fxmlLoader
									.<ControllerEditarCategoria> getController();
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
