package controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import fonte.RelatorioHistograma;
import fonte.Transacao;
import fonte.Usuario;

public class ControllerHistograma {
	
	private Usuario usuarioAtivo;
	private RelatorioHistograma histo;
	private EventHandler<ActionEvent> eventos = (EventHandler<ActionEvent>) new Eventos();
	private Tabela tabela;

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

	@FXML
	void initialize() {
		botaoCancelar.setOnAction(eventos);
		menuCategoria.setOnAction(eventos);
		menuDespesa.setOnAction(eventos);
		menuProvento.setOnAction(eventos);
		cbMeses.getItems().addAll(RelatorioHistograma.getMeses());
		cbMeses.valueProperty().addListener(tabela);
	}

	public void setUsuario(Usuario usuario) {
		usuarioAtivo = usuario;
		histo = new RelatorioHistograma(usuarioAtivo);
		
	}
	
	private class Tabela implements ChangeListener<String>{
    	
    	@Override 
        public void changed(ObservableValue ov, String t, String t1) {  
    		double maiorValor = 0;
			ObservableList<XYChart.Series<String, Number>> series = FXCollections.observableArrayList();
			eixoX.setCategories(FXCollections.observableArrayList(histo.getCategorias()));
			
			XYChart.Series<String, Number> series1 = new XYChart.Series<String, Number>();
			series1.setName("Provento");
			for (int i = 0; i < 12; i++) {
				histo.valoresCategorias(t1).get(0);
			}
			
			
			eixoY.setAutoRanging(false);
			eixoY.setUpperBound(maiorValor);
			eixoY.setTickUnit(10);
			
			tabelaHistograma.getData().addAll(series);	
        }  
	}

	private class Eventos implements EventHandler<ActionEvent> {

		double maiorValor;
		
		@Override
		public void handle(ActionEvent evento) {
			if (evento.getSource() == menuDespesa) {
				cbMeses.setVisible(false);
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
				cbMeses.setVisible(false);
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
				cbMeses.setVisible(true);
				tabelaHistograma.getData().clear();
				
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
