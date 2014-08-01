package gui;

import org.controlsfx.dialog.Dialogs;
import fonte.GerenteDeUsuarios;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class ControllerCadastro {
	
	EventHandler<ActionEvent> eventos = (EventHandler<ActionEvent>) new Eventos();
	
	@FXML
    private PasswordField senha;

    @FXML
    private TextField dicaSenha;

    @FXML
    private PasswordField confirmacaoSenha;

    @FXML
    private TextField nome;

    @FXML
    private TextField email;
    @FXML
    private Button cadastrar;
    
    @FXML
    void initialize() {
    	
    	nome.setOnAction(eventos);
    	email.setOnAction(eventos);
    	senha.setOnAction(eventos);
    	dicaSenha.setOnAction(eventos);
    	confirmacaoSenha.setOnAction(eventos);
    	cadastrar.setOnAction(eventos);
    }
    
    private class Eventos implements EventHandler<ActionEvent>{
    	
    	GerenteDeUsuarios gerente = new GerenteDeUsuarios();
    	
    	@Override
    	public void handle(ActionEvent evento){
    		if (evento.getSource() == cadastrar){
    			 
     	            try {
     					gerente.adicionaUsuario(nome.getText(), email.getText(), senha.getText(), confirmacaoSenha.getText(), dicaSenha.getText());
     				} catch (Exception e) {
     					Dialogs.create()
     	    	        .owner(null)
     	    	        .title("MoneySaver")
     	    	        .masthead(null)
     	    	        .message(e.getMessage())
     	    	        .showInformation();
     				}
     	     }
    	}
    }
}
