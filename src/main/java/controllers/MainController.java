package controllers;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.text.Text;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import helpers.DistanceMatrix;

public class MainController implements Initializable {
    @FXML
    public Button rand;
    @FXML
    public Text OutputText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        rand.setOnDragOver((EventHandler<Event>) event -> {
            DragEvent devent = (DragEvent) event;
            if (devent.getDragboard().hasFiles()) {
                devent.acceptTransferModes(TransferMode.ANY);
            }
            event.consume();
        });
        rand.setOnDragDropped(event -> {
            List<File> files = event.getDragboard().getFiles();
            FileHandler(files.get(0));
            event.consume();
        });
    }

    private void FileHandler(File file){
        if(file.getName().endsWith(".xml")){
            DistanceMatrix output = new DistanceMatrix(file);
            OutputText.setText(String.valueOf(output.matrix.length));
        }else if(file.getName().endsWith(".dm")){
            DistanceMatrix output = DistanceMatrix.load(file);
            OutputText.setText(String.valueOf(output.matrix.length));
        }else{
            System.out.println("wrong file format");
        }

    }

    public void generateRandom() {

    }
}
