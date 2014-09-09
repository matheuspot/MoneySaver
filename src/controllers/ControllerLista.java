package controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import fonte.Categoria;
import fonte.Lista;
import fonte.Relatorio;
import fonte.Transacao;
import fonte.Usuario;

public class ControllerLista {

	private Usuario usuarioAtivo;
	private final EventHandler<ActionEvent> eventos = (EventHandler<ActionEvent>) new Eventos();

	@FXML
	private Label labelAviso;

	@FXML
	private Button botaoVoltar;

	@FXML
	private AnchorPane content;

	@FXML
	private TableView<Transacao> table;

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
		botaoVoltar.setOnAction(eventos);
	}

	public void inicializa(Usuario usuario, Relatorio relatorio) {
		usuarioAtivo = usuario;
		Lista lista = new Lista();

		@SuppressWarnings("unchecked")
		List<Transacao> transacoes = (List<Transacao>) relatorio
				.getTransacoesPreparadas(lista);

		colunaValor
				.setCellValueFactory(new PropertyValueFactory<Transacao, Double>(
						"valor"));
		colunaValor
				.setCellFactory(new Callback<TableColumn<Transacao, Double>, TableCell<Transacao, Double>>() {

					@Override
					public TableCell<Transacao, Double> call(
							TableColumn<Transacao, Double> param) {

						TableCell<Transacao, Double> cell = new TableCell<Transacao, Double>() {

							protected void updateItem(Double item, boolean empty) {
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

							protected void updateItem(String item, boolean empty) {
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

							protected void updateItem(String item, boolean empty) {
								if (item != null) {
									setText(item);
								}
							}
						};
						cell.setAlignment(Pos.CENTER);
						return cell;
					}
				});

		ObservableList<Transacao> transacoes2 = FXCollections
				.observableArrayList();

		for (Transacao transacao : transacoes)
			transacoes2.add(transacao);

		table.setTableMenuButtonVisible(false);
		table.setItems(transacoes2);
	}

	private class Eventos implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent evento) {
			if (evento.getSource() == botaoVoltar) {
				try {
					FXMLLoader fxmlLoader = new FXMLLoader(getClass()
							.getResource("../gui/TelaGerarRelatorio.fxml"));
					Parent root = (Parent) fxmlLoader.load();
					ControllerGerarRelatorio controller = fxmlLoader
							.<ControllerGerarRelatorio> getController();
					controller.inicializa(usuarioAtivo);
					content.getChildren().setAll(root);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
