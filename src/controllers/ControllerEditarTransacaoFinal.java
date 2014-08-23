package controllers;

import java.io.IOException;
import java.util.List;

import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;
import org.controlsfx.dialog.Dialog.Actions;

import fonte.Categoria;
import fonte.GerenteDeCategorias;
import fonte.GerenteDeTransacoes;
import fonte.Transacao;
import fonte.Usuario;
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

public class ControllerEditarTransacaoFinal {

    @FXML
    private ComboBox<Categoria> cbCategoria;

    @FXML
    private Button botaoCancelar;

    @FXML
    private RadioButton rbDespesa;

    @FXML
    private DatePicker data;

    @FXML
    private Label labelAviso;

    @FXML
    private ComboBox<String> cbRecorrencia;

    @FXML
    private TextField tfValor;

    @FXML
    private Button botaoEditar;

    @FXML
    private RadioButton rbProvento;

    @FXML
    private TextArea descricao;
    
    @FXML
    private AnchorPane content;
    
    private EventHandler<ActionEvent> eventos = (EventHandler<ActionEvent>) new Eventos();
    
    private Transacao transacaoParaEditar;
    
    private Usuario usuarioAtivo;

    private ToggleGroup group = new ToggleGroup();
    
    private GerenteDeTransacoes gerenteTransacao;
    
    private GerenteDeCategorias gerente = new GerenteDeCategorias(usuarioAtivo); 
    
    private List<Categoria> categorias = gerente.getCategorias();
    
    private final ObservableList<String> recorrencias =
		    FXCollections.observableArrayList("Nenhuma", "Semanal",	"Mensal");   
    
    @FXML
	void initialize() {
    	botaoCancelar.setOnAction(eventos);
    	botaoEditar.setOnAction(eventos);
    	labelAviso.setVisible(false);
    	rbDespesa.setToggleGroup(group);
    	rbDespesa.setUserData("despesa");
    	rbProvento.setToggleGroup(group);
    	rbProvento.setUserData("provento");
    	cbRecorrencia.setItems(recorrencias);
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
    
    public void setUsuario(Usuario usuario, Transacao transacao){
    	usuarioAtivo = usuario;
    	gerenteTransacao = new GerenteDeTransacoes(usuarioAtivo);
    	transacaoParaEditar = transacao;
    	
    	descricao.setText(transacao.getDescricao());
    	data.setValue(transacao.getDataDeInsercao());
    	cbCategoria.setValue(transacao.getCategoria());
    	cbRecorrencia.setValue(transacao.getRecorrencia());
    	if (transacao.getValor() < 0){
    		group.selectToggle(rbDespesa);
    		tfValor.setText(String.valueOf(Math.abs(transacao.getValor())));
    	} else {
    		group.selectToggle(rbProvento);
    		tfValor.setText(String.valueOf(transacao.getValor()));
    	}
    }
    
	private class Eventos implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent evento) {
			if (evento.getSource() == botaoCancelar) {
				try {
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../gui/TelaEditarTransacaoInicial.fxml"));     
					Parent root = (Parent)fxmlLoader.load();          
					ControllerEditarTransacaoInicial controller = fxmlLoader.<ControllerEditarTransacaoInicial>getController();
					controller.setUsuario(usuarioAtivo);
					content.getChildren().setAll(root);
				} catch (IOException e) {
					e.printStackTrace();
				}	
			} else if (evento.getSource() == botaoEditar) {
				try{
					
					gerenteTransacao.editaTransacao(transacaoParaEditar, descricao.getText(), data.getValue(),tfValor.getText(), 
							cbCategoria.getSelectionModel().getSelectedItem(), 
							cbRecorrencia.getSelectionModel().getSelectedItem(), 
							(String) group.getSelectedToggle().getUserData());
					
					Dialog.Actions resposta = (Actions) Dialogs.create().owner(null).title("MoneySaver")
							.masthead(null).message("Transação modificada. Deseja editar uma nova transação?")
							.showConfirm();
							
					if (resposta == Dialog.Actions.YES){
						try {
							FXMLLoader fxmlLoader = new FXMLLoader(getClass	().getResource("../gui/TelaEditarTransacaoInicial.fxml"));     
							Parent root = (Parent)fxmlLoader.load();          
							ControllerEditarTransacaoInicial controller = 	fxmlLoader.<ControllerEditarTransacaoInicial>getController();
							controller.setUsuario(usuarioAtivo);
							content.getChildren().setAll(root);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					else if (resposta == Dialog.Actions.NO){
						try {
							FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../gui/TelaDeOperacoesPrincipais.fxml"));     
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