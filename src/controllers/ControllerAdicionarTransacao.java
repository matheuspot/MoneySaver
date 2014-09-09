package controllers;

import java.io.IOException;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialog.Actions;
import org.controlsfx.dialog.Dialogs;

import fonte.Categoria;
import fonte.GerenteDeUsuarios;
import fonte.Usuario;

public class ControllerAdicionarTransacao {

	private final EventHandler<ActionEvent> eventos = (EventHandler<ActionEvent>) new Eventos();
	private final GerenteDeUsuarios gerente = new GerenteDeUsuarios();
	private Usuario usuarioAtivo;

	@FXML
    private RadioButton rbProvento;

    @FXML
    private Button botaoVoltar;

    @FXML
    private ComboBox<String> cbRecorrencia;

    @FXML
    private TextField tfValor;

    @FXML
    private ComboBox<Categoria> cbCategoria;

    @FXML
    private AnchorPane content;

    @FXML
    private RadioButton rbDespesa;
    
    @FXML
    private Label labelAviso;
    
    @FXML
    private Button botaoAdicionar;
    
    @FXML
    private TextField tfDescricao;
    
    @FXML
    private DatePicker data;
    
    private ToggleGroup group = new ToggleGroup();
    
    private List<Categoria> categorias;
    
    private final ObservableList<String> recorrencias =
		    FXCollections.observableArrayList("Nenhuma", "Semanal",	"Mensal");   
    
    @FXML
	void initialize() {
    	botaoVoltar.setOnAction(eventos);
    	botaoAdicionar.setOnAction(eventos);
    	rbDespesa.setToggleGroup(group);
    	rbDespesa.setUserData("despesa");
    	rbProvento.setToggleGroup(group);
    	rbProvento.setUserData("provento");
    	rbProvento.setSelected(true);
    	cbRecorrencia.setItems(recorrencias);
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
			} else if (evento.getSource() == botaoAdicionar) {
				boolean passouOrcamento;
				
				try{
					passouOrcamento = usuarioAtivo.getContaAtiva().adicionaTransacao(tfDescricao.getText(), data.getValue(),
							tfValor.getText(), 
							cbCategoria.getSelectionModel().getSelectedItem(), 
							cbRecorrencia.getSelectionModel().getSelectedItem(), 
							(String) group.getSelectedToggle().getUserData());
					
					gerente.atualizaSistema(usuarioAtivo);
					
					if (passouOrcamento){
						Dialogs.create().owner(null).title("MoneySaver")
								.masthead(null).message("O valor do orçamento de despesa desta categoria foi ultrapassado.")
								.showInformation();
					}
						
					Dialog.Actions resposta = (Actions) Dialogs.create().owner(null).title("MoneySaver")
							.masthead(null).message("Transação efetuada. Deseja adicionar uma nova transação?")
							.showConfirm();
							
					if (resposta == Dialog.Actions.YES){
						try {
							FXMLLoader fxmlLoader = new FXMLLoader(getClass	().getResource("../gui/TelaAdicionarTransacao.fxml"));     
							Parent root = (Parent)fxmlLoader.load();          
							ControllerAdicionarTransacao controller = 	fxmlLoader.<ControllerAdicionarTransacao>getController();
							controller.inicializa(usuarioAtivo);
							content.getChildren().setAll(root);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					else if (resposta == Dialog.Actions.NO){
						try {
							FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../gui/TelaOperacoesPrincipais.fxml"));     
							Parent root = (Parent)fxmlLoader.load();          
							ControllerOperacoesPrincipais controller = fxmlLoader.<ControllerOperacoesPrincipais>getController();
							controller.inicializa(usuarioAtivo);
							content.getChildren().setAll(root);
						} catch (IOException e) {
							e.printStackTrace();
						}	
					}
				} catch (Exception e){
					labelAviso.setText(e.getMessage());
					labelAviso.setVisible(true);
				}
			}
		}
	}
}
