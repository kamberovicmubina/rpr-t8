package sample;

import javafx.application.Platform;
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
    public ListView<String> list;
    @FXML
    public Label uzorak;
    @FXML
    public TextField textBox;
    @FXML
    public Button traziBtn;

    public void initialize (URL url, ResourceBundle rb) {
    }

    public class Pretraga implements Runnable {
        @Override
        public void run () {
            String podstring = textBox.getText();
            try {
                trazi(new File ("C:\\Users\\User\\Desktop"), podstring);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void onTrazi(ActionEvent event) {
        list.getSelectionModel().clearSelection();
        list.getItems().clear();
        Pretraga pretraga = new Pretraga ();
        Thread thread = new Thread (pretraga);
        thread.start();
    }

    public void trazi (File file, String podstring) throws Exception {
        for (File f : file.listFiles()) {
            if (f.isFile()) { // ako je file
                if (f.getName().contains(podstring)) { // provjeravamo mu naziv
                    Platform.runLater(() -> {
                                list.getItems().add(f.getPath());
                            }
                    );

                }
            } else { // ako je folder
                trazi(f, podstring);
            }
        }
    }
}
