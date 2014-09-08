package controllers;

import java.io.IOException;

import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;
import org.controlsfx.dialog.Dialog.Actions;

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
import javafx.scene.layout.AnchorPane;

public class ControllerRemoverConta {
	
	private EventHandler<ActionEvent> eventos = (EventHandler<ActionEvent>) new Eventos();
	private Usuario usuarioAtivo;
	private GerenteDeUsuarios gerente = new GerenteDeUsuarios();

    @FXML
    private Button botaoVoltar;

    @FXML
    private Label labelAviso;

    @FXML
    private Button botaoRemover;

    @FXML
    private ComboBox<String> cbContas;

    @FXML
    private AnchorPane content;

    @FXML
   	void initialize() {
    	botaoRemover.setOnAction(eventos);
    	botaoVoltar.setOnAction(eventos);
    }
   
    public void setUsuario(Usuario usuario){
    	usuarioAtivo = usuario;
    	cbContas.getItems().addAll(usuarioAtivo.listaNomeContas());
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
			} else if (evento.getSource() == botaoRemover) {
				
				if (cbContas.getSelectionModel().getSelectedItem() == null){
					labelAviso.setText("Selecione uma conta.");
					labelAviso.setVisible(true);
					
				} else {
					labelAviso.setVisible(false);
					
					Dialog.Actions resposta = (Actions) Dialogs.create().owner(null).title("MoneySaver")
							.masthead(null).message("Deseja remover a conta selecionada?")
							.showConfirm();
					
					if (resposta == Dialog.Actions.YES){
						try{						
							usuarioAtivo.removeConta(
									usuarioAtivo.pesquisaConta(cbContas.getSelectionModel().getSelectedItem()));
							
							gerente.atualizaSistema(usuarioAtivo);
							try {
								FXMLLoader fxmlLoader = new FXMLLoader(getClass	().getResource("../gui/TelaRemoverConta.fxml"));     
								Parent root = (Parent)fxmlLoader.load();          
								ControllerRemoverConta controller = fxmlLoader.<ControllerRemoverConta>getController();
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
							FXMLLoader fxmlLoader = new FXMLLoader(getClass	().getResource("../gui/TelaRemoverConta.fxml"));     
							Parent root = (Parent)fxmlLoader.load();          
							ControllerRemoverConta controller = fxmlLoader.<ControllerRemoverConta>getController();
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
