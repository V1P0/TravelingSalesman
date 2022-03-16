package controllers;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import helpers.DistanceMatrix;

public class MainController implements Initializable {
    @FXML
    public Button rand;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        rand.setOnDragOver((EventHandler<Event>) event -> {
            DragEvent devent = (DragEvent) event;
            if (devent.getDragboard().hasFiles()) {
                devent.acceptTransferModes(TransferMode.ANY);
            }
            event.consume();
        });
        rand.setOnDragDropped(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                List<File> files = event.getDragboard().getFiles();
                System.out.println("Got " + files.size() + " files");
                new DistanceMatrix(files.get(0));
                event.consume();
            }
        });
    }

    public void generateRandom() {

    }
}
