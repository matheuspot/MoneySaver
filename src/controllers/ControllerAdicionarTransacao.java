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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialog.Actions;
import org.controlsfx.dialog.Dialogs;

import fonte.Categoria;
import fonte.Usuario;

public class ControllerAdicionarTransacao {

	private EventHandler<ActionEvent> eventos = (EventHandler<ActionEvent>) new Eventos();
	private Usuario usuarioAtivo;

	@FXML
    private RadioButton RBprovento;

    @FXML
    private Button botaoCancelar;

    @FXML
    private DatePicker tabelaData;

    @FXML
    private ComboBox<String> CBrecorrencia;

    @FXML
    private TextField valor;

    @FXML
    private ComboBox<Categoria> CBcategoria;

    @FXML
    private AnchorPane content;

    @FXML
    private TextArea descricao;

    @FXML
    private RadioButton RBdespesa;
    
    @FXML
    private Label labelAviso;
    
    @FXML
    private Button botaoAdicionar;
    
    private ToggleGroup group = new ToggleGroup();
    
    private List<Categoria> categorias;
    
    private final ObservableList<String> recorrencias =
		    FXCollections.observableArrayList("Nenhuma", "Semanal",	"Mensal");   
    
    @FXML
	void initialize() {
    	botaoCancelar.setOnAction(eventos);
    	botaoAdicionar.setOnAction(eventos);
    	labelAviso.setVisible(false);
    	RBdespesa.setToggleGroup(group);
    	RBdespesa.setUserData("despesa");
    	RBprovento.setToggleGroup(group);
    	RBprovento.setUserData("provento");
    	RBprovento.setSelected(true);
    	CBrecorrencia.setItems(recorrencias);
    }
    
    public void setUsuario(Usuario usuario){
    	usuarioAtivo = usuario;
    	
    	categorias = usuarioAtivo.getCategorias();
    	CBcategoria.getItems().addAll(categorias);
    	CBcategoria.setCellFactory(
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
			if (evento.getSource() == botaoCancelar) {
				try {
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../gui/TelaOperacoesPrincipais.fxml"));     
					Parent root = (Parent)fxmlLoader.load();          
					ControllerOperacoesPrincipais controller = fxmlLoader.<ControllerOperacoesPrincipais>getController();
					controller.setUsuario(usuarioAtivo);
					content.getChildren().setAll(root);
				} catch (IOException e) {
					e.printStackTrace();
				}	
			} else if (evento.getSource() == botaoAdicionar) {
				try{
					usuarioAtivo.getContaAtiva().adicionaTransacao(descricao.getText(), tabelaData.getValue(), valor.getText(), 
							CBcategoria.getSelectionModel().getSelectedItem(), 
							CBrecorrencia.getSelectionModel().getSelectedItem(), 
							(String) group.getSelectedToggle().getUserData());
					
					Dialog.Actions resposta = (Actions) Dialogs.create().owner(null).title("MoneySaver")
							.masthead(null).message("Transação efetuada. Deseja adicionar uma nova transação?")
							.showConfirm();
							
					if (resposta == Dialog.Actions.YES){
						try {
							FXMLLoader fxmlLoader = new FXMLLoader(getClass	().getResource("../gui/TelaAdicionarTransacao.fxml"));     
							Parent root = (Parent)fxmlLoader.load();          
							ControllerAdicionarTransacao controller = 	fxmlLoader.<ControllerAdicionarTransacao>getController();
							controller.setUsuario(usuarioAtivo);
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
							controller.setUsuario(usuarioAtivo);
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
