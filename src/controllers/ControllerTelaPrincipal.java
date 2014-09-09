package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialogs;
import org.controlsfx.dialog.Dialogs.CommandLink;

import fonte.GerenteDeUsuarios;
import fonte.Usuario;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import auxiliar.MoneySaverMail;

public class ControllerTelaPrincipal {

	private final EventHandler<ActionEvent> eventos = (EventHandler<ActionEvent>) new Eventos();
	private final EventHandler<KeyEvent> eventosTeclado = (EventHandler<KeyEvent>) new EventosTeclado();
	private final GerenteDeUsuarios gerente = new GerenteDeUsuarios();
	private Usuario usuarioAtivo;
	private Stage stage;

	@FXML
	private Button botaoCadastrar;
	@FXML
	private Button botaoConectar;
	@FXML
	private PasswordField pfSenha;
	@FXML
	private TextField tfEmail;
	@FXML
	private AnchorPane content;
	@FXML
	private Label labelAviso;
	@FXML
    private Hyperlink recuperarSenha;
	@FXML
	private MenuItem itemSobre;
	@FXML
	private MenuItem itemSair;

	@FXML
	void initialize() {
		botaoCadastrar.setOnAction(eventos);
		botaoConectar.setOnAction(eventos);
		pfSenha.setOnAction(eventos);
		tfEmail.setOnAction(eventos);
		recuperarSenha.setOnAction(eventos);
		itemSobre.setOnAction(eventos);
		itemSair.setOnAction(eventos);
		
		tfEmail.setOnKeyPressed(eventosTeclado);
		pfSenha.setOnKeyPressed(eventosTeclado);
	}
	
	public void setStage(Stage stage){
    	this.stage = stage;
    }

	private class Eventos implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent evento) {
			if (evento.getSource() == botaoCadastrar) {
				try {
					content.getChildren().setAll(
							FXMLLoader.load(getClass().getResource(
									"../gui/TelaCadastrar.fxml")));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			else if (evento.getSource() == botaoConectar) {
				try {
					usuarioAtivo = gerente.login(tfEmail.getText(), pfSenha.getText());
					try {
						FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../gui/TelaOperacoesPrincipais.fxml"));     
						Parent root = (Parent)fxmlLoader.load();          
						ControllerOperacoesPrincipais controller = fxmlLoader.<ControllerOperacoesPrincipais>getController();
						controller.inicializa(usuarioAtivo);
						content.getChildren().setAll(root);
					} catch (IOException e) {
						e.printStackTrace();
					}
				} catch (Exception e) {
					labelAviso.setText(e.getMessage());
					labelAviso.setVisible(true);
				}
			} else if (evento.getSource() == recuperarSenha) {
				List<CommandLink> links = new ArrayList<>();
				links.add(new CommandLink("Dica de senha", "Lembre sua senha com auxílio da sua dica de senha."));
				links.add(new CommandLink("Enviar senha por email", "Receba a sua senha no seu email cadastrado."));
				links.add(new CommandLink("Voltar", "Retorne para a tela principal."));

				Action resposta = Dialogs.create()
				        .owner(null)
				        .title("MoneySaver")
				        .masthead(null)
				        .message("Escolha uma opção para recuperar a sua senha")
				        .showCommandLinks(links.get(2), links);
				
				if (resposta.textProperty().getValue().equals("Dica de senha")){
					if (tfEmail.getText() == null || tfEmail.getText().trim().length() == 0){
						Dialogs.create().owner(null).title("MoneySaver")
						.masthead(null).message("Preencha seu email no campo de login.")
						.showInformation();
					}
					else {
						if (gerente.pesquisaUsuario(tfEmail.getText()) == null){
							Dialogs.create().owner(null).title("MoneySaver")
							.masthead(null).message("Usuário não cadastrado.")
							.showInformation();
						} else {
							Dialogs.create().owner(null).title("MoneySaver")
							.masthead(null).message("Dica de senha: " + gerente.pesquisaUsuario(tfEmail.getText()).getDicaSenha())
							.showInformation();
						}
					}
				} else if (resposta.textProperty().getValue().equals("Enviar senha por email")){
					if (tfEmail.getText() == null || tfEmail.getText().trim().length() == 0){
						Dialogs.create().owner(null).title("MoneySaver")
						.masthead(null).message("Preencha seu email no campo de login.")
						.showInformation();
					}
					else {
						if (gerente.pesquisaUsuario(tfEmail.getText()) == null){
							Dialogs.create().owner(null).title("MoneySaver")
							.masthead(null).message("Usuário não cadastrado.")
							.showInformation();
						} else {
							try {
								MoneySaverMail.enviarEmail(tfEmail.getText());
								
								Dialogs.create().owner(null).title("MoneySaver")
								.masthead(null).message("A senha foi enviada para seu email.")
								.showInformation();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
			} else if (evento.getSource() == itemSobre) {
				try {
		            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../gui/TelaSobre.fxml"));     
					Parent root = (Parent)fxmlLoader.load(); 
					ControllerTelaSobre controller = fxmlLoader.<ControllerTelaSobre>getController();
					Stage stage = new Stage();
		            stage.setTitle("MoneySaver");
		            stage.setScene(new Scene(root));
		            stage.show();
		            stage.setResizable(false);
					controller.setStage(stage);
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
			} else if (evento.getSource() == itemSair) {
				stage.close();
			}
		}
	}
	
	private class EventosTeclado implements EventHandler<KeyEvent> {
		@Override
		public void handle(KeyEvent keyEvent) {
	         if (keyEvent.getCode() == KeyCode.ENTER) {
	        	 try {
						usuarioAtivo = gerente.login(tfEmail.getText(), pfSenha.getText());
						try {
							FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../gui/TelaOperacoesPrincipais.fxml"));     
							Parent root = (Parent)fxmlLoader.load();          
							ControllerOperacoesPrincipais controller = fxmlLoader.<ControllerOperacoesPrincipais>getController();
							controller.inicializa(usuarioAtivo);
							content.getChildren().setAll(root);
						} catch (IOException e) {
							e.printStackTrace();
						}
					} catch (Exception e) {
						labelAviso.setText(e.getMessage());
						labelAviso.setVisible(true);
					}
	         }
		}
	}
}
