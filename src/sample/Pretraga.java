package sample;

import javafx.collections.ObservableList;

import java.io.File;

public class Pretraga implements Runnable {
    private File file;
    private Controller controller;
    private String podstring = "";
    private ObservableList<File> list;

    @Override
    public void run() {
        for (int i=1; i<=100; i++)
            System.out.print(" " + i);

        try {
            trazi(file); // pocinjemo pretragu
        } catch (Exception e) {
            System.out.println("Izuzetak " + e.getMessage());
        }
    }


    public Pretraga (Controller controller) {
        file = new File (System.getProperty("user.home")); // krecemo od home direktorija
        this.controller = controller;
        podstring = podstring + controller.traziBtn.getText().trim(); // ovaj podstring trazimo u nazivima fileova
        list = controller.fileList();
    }

    public void trazi (File file) throws Exception {
        if (file.isFile()) { // ako je file
            if (file.getName().contains(podstring)) { // provjeravamo mu naziv
                if (list != null) {
                    list.add(file);
                }
            }
        } else { // ako je folder
            try {
                for (File f : file.listFiles()) { // za svaki file
                    trazi(f); // rekurzivno pozivamo funkciju
                }
            } catch (NullPointerException e) {
                System.out.println("Izuzetak " + e.getMessage());
            }
        }
    }
}
