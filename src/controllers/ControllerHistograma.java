package controllers;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import fonte.GerenteDeCategorias;
import fonte.RelatorioHistograma;
import fonte.Usuario;

public class ControllerHistograma {

	@FXML
	private BarChart<String, Number> tabelaHistograma;

	@FXML
	private MenuItem menuDespesa;

	@FXML
	private MenuItem menuCategoria;

	@FXML
	private MenuItem menuProvento;
	
	@FXML
	private Button botaoCancelar;

	@FXML
	private CategoryAxis eixoX;

	@FXML
	private AnchorPane content;

	@FXML
	private NumberAxis eixoY;
	
	@FXML
    private ComboBox<String> cbMeses;

	private Usuario usuarioAtivo;
	private GerenteDeCategorias gerenteDeCategorias = new GerenteDeCategorias(
			usuarioAtivo);
	private RelatorioHistograma histo;
	private EventHandler<ActionEvent> eventos = (EventHandler<ActionEvent>) new Eventos();

	@FXML
	void initialize() {
		botaoCancelar.setOnAction(eventos);
		menuCategoria.setOnAction(eventos);
		menuDespesa.setOnAction(eventos);
		menuProvento.setOnAction(eventos);
	}

	public void setUsuario(Usuario usuario) {
		usuarioAtivo = usuario;
		histo = new RelatorioHistograma(usuarioAtivo);
	}

	private class Eventos implements EventHandler<ActionEvent> {

		double maiorValor;
		
		@Override
		public void handle(ActionEvent evento) {
			if (evento.getSource() == menuDespesa) {
				maiorValor = 0;
				XYChart.Series<String, Number> series1 = new XYChart.Series<String, Number>();
				series1.setName("Despesa");
				eixoX.setCategories(FXCollections
						.observableArrayList(RelatorioHistograma.getMeses()));

				for (int i = 0; i < 12; i++) {
					series1.getData().add(
							new XYChart.Data<String, Number>(
									RelatorioHistograma.getMeses()[i], histo
											.valoresDespesas().get(i)));
					if (histo.valoresDespesas().get(i) > maiorValor)
						maiorValor = histo.valoresDespesas().get(i);
				}
				
				eixoY.setAutoRanging(false);
				eixoY.setUpperBound(maiorValor);
				eixoY.setTickUnit(10);

				tabelaHistograma.getData().clear();
				tabelaHistograma.getData().addAll(series1);
				
			} else if (evento.getSource() == menuProvento) {
				maiorValor = 0;
				XYChart.Series<String, Number> series1 = new XYChart.Series<String, Number>();
				series1.setName("Provento");
				eixoX.setCategories(FXCollections
						.observableArrayList(RelatorioHistograma.getMeses()));

				for (int i = 0; i < 12; i++) {
					series1.getData().add(
							new XYChart.Data<String, Number>(
									RelatorioHistograma.getMeses()[i], histo
											.valoresProventos().get(i)));
					if (histo.valoresProventos().get(i) > maiorValor)
						maiorValor = histo.valoresProventos().get(i);
				}
				
				eixoY.setAutoRanging(false);
				eixoY.setUpperBound(maiorValor);
				eixoY.setTickUnit(10);

				tabelaHistograma.getData().clear();
				tabelaHistograma.getData().addAll(series1);
				
			} else if (evento.getSource() == menuCategoria) {
				maiorValor = 0;
				ObservableList<XYChart.Series<String, Number>> series = FXCollections.observableArrayList();
				eixoX.setCategories(FXCollections.observableArrayList(histo.getCategorias()));
				
				XYChart.Series<String, Number> series1 = new XYChart.Series<String, Number>();
				series1.setName("Provento");
				for (int i = 1; i < 13; i++) {
					histo.valoresCategorias(i).get(0);
				}
				
				
				eixoY.setAutoRanging(false);
				eixoY.setUpperBound(maiorValor);
				eixoY.setTickUnit(10);

				tabelaHistograma.getData().clear();
				tabelaHistograma.getData().addAll(series);	
			} else if (evento.getSource() == botaoCancelar) {
				try {
					FXMLLoader fxmlLoader = new FXMLLoader(getClass()
							.getResource("../gui/TelaOperacoesPrincipais.fxml"));
					Parent root = (Parent) fxmlLoader.load();
					ControllerOperacoesPrincipais controller = fxmlLoader
							.<ControllerOperacoesPrincipais> getController();
					controller.setUsuario(usuarioAtivo);
					content.getChildren().setAll(root);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
}
