package controllers;

import fonte.Categoria;
import fonte.GerenteDeCategorias;
import fonte.Usuario;

import java.io.IOException;
import java.util.ArrayList;

import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;
import org.controlsfx.dialog.Dialog.Actions;

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
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;

public class ControllerRemoverCategoria {
	
	private EventHandler<ActionEvent> eventos = (EventHandler<ActionEvent>) new Eventos();
	private Usuario usuarioAtivo;

    @FXML
    private Button botaoCancelar;

    @FXML
    private ComboBox<Categoria> CBcategorias;

    @FXML
    private Label labelAviso;

    @FXML
    private Button botaoRemover;

    @FXML
    private AnchorPane content;  
    
    private GerenteDeCategorias gerente = new GerenteDeCategorias(usuarioAtivo); 
    
    private ArrayList<Categoria> categorias = gerente.getCategorias();
    
    @FXML
	void initialize() {
		botaoCancelar.setOnAction(eventos);
    	botaoRemover.setOnAction(eventos);
    	
    	labelAviso.setVisible(false);
    	CBcategorias.getItems().addAll(categorias);
    	CBcategorias.setCellFactory(
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
    
    public void setUsuario(Usuario usuario){
    	usuarioAtivo = usuario;
    }
	
	private class Eventos implements EventHandler<ActionEvent> {
		
		@Override
		public void handle(ActionEvent evento) {
			if (evento.getSource() == botaoCancelar) {
				try {
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../gui/TelaDeOperacoesPrincipais.fxml"));     
					Parent root = (Parent)fxmlLoader.load();          
					ControllerOperacoesPrincipais controller = fxmlLoader.<ControllerOperacoesPrincipais>getController();
					controller.setUsuario(usuarioAtivo);
					content.getChildren().setAll(root);
				} catch (IOException e) {
					e.printStackTrace();
				}		
			} else if (evento.getSource() == botaoRemover) {
				
				if (CBcategorias.getSelectionModel().getSelectedItem() == null){
					labelAviso.setText("Selecione uma categoria.");
					labelAviso.setVisible(true);
					
				} else {
					labelAviso.setVisible(false);
					
					Dialog.Actions resposta = (Actions) Dialogs.create().owner(null).title("MoneySaver")
							.masthead(null).message("Deseja remover a categoria selecionada?")
							.showConfirm();
					
					if (resposta == Dialog.Actions.YES){
						try{						
							gerente.removeCategoria(CBcategorias.getSelectionModel().getSelectedItem());
							try {
								FXMLLoader fxmlLoader = new FXMLLoader(getClass	().getResource("../gui/TelaRemoverCategoria.fxml"));     
								Parent root = (Parent)fxmlLoader.load();          
								ControllerRemoverCategoria controller = fxmlLoader.<ControllerRemoverCategoria>getController();
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
							FXMLLoader fxmlLoader = new FXMLLoader(getClass	().getResource("../gui/TelaRemoverCategoria.fxml"));     
							Parent root = (Parent)fxmlLoader.load();          
							ControllerRemoverCategoria controller = fxmlLoader.<ControllerRemoverCategoria>getController();
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