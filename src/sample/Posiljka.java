package sample;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

public class Posiljka implements Initializable {
    @FXML
    public TextField imePolje;
    @FXML
    public TextField prezimePolje;
    @FXML
    public TextField adresaPolje;
    @FXML
    public TextField gradPolje;
    @FXML
    public TextField pBrojPolje;

    @Override
    public void initialize (URL url, ResourceBundle rb) {
        imePolje.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> obs, Boolean o, Boolean n) {
                if (!n) {
                    if (validnoIme(imePolje.getText())) {
                        imePolje.getStyleClass().removeAll("poljeNijeIspravno");
                        imePolje.getStyleClass().add("poljeIspravno");
                    } else {
                        imePolje.getStyleClass().removeAll("poljeIspravno");
                        imePolje.getStyleClass().add("poljeNijeIspravno");
                    }
                }
            }
        });

        prezimePolje.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> obs, Boolean o, Boolean n) {
                if (!n) {
                    if (validnoPrezime(prezimePolje.getText())) {
                        prezimePolje.getStyleClass().removeAll("poljeNijeIspravno");
                        prezimePolje.getStyleClass().add("poljeIspravno");
                    } else {
                        prezimePolje.getStyleClass().removeAll("poljeIspravno");
                        prezimePolje.getStyleClass().add("poljeNijeIspravno");
                    }
                }
            }
        });

        adresaPolje.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> obs, Boolean o, Boolean n) {
                if (!n) {
                    if (validnaAdresa(adresaPolje.getText())) {
                        adresaPolje.getStyleClass().removeAll("poljeNijeIspravno");
                        adresaPolje.getStyleClass().add("poljeIspravno");
                    } else {
                        adresaPolje.getStyleClass().removeAll("poljeIspravno");
                        adresaPolje.getStyleClass().add("poljeNijeIspravno");
                    }
                }
            }
        });

        gradPolje.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> obs, Boolean o, Boolean n) {
                if (!n) {
                    if (validnoIme(gradPolje.getText())) {  // ista pravila kao za ime
                        gradPolje.getStyleClass().removeAll("poljeNijeIspravno");
                        gradPolje.getStyleClass().add("poljeIspravno");
                    } else {
                        gradPolje.getStyleClass().removeAll("poljeIspravno");
                        gradPolje.getStyleClass().add("poljeNijeIspravno");
                    }
                }
            }
        });

        pBrojPolje.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> obs, Boolean o, Boolean n) {
                if (!n) {
                    Thread t = new Thread(() -> { validanPBroj(); });
                    t.start();
                }
            }

        });

    }

    private boolean validnoIme (String n) {
        for (char c : n.toCharArray())
        {
            if (Character.isDigit(c)) {
                return false;
            }
        }
        boolean imaSlovo = false;
        for (int i = 0; i < n.length(); i++) {
            if (((n.charAt(i) >= 'a' && n.charAt(i) <= 'z') || (n.charAt(i) >= 'A' && n.charAt(i) <= 'Z'))) {
                imaSlovo = true;
                break;
            }
        }
        return !n.trim().isEmpty() && n.trim().length() <= 20 && imaSlovo;
    }

    private boolean validnoPrezime (String n) {
        for (char c : n.toCharArray())
        {
            if (Character.isDigit(c)) {
                return false;
            }
        }
        boolean imaSlovo = false;
        for (int i = 0; i < n.length(); i++) {
            if ((n.charAt(i) >= 'a' && n.charAt(i) <= 'z') || (n.charAt(i) >= 'A' && n.charAt(i) <= 'Z')) {
                imaSlovo = true;
                break;
            }
        }
        return !n.trim().isEmpty() && n.trim().length() <= 20 && imaSlovo;
    }

    private boolean validnaAdresa (String n) {
        boolean imaSlovo = false;
        for (int i = 0; i < n.length(); i++) {
            if ((n.charAt(i) >= 'a' && n.charAt(i) <= 'z') || (n.charAt(i) >= 'A' && n.charAt(i) <= 'Z')) {
                imaSlovo = true;
                break;
            }
        }
        return imaSlovo && n.trim().length() <= 20;
    }

    private boolean validanPBroj () {
        String broj = pBrojPolje.getText();
        String adresa = "http://c9.etf.unsa.ba/proba/postanskiBroj.php?postanskiBroj=";
        String rezultat = null;

        try {
            URL url = new URL(adresa+broj);
            BufferedReader ulaz = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
            rezultat = ulaz.readLine();
        } catch (Exception e) {
            System.out.println("Izuzetak " + e.getMessage());
        }

        if (rezultat.equals("OK")) {
            Platform.runLater( () -> {
                pBrojPolje.getStyleClass().removeAll("poljeNijeIspravno");
                pBrojPolje.getStyleClass().add("poljeIspravno");
            });
        } else {
            Platform.runLater( () -> {
                pBrojPolje.getStyleClass().removeAll("poljeIspravno");
                pBrojPolje.getStyleClass().add("poljeNijeIspravno");
            });
        }
        return rezultat.equals("OK");
    }

    private boolean validnaForma () {
        return validnoIme(imePolje.getText()) && validnoPrezime(prezimePolje.getText())
                && validnaAdresa(adresaPolje.getText()) && validnoIme(gradPolje.getText()) && validanPBroj();
    }

    private void uspjeh () {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Obavjestenje");

        alert.setContentText("File uspjesno poslan!" );
        alert.showAndWait();
    }

    private void upozorenje () {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Obavjestenje");

        alert.setContentText("Forma koju ste unijeli nije validna");
        alert.showAndWait();
    }

    public void onPosalji (ActionEvent event) {
        if (validnaForma()) {
            uspjeh();
        } else {
            upozorenje();
        }
    }

}
