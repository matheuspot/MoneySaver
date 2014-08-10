package gui;

import java.io.IOException;

import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialog.Actions;
import org.controlsfx.dialog.Dialogs;

import fonte.GerenteDeCategorias;
import fonte.Transacao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;

public class ControllerAdicionarTransacao {

	EventHandler<ActionEvent> eventos = (EventHandler<ActionEvent>) new Eventos();

	@FXML
    private RadioButton RBprovento;

    @FXML
    private Button botaoCancelar;

    @FXML
    private DatePicker data;

    @FXML
    private ComboBox<String> CBrecorrencia;

    @FXML
    private TextField valor;

    @FXML
    private ComboBox<String> CBcategoria;

    @FXML
    private AnchorPane content;

    @FXML
    private TextArea descricao;

    @FXML
    private RadioButton RBdespesa;
    
    @FXML
    private Label labelAviso;
    
    @FXML
    private Button botaoAdicionar;
    
    private ToggleGroup group = new ToggleGroup();
    
    private final ObservableList<String> recorrencias =
		    FXCollections.observableArrayList(
		                 "Nenhuma",
		                 "Semanal",	
		                 "Mensal");   
    
    GerenteDeCategorias gerente = new GerenteDeCategorias(ControllerTelaPrincipal.usuarioAtivo);
    
    private ObservableList<String> categorias =
		    FXCollections.observableArrayList(gerente.listaCategorias());
    
    @FXML
	void initialize() {
    	botaoCancelar.setOnAction(eventos);
    	botaoAdicionar.setOnAction(eventos);
    	labelAviso.setVisible(false);
    	RBdespesa.setToggleGroup(group);
    	RBprovento.setToggleGroup(group);
    	CBrecorrencia.setItems(recorrencias);
    	CBcategoria.setItems(categorias);
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
			}
			else if (evento.getSource() == botaoAdicionar) {
				Dialog.Actions resposta = (Actions) Dialogs.create().owner(null).title("MoneySaver")
				.masthead(null).message("Transação efetuada. Deseja adicionar uma nova transação?")
				.showConfirm();
				
				if (resposta == Dialog.Actions.YES){
					try {
						content.getChildren().clear();
						content.getChildren().setAll(
								FXMLLoader.load(getClass().getResource(
										"TelaAdicionarTransacao.fxml")));
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
				
			}
		}
	}
}
