package controllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.text.StyledEditorKit.ForegroundAction;

import org.junit.internal.runners.model.EachTestNotifier;

import fonte.Categoria;
import fonte.GerenteDeCategorias;
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
    private BarChart<String, Number> histograma;

    @FXML
    private Button botaoAdicionar;

    @FXML
    private Button botaoCancelar;

    @FXML
    private Label labelAviso;

    @FXML
    private CategoryAxis eixoX;

    @FXML
    private AnchorPane content;

    @FXML
    private NumberAxis eixoY;
    
    Usuario usuarioAtivo;
    GerenteDeCategorias gerente = new GerenteDeCategorias(usuarioAtivo);
    
    
    
    private EventHandler<ActionEvent> eventos = (EventHandler<ActionEvent>) new Eventos();

    @FXML
	void initialize() {
    	botaoAdicionar.setOnAction(eventos);
    	botaoCancelar.setOnAction(eventos);
    }
    
    public void setUsuario(Usuario usuario){
    	usuarioAtivo = usuario;

    }
    
    private class Eventos implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent evento) {
			if (evento.getSource() == botaoAdicionar) {
				XYChart.Series<String, Number> series1 = new XYChart.Series<String, Number>();
		        series1.setName("XYChart.Series 1");
		        XYChart.Series series2 = new XYChart.Series();
		        series2.setName("XYChart.Series 2");
				ObservableList categorias = FXCollections.observableArrayList();
				eixoX.setCategories(FXCollections.observableArrayList(gerente.listaCategorias()));
				int c = 5;
				for(String categoria : gerente.listaCategorias()){
               	 series1.getData().add(new XYChart.Data(categoria, c));
               	 c+=10;
               }
				c=5;
				for(String categoria : gerente.listaCategorias()){
	               	 series2.getData().add(new XYChart.Data(categoria, c));
	               	 c+=5;
	               }
				histograma.getData().addAll(series1, series2);
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
