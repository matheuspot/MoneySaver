package controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import fonte.Transacao;
import fonte.Usuario;

public class ControllerOperacoesPrincipais {
	
	private EventHandler<ActionEvent> eventos = (EventHandler<ActionEvent>) new Eventos();
	private Usuario usuarioAtivo;
	private final String[] MESES = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho",
    		"Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
    private Tabela tabela;
	

	    @FXML
	    private Label labelSaldo;

	    @FXML
	    private Button botaoSair;
	    
	    @FXML
	    private AnchorPane content;
	    
	    @FXML
	    private TableView<Transacao> table;
	    
	    @FXML
	    private TableColumn<Transacao, Double> colunaValor;
	    
	    @FXML
	    private TableColumn<Transacao, LocalDate> colunaData;
	    
	    @FXML
	    private ComboBox<String> CBmes;
	    
	    @FXML
	    private MenuItem editarTransacao;

	    @FXML
	    private MenuItem adicionarConta;
	    
	    @FXML
	    private MenuItem removerTransacao;

	    @FXML
	    private MenuItem adicionarTransacao;

	    @FXML
	    private MenuItem editarCategoria;
	    
	    @FXML
	    private MenuItem removerConta;

	    @FXML
	    private MenuItem adicionarCategoria;

	    @FXML
	    private ComboBox<String> cbContas;
	    
	    @FXML
	    private MenuItem adicionarOrcamento;

	    @FXML
	    private MenuItem editarConta;

	    @FXML
	    private MenuItem removerCategoria;
	    
	    @FXML
	    private Label saldoMes;
	    
	    @FXML
	    private Button botaoRelatorio;
	   
	    
    @FXML
	void initialize() {
    	adicionarTransacao.setOnAction(eventos);
    	removerTransacao.setOnAction(eventos);
    	editarTransacao.setOnAction(eventos);
    	adicionarCategoria.setOnAction(eventos);
    	removerCategoria.setOnAction(eventos);
    	editarCategoria.setOnAction(eventos);
    	editarConta.setOnAction(eventos);
    	adicionarConta.setOnAction(eventos);
    	removerConta.setOnAction(eventos);
    	adicionarOrcamento.setOnAction(eventos);
    	botaoSair.setOnAction(eventos);
    	tabela = new Tabela();
    	CBmes.getItems().addAll(MESES);
    	CBmes.valueProperty().addListener(tabela);
    	botaoRelatorio.setOnAction(eventos);
    }
    
    public void setUsuario(Usuario usuario){
    	usuarioAtivo = usuario;
    	usuarioAtivo.getContaAtiva().atualizaRecorrencias();
    	cbContas.getItems().addAll(usuarioAtivo.listaNomeContas());
    	cbContas.getSelectionModel().select(usuarioAtivo.getContaAtiva().getNome());
    	cbContas.valueProperty().addListener(tabela);
    	tabela.criarTabela();
    }
    
    private class Tabela implements ChangeListener<String>{
    	
    	private List<Transacao> transacoes = null;
    	private Map<String, Integer> mapaMeses = new HashMap<String, Integer>();
    	private ObservableList<Transacao> transacoes2;
    	
    	public Tabela() {
    		mapaMeses.put("Janeiro", 1);
        	mapaMeses.put("Fevereiro", 2);
        	mapaMeses.put("Março", 3);
        	mapaMeses.put("Abril", 4);
        	mapaMeses.put("Maio", 5);
        	mapaMeses.put("Junho", 6);
        	mapaMeses.put("Julho", 7);
        	mapaMeses.put("Agosto", 8);
        	mapaMeses.put("Setembro", 9);
        	mapaMeses.put("Outubro", 10);
        	mapaMeses.put("Novembro", 11);
        	mapaMeses.put("Dezembro", 12);
		}
    	
    	@Override 
        public void changed(ObservableValue ov, String t, String t1) { 
    		boolean conta = false;
    		
    		usuarioAtivo.pesquisaConta(t1);
    		
    		for(String nomeConta : usuarioAtivo.listaNomeContas()){
    			if (ov.getValue().equals(nomeConta)){
    				conta = true;
    				break;
    			}
    		}
    			
    		if (conta){
    			transacoes = usuarioAtivo.getContaAtiva().getTransacoesExistentes();
    			labelSaldo.setVisible(false);
    			saldoMes.setVisible(false);
    			
    		} else {
    			transacoes = usuarioAtivo.getContaAtiva().listaTransacoesPeloMes(mapaMeses.get(t1));
    			labelSaldo.setText(String.format("R$ %.2f", usuarioAtivo.getContaAtiva().pegaSaldoDoMes(mapaMeses.get(t1))));
    			labelSaldo.setVisible(true);
    			saldoMes.setVisible(true);
    		}
    		
        	criarTabela();
        }  
    	
    	public void criarTabela(){
    		transacoes2 = FXCollections.observableArrayList();
    		
    		if (transacoes == null){
    			int c = 0;
    			transacoes = usuarioAtivo.getContaAtiva().getTransacoesExistentes();
    			
    			for (Transacao transacao : transacoes){
    				transacoes2.add(transacao);
    				c++;
    				if (c == 10)
    					break;
    			}
         		    
    		} else {
    			for (Transacao transacao : transacoes) 
         		    transacoes2.add(transacao);
    		}
        	
        	colunaValor.setCellValueFactory(new PropertyValueFactory<Transacao, Double>("valor"));
        	colunaValor.setCellFactory(new Callback<TableColumn<Transacao, Double>, TableCell<Transacao,Double>>(){

                @Override
                public TableCell<Transacao, Double> call(TableColumn<Transacao, Double> param) {

                    TableCell<Transacao, Double> cell = new TableCell<Transacao, Double>(){

                    	protected void updateItem(Double item, boolean empty) {
                            if (item != null) {
                            	if (item < 0)
                            		setTextFill(Color.RED);
                            	else
                            		setTextFill(Color.GREEN);
                            	
                                setText(String.format("R$ %,.2f", item));
                            }
                        }                    
                    };               
                    
                    cell.setAlignment(Pos.CENTER);
                    return cell;        
                }

            });
        	
        	colunaData.setCellValueFactory(new PropertyValueFactory<Transacao, LocalDate>("dataDeInsercao"));
        	colunaData.setCellFactory(new Callback<TableColumn<Transacao, LocalDate>, TableCell<Transacao, LocalDate>>(){

                @Override
                public TableCell<Transacao, LocalDate> call(TableColumn<Transacao, LocalDate> param) {

                    TableCell<Transacao, LocalDate> cell = new TableCell<Transacao, LocalDate>(){

                    	protected void updateItem(LocalDate item, boolean empty) {
                            if (item != null) {
                            	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                                setText(item.format(formatter));
                            }
                        }                    
                    };               
                    
                    cell.setAlignment(Pos.CENTER);
                    return cell;        
                }

            });
        	
        	table.setTableMenuButtonVisible(false);
        	table.setItems(transacoes2);
    	}
    }
    	
    private class Eventos implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent evento) {
			if (evento.getSource() == adicionarTransacao) {
				try {
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../gui/TelaAdicionarTransacao.fxml"));     
					Parent root = (Parent)fxmlLoader.load();          
					ControllerAdicionarTransacao controller = fxmlLoader.<ControllerAdicionarTransacao>getController();
					controller.setUsuario(usuarioAtivo);
					content.getChildren().setAll(root);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else if (evento.getSource() == botaoSair) {
				try {
					content.getChildren().setAll(
							FXMLLoader.load(getClass().getResource(
									"../gui/TelaPrincipal.fxml")));
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else if (evento.getSource() == adicionarCategoria) {
				try {
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../gui/TelaAdicionarCategoria.fxml"));     
					Parent root = (Parent)fxmlLoader.load();          
					ControllerAdicionaCategoria controller = fxmlLoader.<ControllerAdicionaCategoria>getController();
					controller.setUsuario(usuarioAtivo);
					content.getChildren().setAll(root);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else if (evento.getSource() == removerCategoria) {
				try {
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../gui/TelaRemoverCategoria.fxml"));     
					Parent root = (Parent)fxmlLoader.load();          
					ControllerRemoverCategoria controller = fxmlLoader.<ControllerRemoverCategoria>getController();
					controller.setUsuario(usuarioAtivo);
					content.getChildren().setAll(root);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else if (evento.getSource() == editarCategoria) {
				try {
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../gui/TelaEditarCategoria.fxml"));     
					Parent root = (Parent)fxmlLoader.load();          
					ControllerEditarCategoria controller = fxmlLoader.<ControllerEditarCategoria>getController();
					controller.setUsuario(usuarioAtivo);
					content.getChildren().setAll(root);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else if (evento.getSource() == removerTransacao) {
				try {
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../gui/TelaRemoverTransacao.fxml"));     
					Parent root = (Parent)fxmlLoader.load();          
					ControllerRemoverTransacao controller = fxmlLoader.<ControllerRemoverTransacao>getController();
					controller.setUsuario(usuarioAtivo);
					content.getChildren().setAll(root);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else if (evento.getSource() == editarTransacao) {
				try {
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../gui/TelaEditarTransacaoInicial.fxml"));     
					Parent root = (Parent)fxmlLoader.load();          
					ControllerEditarTransacaoInicial controller = fxmlLoader.<ControllerEditarTransacaoInicial>getController();
					controller.setUsuario(usuarioAtivo);
					content.getChildren().setAll(root);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else if (evento.getSource() == botaoRelatorio) {
				try {
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../gui/TelaGerarRelatorio.fxml"));     
					Parent root = (Parent)fxmlLoader.load();  
					ControllerGerarRelatorio controller = fxmlLoader.<ControllerGerarRelatorio>getController();
					controller.setUsuario(usuarioAtivo);
					content.getChildren().setAll(root);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else if (evento.getSource() == adicionarOrcamento) {
				try {
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../gui/TelaAdicionarOrcamento.fxml"));     
					Parent root = (Parent)fxmlLoader.load();          
					ControllerAdicionarOrcamento controller = fxmlLoader.<ControllerAdicionarOrcamento>getController();
					controller.setUsuario(usuarioAtivo);
					content.getChildren().setAll(root);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else if (evento.getSource() == adicionarConta) {
				try {
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../gui/TelaAdicionarConta.fxml"));     
					Parent root = (Parent)fxmlLoader.load();          
					ControllerAdicionarConta controller = fxmlLoader.<ControllerAdicionarConta>getController();
					controller.setUsuario(usuarioAtivo);
					content.getChildren().setAll(root);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else if (evento.getSource() == removerConta) {
				try {
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../gui/TelaRemoverConta.fxml"));     
					Parent root = (Parent)fxmlLoader.load();          
					ControllerRemoverConta controller = fxmlLoader.<ControllerRemoverConta>getController();
					controller.setUsuario(usuarioAtivo);
					content.getChildren().setAll(root);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else if (evento.getSource() == editarConta) {
				try {
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../gui/TelaEditarConta.fxml"));     
					Parent root = (Parent)fxmlLoader.load();          
					ControllerEditarConta controller = fxmlLoader.<ControllerEditarConta>getController();
					controller.setUsuario(usuarioAtivo);
					content.getChildren().setAll(root);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
    }
}
