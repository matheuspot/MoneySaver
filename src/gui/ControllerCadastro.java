package gui;


import org.controlsfx.dialog.Dialogs;
import fonte.GerenteDeUsuarios;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class ControllerCadastro {
	
	EventHandler<ActionEvent> eventos = (EventHandler<ActionEvent>) new Eventos();
	
	@FXML
    private PasswordField PFsenha;

    @FXML
    private TextField TFdicaSenha;

    @FXML
    private PasswordField PFconfirmacaoSenha;

    @FXML
    private TextField TFnome;

    @FXML
    private TextField TFemail;
    
    @FXML
    private Button botaoCadastrar;
    
    @FXML
    private Button botaoVoltar;
    
    @FXML
    private Label labelAviso;
    
    
    @FXML
    void initialize() {
    	
    	TFnome.setOnAction(eventos);
    	TFemail.setOnAction(eventos);
    	PFsenha.setOnAction(eventos);
    	TFdicaSenha.setOnAction(eventos);
    	PFconfirmacaoSenha.setOnAction(eventos);
    	botaoCadastrar.setOnAction(eventos);
    	botaoVoltar.setOnAction(eventos);
    	labelAviso.setVisible(false);
    }
    
    private class Eventos implements EventHandler<ActionEvent>{
    	
    	GerenteDeUsuarios gerente = new GerenteDeUsuarios();
    	
    	@Override
    	public void handle(ActionEvent evento){
    		if (evento.getSource() == botaoCadastrar){
    			 
     	            try {
     					gerente.adicionaUsuario(TFnome.getText(), TFemail.getText(), PFsenha.getText(), PFconfirmacaoSenha.getText(), TFdicaSenha.getText());
     					labelAviso.setText("Cadastro efetuado!");
     					labelAviso.setVisible(true);
     	            } catch (Exception e) {
     	            	labelAviso.setText(e.getMessage());
     					labelAviso.setVisible(true);
     				}
     	     }
    		
    		else if (evento.getSource() == botaoVoltar){
    			
    		}
    	}
    }
}
