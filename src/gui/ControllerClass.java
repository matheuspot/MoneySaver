package gui;

import java.io.IOException;

import org.controlsfx.dialog.Dialogs;

import fonte.GerenteDeUsuarios;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControllerClass {

	EventHandler<ActionEvent> eventos = (EventHandler<ActionEvent>) new Eventos();
	
    @FXML
    private Button botaoCadastrar;
    @FXML
    private Button botaoConectar;
    @FXML
    private PasswordField PFsenha;
    @FXML
    private TextField TFemail;
    
    
    @FXML
    void initialize() {
    	botaoCadastrar.setOnAction(eventos);
    	botaoConectar.setOnAction(eventos);
    	PFsenha.setOnAction(eventos);
    	TFemail.setOnAction(eventos);
    	
    }
    
    private class Eventos implements EventHandler<ActionEvent>{
    	
    	GerenteDeUsuarios gerente = new GerenteDeUsuarios();
    	
    	@Override
    	public void handle(ActionEvent evento){
    		if (evento.getSource() == botaoCadastrar){
    			Parent root;
				try {
					root = FXMLLoader.load(getClass().getResource("Cadastro.fxml"));
					Stage stage = new Stage();
    	            stage.setTitle("MoneySaver / Cadastro de novo usuário");
    	            stage.setScene(new Scene(root, 600, 400));
    	            stage.show();
    	            ((Node) evento.getSource()).getScene().getWindow().hide();
    	            
    	           
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				
    	        
    	    }
    	    
    		else if (evento.getSource() == botaoConectar){
    			Dialogs.create()
    	        .owner(null)
    	        .title("MoneySaver")
    	        .masthead(null)
    	        .message("Botão conectar!")
    	        .showInformation();
    		}
    		else if (evento.getSource() == PFsenha){
    			PasswordField PFsenha2 = (PasswordField) evento.getSource();
    			System.out.print(PFsenha2.getText());
    		}
    			
    		else if (evento.getSource() == TFemail)
    			System.out.print("sdasdsad");
    			
    	}
    }
}
