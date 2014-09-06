package controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fonte.Categoria;
import fonte.Transacao;
import fonte.Usuario;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;

public class ControllerEditarTransacaoInicial {
	
	private EventHandler<ActionEvent> eventos = (EventHandler<ActionEvent>) new Eventos();
	private Usuario usuarioAtivo;
	private String[] meses = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho",
    		"Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
    private Tabela tabela;

    @FXML
    private ComboBox<String> cbMes;
    
    @FXML
    private Button botaoEditar;

    @FXML
    private Button botaoVoltar;
    
    @FXML
    private TableView<Transacao> table;
    
    @FXML
    private AnchorPane content;
    
    @FXML
    private Label labelAviso;
    
    @FXML
    private TableColumn<Transacao, Categoria> colunaCategoria;

    @FXML
    private TableColumn<Transacao, Double> colunaValor;
    
    @FXML
    private TableColumn<Transacao, LocalDate> colunaData;
    
    @FXML
    private TableColumn<Transacao, String> colunaRecorrencia;

    @FXML
    private TableColumn<Transacao, String> colunaDescricao;
    
    @FXML
	void initialize() {
    	botaoVoltar.setOnAction(eventos);
    	botaoEditar.setOnAction(eventos);
    	tabela = new Tabela();
    	cbMes.getItems().addAll(meses);
    	cbMes.valueProperty().addListener(tabela);
    }
    
    public void setUsuario(Usuario usuario){
    	usuarioAtivo = usuario;
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
    		try {
				transacoes = usuarioAtivo.getContaAtiva().listaTransacoesPeloMes(mapaMeses.get(t1));
			} catch (Exception e) {
				e.printStackTrace();
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
        	
        	colunaDescricao.setCellValueFactory(new PropertyValueFactory<Transacao, String>("descricao"));
        	colunaDescricao.setCellFactory(new Callback<TableColumn<Transacao, String>, TableCell<Transacao, String>>(){

                @Override
                public TableCell<Transacao, String> call(TableColumn<Transacao, String> param) {

                    TableCell<Transacao, String> cell = new TableCell<Transacao, String>(){

                    	protected void updateItem(String item, boolean empty) {
                            if (item != null) {
                                setText(item);
                            }
                        }                    
                    };               
                    cell.setAlignment(Pos.CENTER);
                    return cell;        
                }
            });
        	
        	colunaCategoria.setCellValueFactory(new PropertyValueFactory<Transacao, Categoria>("categoria"));
        	colunaCategoria.setCellFactory(new Callback<TableColumn<Transacao, Categoria>, TableCell<Transacao, Categoria>>(){

                @Override
                public TableCell<Transacao, Categoria> call(TableColumn<Transacao, Categoria> param) {

                    TableCell<Transacao, Categoria> cell = new TableCell<Transacao, Categoria>(){

                    	protected void updateItem(Categoria item, boolean empty) {
                            if (item != null) {
                                setText(item.getNome());
                                setTextFill(Color.valueOf(item.getCor()));
                            }
                        }                    
                    };               
                    cell.setAlignment(Pos.CENTER);
                    return cell;        
                }
            });
        	
        	colunaRecorrencia.setCellValueFactory(new PropertyValueFactory<Transacao, String>("recorrencia"));
        	colunaRecorrencia.setCellFactory(new Callback<TableColumn<Transacao, String>, TableCell<Transacao, String>>(){

                @Override
                public TableCell<Transacao, String> call(TableColumn<Transacao, String> param) {

                    TableCell<Transacao, String> cell = new TableCell<Transacao, String>(){

                    	protected void updateItem(String item, boolean empty) {
                            if (item != null) {
                                setText(item);
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
			} else if (evento.getSource() == botaoEditar) {
				if (table.getSelectionModel().getSelectedItem() == null){
					labelAviso.setText("Selecione uma transação.");
					labelAviso.setVisible(true);
					
				} else {
					try {
						FXMLLoader fxmlLoader = new FXMLLoader(getClass	().getResource("../gui/TelaEditarTransacaoFinal.fxml"));     
						Parent root = (Parent)fxmlLoader.load();          
						ControllerEditarTransacaoFinal controller = fxmlLoader.<ControllerEditarTransacaoFinal>getController();
						controller.setUsuario(usuarioAtivo, table.getSelectionModel().getSelectedItem());
						content.getChildren().setAll(root);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} 
		}
    }
}