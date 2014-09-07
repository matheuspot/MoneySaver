package controllers;

import java.io.IOException;
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
	
	private EventHandler<ActionEvent> eventos = (EventHandler<ActionEvent>) new Eventos();
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
    
    private ToggleGroup group = new ToggleGroup();
    private ToggleGroup group2 = new ToggleGroup();
    private List<Categoria> categorias;
    
    @FXML
	void initialize() {
    	botaoGerar.setOnAction(eventos);
    	botaoVoltar.setOnAction(eventos);
    	rbDespesa.setToggleGroup(group);
    	rbDespesa.setUserData("despesa");
    	rbProvento.setToggleGroup(group);
    	rbProvento.setUserData("provento");
    	rbProvento.setSelected(true);
    	rbHistograma.setToggleGroup(group2);
    	rbHistograma.setUserData("histograma");
    	rbLista.setToggleGroup(group2);
    	rbLista.setUserData("lista");
    	rbLista.setSelected(true);
    	cbMesFinal.getItems().addAll(ControllerOperacoesPrincipais.MESES);
    	cbMesInicial.getItems().addAll(ControllerOperacoesPrincipais.MESES);
    }
    
    public void setUsuario(Usuario usuario){
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
					controller.setUsuario(usuarioAtivo);
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
					Relatorio relatorio;
					try {
						relatorio = new Relatorio(usuarioAtivo.getContaAtiva());
						relatorio.filtraPorCategoria(cbCategoria.getSelectionModel().getSelectedItem());
						relatorio.filtraPorData(cbMesInicial.getSelectionModel().getSelectedIndex()+1, 
								cbMesFinal.getSelectionModel().getSelectedIndex()+1);
						relatorio.filtraPorTipo((String) group.getSelectedToggle().getUserData());
						
						String tipoRelatorio = (String) group2.getSelectedToggle().getUserData();
						
						if (tipoRelatorio.equals("histograma")){
							try {
								FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../gui/TelaHistograma.fxml"));     
								Parent root = (Parent)fxmlLoader.load();          
								ControllerHistograma controller = fxmlLoader.<ControllerHistograma>getController();
								controller.setUsuario(usuarioAtivo, relatorio);
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
