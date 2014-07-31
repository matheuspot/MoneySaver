package gui;

import org.controlsfx.dialog.Dialogs;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
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
    	
    	@Override
    	public void handle(ActionEvent evento){
    		if (evento.getSource() == botaoCadastrar){
    			Dialogs.create()
    	        .owner(null)
    	        .title("MoneySaver")
    	        .masthead(null)
    	        .message("Botão cadastrar!")
    	        .showInformation();
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
