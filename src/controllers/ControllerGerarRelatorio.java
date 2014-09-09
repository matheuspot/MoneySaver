package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fonte.Categoria;
import fonte.Relatorio;
import fonte.Usuario;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;

public class ControllerGerarRelatorio {
	
	private final EventHandler<ActionEvent> eventos = (EventHandler<ActionEvent>) new Eventos();
	private Usuario usuarioAtivo;

    @FXML
    private Button botaoGerar;

    @FXML
    private ComboBox<Categoria> cbCategoria;

    @FXML
    private RadioButton rbLista;

    @FXML
    private RadioButton rbDespesa;

    @FXML
    private Label labelAviso;

    @FXML
    private ComboBox<String> cbMesInicial;

    @FXML
    private ComboBox<String> cbMesFinal;

    @FXML
    private Button botaoVoltar;

    @FXML
    private RadioButton rbHistograma;

    @FXML
    private AnchorPane content;

    @FXML
    private RadioButton rbProvento;
    
    private ToggleGroup grupoTipoTransacao = new ToggleGroup();
    private ToggleGroup grupoTipoRelatorio = new ToggleGroup();
    private List<Categoria> categorias;
    private Relatorio relatorio;
    
    @FXML
	void initialize() {
    	botaoGerar.setOnAction(eventos);
    	botaoVoltar.setOnAction(eventos);
    	rbDespesa.setToggleGroup(grupoTipoTransacao);
    	rbDespesa.setUserData("Despesa");
    	rbProvento.setToggleGroup(grupoTipoTransacao);
    	rbProvento.setUserData("Provento");
    	rbProvento.setSelected(true);
    	rbHistograma.setToggleGroup(grupoTipoRelatorio);
    	rbHistograma.setUserData("histograma");
    	rbLista.setToggleGroup(grupoTipoRelatorio);
    	rbLista.setUserData("lista");
    	rbLista.setSelected(true);
    	cbMesFinal.getItems().addAll(ControllerOperacoesPrincipais.MESES);
    	cbMesInicial.getItems().addAll(ControllerOperacoesPrincipais.MESES);
    }
    
    public void inicializa(Usuario usuario){
    	usuarioAtivo = usuario;
    	
    	categorias = usuarioAtivo.getCategorias();
    	cbCategoria.getItems().addAll(categorias);
    	cbCategoria.setCellFactory(
    	        new Callback<ListView<Categoria>, ListCell<Categoria>>() {
    	            @Override public ListCell<Categoria> call(ListView<Categoria> param) {
    	                final ListCell<Categoria> cell = new ListCell<Categoria>() {
    	                    @Override public void updateItem(Categoria item, 
    	                        boolean empty) {
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
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../gui/TelaOperacoesPrincipais.fxml"));     
					Parent root = (Parent)fxmlLoader.load();          
					ControllerOperacoesPrincipais controller = fxmlLoader.<ControllerOperacoesPrincipais>getController();
					controller.inicializa(usuarioAtivo);
					content.getChildren().setAll(root);
				} catch (IOException e) {
					e.printStackTrace();
				}	
			} else if (evento.getSource() == botaoGerar) {
				if (cbCategoria.getSelectionModel().getSelectedItem() == null ||
						cbMesInicial.getSelectionModel().getSelectedItem() == null ||
						cbMesFinal.getSelectionModel().getSelectedItem() == null){
					
					labelAviso.setText("Selecione todas as opções.");
					labelAviso.setVisible(true);
				} else if (cbMesInicial.getSelectionModel().getSelectedIndex() > cbMesFinal.getSelectionModel().getSelectedIndex()){
					labelAviso.setText("Selecione um mês final que seja igual ou posterior ao mês inicial.");
					labelAviso.setVisible(true);
				} else {
					
					try {
						relatorio = new Relatorio(usuarioAtivo.getContaAtiva());
						relatorio.filtraPorCategoria(cbCategoria.getSelectionModel().getSelectedItem());
						relatorio.filtraPorData(cbMesInicial.getSelectionModel().getSelectedIndex()+1, 
								cbMesFinal.getSelectionModel().getSelectedIndex()+1);
						relatorio.filtraPorTipo((String) grupoTipoTransacao.getSelectedToggle().getUserData());
						
						String tipoRelatorio = (String) grupoTipoRelatorio.getSelectedToggle().getUserData();
						
						if (tipoRelatorio.equals("histograma")){
							List<String> meses = new ArrayList<String>();
							
							for (int i=0; i<12; i++){
								if (i >= cbMesInicial.getSelectionModel().getSelectedIndex() &&
										i <= cbMesFinal.getSelectionModel().getSelectedIndex()){
									meses.add(ControllerOperacoesPrincipais.MESES[i]);
								}
							}
							
							try {
								FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../gui/TelaHistograma.fxml"));     
								Parent root = (Parent)fxmlLoader.load();          
								ControllerHistograma controller = fxmlLoader.<ControllerHistograma>getController();
								controller.inicializa(usuarioAtivo, relatorio, cbCategoria.getSelectionModel().getSelectedItem(),
										(String) grupoTipoTransacao.getSelectedToggle().getUserData(), meses,
										cbMesInicial.getSelectionModel().getSelectedIndex(),
										cbMesFinal.getSelectionModel().getSelectedIndex());
								content.getChildren().setAll(root);
							} catch (IOException e) {
								e.printStackTrace();
							}	
						} else if (tipoRelatorio.equals("lista")){
							try {
								FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../gui/TelaLista.fxml"));     
								Parent root = (Parent)fxmlLoader.load();          
								ControllerLista controller = fxmlLoader.<ControllerLista>getController();
								controller.inicializa(usuarioAtivo, relatorio);
								content.getChildren().setAll(root);
							} catch (IOException e) {
								e.printStackTrace();
							}	
						}
					} catch (Exception e) {
						labelAviso.setText(e.getMessage());
						labelAviso.setVisible(true);
					}
				}
			}
		}
	}
}
