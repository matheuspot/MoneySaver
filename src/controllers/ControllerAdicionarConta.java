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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class ControllerAdicionarConta {
	
	private EventHandler<ActionEvent> eventos = (EventHandler<ActionEvent>) new Eventos();
	private Usuario usuarioAtivo;
	private GerenteDeUsuarios gerente = new GerenteDeUsuarios();

    @FXML
    private Button botaoAdicionar;

    @FXML
    private Button botaoCancelar;

    @FXML
    private Label labelAviso;

    @FXML
    private AnchorPane content;

    @FXML
    private TextField tfNome;

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
					usuarioAtivo.adicionaConta(tfNome.getText());
					
					gerente.atualizaSistema(usuarioAtivo);
					
					Dialog.Actions resposta = (Actions) Dialogs.create().owner(null).title("MoneySaver")
							.masthead(null).message("Conta adicionada. Deseja adicionar uma nova conta?")
							.showConfirm();
							
					if (resposta == Dialog.Actions.YES){
						try {
							FXMLLoader fxmlLoader = new FXMLLoader(getClass	().getResource("../gui/TelaAdicionarConta.fxml"));     
							Parent root = (Parent)fxmlLoader.load();          
							ControllerAdicionarConta controller = 	fxmlLoader.<ControllerAdicionarConta>getController();
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