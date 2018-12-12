package sample;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller {
    @FXML
    public ListView list;
    @FXML
    public Label uzorak;
    @FXML
    public TextField textBox;
    @FXML
    public Button traziBtn;

    private ObservableList<File> lista = FXCollections.observableArrayList();

    public void initialize (URL url, ResourceBundle rb) {
        list.setItems(lista);
        list.getItems().addListener((ListChangeListener) (change) ->
                list.scrollTo(list.getItems().size()-1)
        );
    }

    @FXML
    public void onTrazi(ActionEvent event) {
        Pretraga pretraga = new Pretraga(this);
        Thread thread = new Thread(pretraga);
        thread.start();
    }

    public ObservableList<File> fileList() {
        return lista;
    }
}
