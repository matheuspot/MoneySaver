package controllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.text.StyledEditorKit.ForegroundAction;

import org.junit.internal.runners.model.EachTestNotifier;

import fonte.Categoria;
import fonte.GerenteDeCategorias;
import fonte.GerenteDeTransacoes;
import fonte.Histograma;
import fonte.Usuario;
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
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class ControllerHistograma {

    @FXML
    private BarChart<String, Number> tabelaHistograma;

    @FXML
    private Button botaoAdicionar;

    @FXML
    private Button botaoCancelar;

    @FXML
    private CategoryAxis eixoX;

    @FXML
    private AnchorPane content;

    @FXML
    private NumberAxis eixoY;
    
    private Usuario usuarioAtivo;
    private GerenteDeCategorias gerente = new GerenteDeCategorias(usuarioAtivo);
    private Histograma histo; 
    private EventHandler<ActionEvent> eventos = (EventHandler<ActionEvent>) new Eventos();

    @FXML
	void initialize() {
    	botaoAdicionar.setOnAction(eventos);
    	botaoCancelar.setOnAction(eventos);
    }
    
    public void setUsuario(Usuario usuario){
    	usuarioAtivo = usuario;
    	histo = new Histograma(usuarioAtivo);
    }
    
    private class Eventos implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent evento) {
			if (evento.getSource() == botaoAdicionar) {
				double maiorValor = 0;
				XYChart.Series<String, Number> series1 = new XYChart.Series<String, Number>();
		        series1.setName("Provento");
				eixoX.setCategories(FXCollections.observableArrayList(histo.getMESES()));
				
				for (int i=0; i<12; i++){
					series1.getData().add(new XYChart.Data<String, Number>(histo.getMESES()[i], histo.valoresDespesaPorMes().get(i)));
					if  (histo.valoresDespesaPorMes().get(i) > maiorValor)
						maiorValor = histo.valoresDespesaPorMes().get(i);
				}
				
				eixoY.setAutoRanging(false);
				eixoY.setUpperBound(maiorValor);
				eixoY.setTickUnit(10);
               	 	
				tabelaHistograma.getData().addAll(series1);
			} else if (evento.getSource() == botaoCancelar) {
				try {
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../gui/TelaOperacoesPrincipais.fxml"));     
					Parent root = (Parent)fxmlLoader.load();          
					ControllerOperacoesPrincipais controller = fxmlLoader.<ControllerOperacoesPrincipais>getController();
					controller.setUsuario(usuarioAtivo);
					content.getChildren().setAll(root);
				} catch (IOException e) {
					e.printStackTrace();
				}	
		}
	}

    }
}
