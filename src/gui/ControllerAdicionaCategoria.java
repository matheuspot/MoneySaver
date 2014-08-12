package gui;

import java.io.IOException;

import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;
import org.controlsfx.dialog.Dialog.Actions;

import fonte.Categoria;
import fonte.GerenteDeCategorias;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class ControllerAdicionaCategoria {
	
	EventHandler<ActionEvent> eventos = (EventHandler<ActionEvent>) new Eventos();
	
	GerenteDeCategorias gerente = new GerenteDeCategorias(ControllerTelaPrincipal.usuarioAtivo);
	
	@FXML
	private Button botaoCancelar;
	
	@FXML
	private Button botaoAdicionar;
	
	@FXML
	private TextField Nome;
	
	@FXML
	private ColorPicker CPcor;
	
	@FXML
    private AnchorPane content;
	
	Categoria categoria; 
	
	@FXML
	void initialize() {
		botaoCancelar.setOnAction(eventos);
    	botaoAdicionar.setOnAction(eventos);
	}
	
	private class Eventos implements EventHandler<ActionEvent> {
		
		@Override
		public void handle(ActionEvent evento) {
			if (evento.getSource() == botaoCancelar) {
				try {
					content.getChildren().setAll(
							FXMLLoader.load(getClass().getResource(
									"TelaDeOperacoesPrincipais.fxml")));
				} catch (IOException e) {
					e.printStackTrace();
				}			
			} else if (evento.getSource() == botaoAdicionar) {
				try {
					gerente.adicionaCategoria(Nome.getText(), CPcor.getPromptText());
					
					Dialog.Actions resposta = (Actions) Dialogs.create().owner(null).title("MoneySaver")
							.masthead(null).message("Categoria Adicionada. Deseja adicionar uma nova Categoria?")
							.showConfirm();
							
							if (resposta == Dialog.Actions.YES){
								try {
									content.getChildren().clear();
									content.getChildren().setAll(
											FXMLLoader.load(getClass().getResource(
													"TelaAdicionarCategoria.fxml")));
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
							else if (resposta == Dialog.Actions.NO){
								try {
									content.getChildren().setAll(
											FXMLLoader.load(getClass().getResource(
													"TelaDeOperacoesPrincipais.fxml")));
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
