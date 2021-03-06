package controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialog.Actions;
import org.controlsfx.dialog.Dialogs;
import fonte.Categoria;
import fonte.GerenteDeUsuarios;
import fonte.Transacao;
import fonte.Usuario;

public class ControllerRemoverTransacao {

	private final EventHandler<ActionEvent> eventos = (EventHandler<ActionEvent>) new Eventos();
	private final GerenteDeUsuarios gerente = new GerenteDeUsuarios();
	private Usuario usuarioAtivo;
	private Tabela tabela;

	@FXML
	private ComboBox<String> cbMes;

	@FXML
	private Button botaoVoltar;

	@FXML
	private Button botaoRemover;

	@FXML
	private TableView<Transacao> table;

	@FXML
	private AnchorPane content;

	@FXML
	private Label labelAviso;

	@FXML
	private TableColumn<Transacao, Categoria> colunaCategoria;

	@FXML
	private TableColumn<Transacao, Double> colunaValor;

	@FXML
	private TableColumn<Transacao, LocalDate> colunaData;

	@FXML
	private TableColumn<Transacao, String> colunaRecorrencia;

	@FXML
	private TableColumn<Transacao, String> colunaDescricao;

	@FXML
	void initialize() {
		botaoRemover.setOnAction(eventos);
		botaoVoltar.setOnAction(eventos);
		tabela = new Tabela();
		cbMes.getItems().addAll(ControllerOperacoesPrincipais.MESES);
		cbMes.valueProperty().addListener(tabela);
	}

	public void inicializa(Usuario usuario) {
		usuarioAtivo = usuario;
		tabela.criarTabela();
	}

	private class Tabela implements ChangeListener<String> {

		private List<Transacao> transacoes = null;
		private ObservableList<Transacao> transacoes2;

		@SuppressWarnings("rawtypes")
		@Override
		public void changed(ObservableValue ov, String t, String t1) {
			transacoes = usuarioAtivo.getContaAtiva().listaTransacoesPeloMes(
					cbMes.getSelectionModel().getSelectedIndex() + 1);
			criarTabela();
		}

		public void criarTabela() {
			transacoes2 = FXCollections.observableArrayList();

			if (transacoes == null) {
				int c = 0;
				transacoes = usuarioAtivo.getContaAtiva()
						.getTransacoesExistentes();

				for (Transacao transacao : transacoes) {
					transacoes2.add(transacao);
					c++;
					if (c == 10)
						break;
				}

			} else {
				for (Transacao transacao : transacoes)
					transacoes2.add(transacao);
			}

			colunaValor
					.setCellValueFactory(new PropertyValueFactory<Transacao, Double>(
							"valor"));
			colunaValor
					.setCellFactory(new Callback<TableColumn<Transacao, Double>, TableCell<Transacao, Double>>() {

						@Override
						public TableCell<Transacao, Double> call(
								TableColumn<Transacao, Double> param) {

							TableCell<Transacao, Double> cell = new TableCell<Transacao, Double>() {

								protected void updateItem(Double item,
										boolean empty) {
									if (item != null) {
										if (item < 0)
											setTextFill(Color.RED);
										else
											setTextFill(Color.GREEN);

										setText(String.format("R$ %,.2f", item));
									}
								}
							};
							cell.setAlignment(Pos.CENTER);
							return cell;
						}
					});

			colunaData
					.setCellValueFactory(new PropertyValueFactory<Transacao, LocalDate>(
							"dataDeInsercao"));
			colunaData
					.setCellFactory(new Callback<TableColumn<Transacao, LocalDate>, TableCell<Transacao, LocalDate>>() {

						@Override
						public TableCell<Transacao, LocalDate> call(
								TableColumn<Transacao, LocalDate> param) {

							TableCell<Transacao, LocalDate> cell = new TableCell<Transacao, LocalDate>() {

								protected void updateItem(LocalDate item,
										boolean empty) {
									if (item != null) {
										DateTimeFormatter formatter = DateTimeFormatter
												.ofPattern("dd/MM/yyyy");
										setText(item.format(formatter));
									}
								}
							};
							cell.setAlignment(Pos.CENTER);
							return cell;
						}
					});

			colunaDescricao
					.setCellValueFactory(new PropertyValueFactory<Transacao, String>(
							"descricao"));
			colunaDescricao
					.setCellFactory(new Callback<TableColumn<Transacao, String>, TableCell<Transacao, String>>() {

						@Override
						public TableCell<Transacao, String> call(
								TableColumn<Transacao, String> param) {

							TableCell<Transacao, String> cell = new TableCell<Transacao, String>() {

								protected void updateItem(String item,
										boolean empty) {
									if (item != null) {
										setText(item);
									}
								}
							};
							cell.setAlignment(Pos.CENTER);
							return cell;
						}
					});

			colunaCategoria
					.setCellValueFactory(new PropertyValueFactory<Transacao, Categoria>(
							"categoria"));
			colunaCategoria
					.setCellFactory(new Callback<TableColumn<Transacao, Categoria>, TableCell<Transacao, Categoria>>() {

						@Override
						public TableCell<Transacao, Categoria> call(
								TableColumn<Transacao, Categoria> param) {

							TableCell<Transacao, Categoria> cell = new TableCell<Transacao, Categoria>() {

								protected void updateItem(Categoria item,
										boolean empty) {
									if (item != null) {
										setText(item.getNome());
										setTextFill(Color.valueOf(item.getCor()));
									}
								}
							};
							cell.setAlignment(Pos.CENTER);
							return cell;
						}
					});

			colunaRecorrencia
					.setCellValueFactory(new PropertyValueFactory<Transacao, String>(
							"recorrencia"));
			colunaRecorrencia
					.setCellFactory(new Callback<TableColumn<Transacao, String>, TableCell<Transacao, String>>() {

						@Override
						public TableCell<Transacao, String> call(
								TableColumn<Transacao, String> param) {

							TableCell<Transacao, String> cell = new TableCell<Transacao, String>() {

								protected void updateItem(String item,
										boolean empty) {
									if (item != null) {
										setText(item);
									}
								}
							};
							cell.setAlignment(Pos.CENTER);
							return cell;
						}
					});

			table.setTableMenuButtonVisible(false);
			table.setItems(transacoes2);
		}
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
			} else if (evento.getSource() == botaoRemover) {
				if (table.getSelectionModel().getSelectedItem() == null) {
					labelAviso.setText("Selecione uma transação.");
					labelAviso.setVisible(true);

				} else {
					labelAviso.setVisible(false);

					Dialog.Actions resposta = (Actions) Dialogs.create()
							.owner(null).title("MoneySaver").masthead(null)
							.message("Deseja remover a transação selecionada?")
							.showConfirm();

					if (resposta == Dialog.Actions.YES) {
						try {
							usuarioAtivo.getContaAtiva()
									.removeTransacao(
											table.getSelectionModel()
													.getSelectedItem());
							gerente.atualizaSistema(usuarioAtivo);
							try {
								FXMLLoader fxmlLoader = new FXMLLoader(
										getClass()
												.getResource(
														"../gui/TelaRemoverTransacao.fxml"));
								Parent root = (Parent) fxmlLoader.load();
								ControllerRemoverTransacao controller = fxmlLoader
										.<ControllerRemoverTransacao> getController();
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
											"../gui/TelaRemoverTransacao.fxml"));
							Parent root = (Parent) fxmlLoader.load();
							ControllerRemoverTransacao controller = fxmlLoader
									.<ControllerRemoverTransacao> getController();
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