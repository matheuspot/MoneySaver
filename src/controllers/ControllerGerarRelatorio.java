package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;

public class ControllerGerarRelatorio {

    @FXML
    private Button botaoGerar;

    @FXML
    private ComboBox<?> cbCategoria;

    @FXML
    private RadioButton rbLista;

    @FXML
    private RadioButton rbDespesa;

    @FXML
    private Label labelAviso;

    @FXML
    private ComboBox<?> cbMeses1;

    @FXML
    private ComboBox<?> cbMeses;

    @FXML
    private Button botaoVoltar;

    @FXML
    private RadioButton rbHistograma;

    @FXML
    private AnchorPane content;

    @FXML
    private RadioButton rbProvento;

   

}
