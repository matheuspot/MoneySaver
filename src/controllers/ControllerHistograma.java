package controllers;

import java.io.IOException;
import java.util.List;
import javafx.collections.FXCollections;
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
import javafx.scene.layout.AnchorPane;
import fonte.Categoria;
import fonte.Histograma;
import fonte.Relatorio;
import fonte.Usuario;

public class ControllerHistograma {
	
	private Usuario usuarioAtivo;
	private EventHandler<ActionEvent> eventos = (EventHandler<ActionEvent>) new Eventos();

	@FXML
	private BarChart<String, Number> tabelaHistograma;
	
	@FXML
	private Button botaoVoltar;

	@FXML
	private CategoryAxis eixoX;

	@FXML
	private AnchorPane content;

	@FXML
	private NumberAxis eixoY;

	@FXML
	void initialize() {
		botaoVoltar.setOnAction(eventos);
	}

	public void setUsuario(Usuario usuario, Relatorio relatorio, Categoria categoria, String tipoTransacao, List<String> meses) {
		usuarioAtivo = usuario;
		
		Histograma histograma = new Histograma();
		List<Double> valores = (List<Double>) relatorio.getTransacoesPreparadas(histograma);
		
		double maiorValor = 0;
		
		XYChart.Series<String, Number> series1 = new XYChart.Series<String, Number>();
		series1.setName(tipoTransacao + " / " + categoria.getNome());
		eixoX.setCategories(FXCollections
				.observableArrayList(meses));

		for (int i = 0; i < meses.size(); i++) {
			series1.getData().add(
					new XYChart.Data<String, Number>(
							meses.get(i), valores.get(i).doubleValue()));
			if (valores.get(i).doubleValue() > maiorValor)
				maiorValor = valores.get(i).doubleValue();
		}
		
		eixoY.setAutoRanging(false);
		eixoY.setUpperBound(maiorValor);
		eixoY.setTickUnit(10);

		tabelaHistograma.getData().addAll(series1);
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
					controller.setUsuario(usuarioAtivo);
					content.getChildren().setAll(root);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
