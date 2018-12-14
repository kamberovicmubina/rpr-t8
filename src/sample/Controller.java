package sample;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class Controller implements Initializable {
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
    public File izabraniFile;

    public void initialize (URL url, ResourceBundle rb) {
        prekiniBtn.setDisable(true);
        list.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue){
                if (newValue != null) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("posiljka.fxml"));
                    Parent root = null;
                    try {
                        root = loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Stage secondaryStage = new Stage();
                    secondaryStage.setTitle("Posiljka");
                    secondaryStage.setScene(new Scene(root, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE));
                    secondaryStage.initModality(Modality.APPLICATION_MODAL);
                    if (list.getSelectionModel().getSelectedItem() != null)
                        izabraniFile = new File(list.getSelectionModel().getSelectedItem());
                    secondaryStage.show();
                }
            }
        });
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
