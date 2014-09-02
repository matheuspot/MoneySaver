package controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import fonte.RelatorioHistograma;
import fonte.Usuario;

public class ControllerHistograma {
	
	private Usuario usuarioAtivo;
	private RelatorioHistograma histo;
	private EventHandler<ActionEvent> eventos = (EventHandler<ActionEvent>) new Eventos();
	private Tabela tabela;
	private Map<String, Integer> mapaMeses = new HashMap<String, Integer>();
	
	private final String[] MESES = { "Janeiro", "Fevereiro", "Março",
		"Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro",
		"Novembro", "Dezembro" };

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
		tabela = new Tabela();
		cbMeses.getItems().addAll(MESES);
    	cbMeses.valueProperty().addListener(tabela);
	}

	public void setUsuario(Usuario usuario) {
		usuarioAtivo = usuario;
		histo = new RelatorioHistograma(usuarioAtivo);
		
		mapaMeses.put("Janeiro", 1);
    	mapaMeses.put("Fevereiro", 2);
    	mapaMeses.put("Março", 3);
    	mapaMeses.put("Abril", 4);
    	mapaMeses.put("Maio", 5);
    	mapaMeses.put("Junho", 6);
    	mapaMeses.put("Julho", 7);
    	mapaMeses.put("Agosto", 8);
    	mapaMeses.put("Setembro", 9);
    	mapaMeses.put("Outubro", 10);
    	mapaMeses.put("Novembro", 11);
    	mapaMeses.put("Dezembro", 12);
	}
	
	private class Tabela implements ChangeListener<String>{
    	
    	@Override 
        public void changed(ObservableValue ov, String t, String t1) {  
    		double maiorValor = 0;
			
			XYChart.Series<String, Number> series1 = new XYChart.Series<String, Number>();
			series1.setName("Provento");

			for (int i = 0; i < usuarioAtivo.listaNomeCategorias().length; i++){
				series1.getData().add(
						new XYChart.Data<String, Number>(
								usuarioAtivo.listaNomeCategorias()[i], histo.valoresCategorias(mapaMeses.get(t1)).get(0).get(0)));
				if (histo.valoresCategorias(mapaMeses.get(t1)).get(0).get(0) > maiorValor)
					maiorValor = histo.valoresCategorias(mapaMeses.get(t1)).get(0).get(0);
			}
			
			XYChart.Series<String, Number> series2 = new XYChart.Series<String, Number>();
			series2.setName("Despesa");

			for (int i = 0; i < usuarioAtivo.listaNomeCategorias().length; i++){
				series2.getData().add(
						new XYChart.Data<String, Number>(
								usuarioAtivo.listaNomeCategorias()[i], histo.valoresCategorias(mapaMeses.get(t1)).get(1).get(0)));
				if (histo.valoresCategorias(mapaMeses.get(t1)).get(1).get(0) > maiorValor)
					maiorValor = histo.valoresCategorias(mapaMeses.get(t1)).get(1).get(0);
			}
			
			eixoY.setAutoRanging(false);
			eixoY.setUpperBound(maiorValor);
			eixoY.setTickUnit(10);
			
			tabelaHistograma.getData().clear();
			tabelaHistograma.getData().addAll(series1, series2);	
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
						.observableArrayList(MESES));

				for (int i = 0; i < 12; i++) {
					series1.getData().add(
							new XYChart.Data<String, Number>(
									MESES[i], histo.valoresDespesas().get(i)));
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
						.observableArrayList(MESES));

				for (int i = 0; i < 12; i++) {
					series1.getData().add(
							new XYChart.Data<String, Number>(
									MESES[i], histo.valoresProventos().get(i)));
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
				eixoX.setCategories(FXCollections.observableArrayList(usuarioAtivo.listaNomeCategorias()));
				
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
