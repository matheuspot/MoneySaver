package controllers;

import java.time.LocalDate;

import fonte.Categoria;
import fonte.Transacao;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

public class ControllerLista {

    @FXML
    private Label labelAviso;

    @FXML
    private Button botaoVoltar;

    @FXML
    private AnchorPane content;

    @FXML
    private TableView<?> table;
    
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

   

}
