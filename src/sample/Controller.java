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
    @FXML
    public Button prekiniBtn;
    Thread thread;

    public void initialize (URL url, ResourceBundle rb) {
        prekiniBtn.setDisable(true);
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
        traziBtn.setDisable(true);
        prekiniBtn.setDisable(false);
        list.getSelectionModel().clearSelection();
        list.getItems().clear();
        Pretraga pretraga = new Pretraga ();
        thread = new Thread (pretraga);
        thread.start();
    }

    public void onPrekini (ActionEvent actionEvent) {
        traziBtn.setDisable(false);
        prekiniBtn.setDisable(true);
        thread.stop();
    }

    public void onPosalji (ActionEvent event) {

    }

    public void trazi (File file, String podstring) throws Exception {
        for (File f : file.listFiles()) {
            if (f.isFile()) { // ako je file
                if (f.getName().contains(podstring)) { // provjeravamo mu naziv
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
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
