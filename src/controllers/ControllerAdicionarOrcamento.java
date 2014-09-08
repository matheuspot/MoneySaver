package controllers;

import java.io.IOException;
import java.util.List;

import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;
import org.controlsfx.dialog.Dialog.Actions;

import fonte.Categoria;
import fonte.GerenteDeUsuarios;
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
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;

public class ControllerAdicionarOrcamento {
	
	private EventHandler<ActionEvent> eventos = (EventHandler<ActionEvent>) new Eventos();
	private Usuario usuarioAtivo;
	private GerenteDeUsuarios gerente = new GerenteDeUsuarios();

    @FXML
    private Button botaoAdicionar;

    @FXML
    private Button botaoVoltar;

    @FXML
    private Label labelAviso;

    @FXML
    private ComboBox<Categoria> cbCategorias;

    @FXML
    private TextField tfValor;

    @FXML
    private AnchorPane content;
    
    private List<Categoria> categorias;
    

    @FXML
   	void initialize() {
   		botaoVoltar.setOnAction(eventos);
       	botaoAdicionar.setOnAction(eventos);
   	}
   
    public void setUsuario(Usuario usuario){
    	usuarioAtivo = usuario;
    	
    	categorias = usuarioAtivo.getCategorias();
    	cbCategorias.getItems().addAll(categorias);
    	cbCategorias.setCellFactory(
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
			} else if (evento.getSource() == botaoAdicionar) {
				
				if (cbCategorias.getSelectionModel().getSelectedItem() == null){
					labelAviso.setText("Selecione uma categoria.");
					labelAviso.setVisible(true);
					
				} else {
					labelAviso.setVisible(false);
					
					Dialog.Actions resposta = (Actions) Dialogs.create().owner(null).title("MoneySaver")
							.masthead(null).message("Deseja adicionar orçamento para a categoria selecionada?")
							.showConfirm();
					
					if (resposta == Dialog.Actions.YES){
						try{						
							cbCategorias.getSelectionModel().getSelectedItem().setOrcamento(Double.parseDouble(tfValor.getText()));
							gerente.atualizaSistema(usuarioAtivo);
							try {
								FXMLLoader fxmlLoader = new FXMLLoader(getClass	().getResource("../gui/TelaAdicionarOrcamento.fxml"));     
								Parent root = (Parent)fxmlLoader.load();          
								ControllerAdicionarOrcamento controller = fxmlLoader.<ControllerAdicionarOrcamento>getController();
								controller.setUsuario(usuarioAtivo);
								content.getChildren().setAll(root);
							} catch (IOException e) {
								e.printStackTrace();
							}
						} catch (Exception e) {
							labelAviso.setText(e.getMessage());
							labelAviso.setVisible(true);
						}
					} else if (resposta == Dialog.Actions.NO){
						try {
							FXMLLoader fxmlLoader = new FXMLLoader(getClass	().getResource("../gui/TelaAdiconarOrcamento.fxml"));     
							Parent root = (Parent)fxmlLoader.load();          
							ControllerAdicionarOrcamento controller = fxmlLoader.<ControllerAdicionarOrcamento>getController();
							controller.setUsuario(usuarioAtivo);
							content.getChildren().setAll(root);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
}


