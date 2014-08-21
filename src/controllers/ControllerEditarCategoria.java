package controllers;

import java.io.IOException;
import java.util.ArrayList;

import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;
import org.controlsfx.dialog.Dialog.Actions;

import fonte.Categoria;
import fonte.GerenteDeCategorias;
import fonte.Usuario;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;

public class ControllerEditarCategoria {
	
	private EventHandler<ActionEvent> eventos = (EventHandler<ActionEvent>) new Eventos();
	private Usuario usuarioAtivo;

    @FXML
    private Button botaoCancelar;

    @FXML
    private ColorPicker cor;

    @FXML
    private ComboBox<Categoria> CBcategoria;

    @FXML
    private TextField TFnome;

    @FXML
    private Button botaoEditar;
    
    @FXML
    private AnchorPane content;
    
    @FXML
    private Label labelAviso;
    
    private GerenteDeCategorias gerente = new GerenteDeCategorias(usuarioAtivo); 
    
    private ArrayList<Categoria> categorias = gerente.getCategorias();
    
    private ChangeListener<Categoria> changeListener = new ChangeListener<Categoria>() {
        @Override 
        public void changed(ObservableValue ov, Categoria t, Categoria t1) {                
            TFnome.setText(t1.getNome()); 
            cor.setValue(Color.valueOf(t1.getCor()));
        }    
    };
    
    @FXML
	void initialize() {
		botaoCancelar.setOnAction(eventos);
    	botaoEditar.setOnAction(eventos);
    	
    	CBcategoria.valueProperty().addListener(changeListener);
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
			} else if (evento.getSource() == botaoEditar) {
				
				if (CBcategoria.getSelectionModel().getSelectedItem() == null){
					labelAviso.setText("Selecione uma categoria.");
					labelAviso.setVisible(true);
					
				} else {
					labelAviso.setVisible(false);
					
					Dialog.Actions resposta = (Actions) Dialogs.create().owner(null).title("MoneySaver")
							.masthead(null).message("Deseja editar a categoria?")
							.showConfirm();
					
					if (resposta == Dialog.Actions.YES){
						try{						
							gerente.editaCategoria(CBcategoria.getSelectionModel().getSelectedItem(), TFnome.getText(), cor.getValue().toString());
							try {
								FXMLLoader fxmlLoader = new FXMLLoader(getClass	().getResource("../gui/TelaEditarCategoria.fxml"));     
								Parent root = (Parent)fxmlLoader.load();          
								ControllerEditarCategoria controller = fxmlLoader.<ControllerEditarCategoria>getController();
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
							FXMLLoader fxmlLoader = new FXMLLoader(getClass	().getResource("../gui/TelaEditarCategoria.fxml"));     
							Parent root = (Parent)fxmlLoader.load();          
							ControllerEditarCategoria controller = fxmlLoader.<ControllerEditarCategoria>getController();
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
