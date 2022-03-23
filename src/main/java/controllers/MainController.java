package controllers;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.List;
import java.util.ResourceBundle;

import helpers.DistanceMatrix;
import helpers.Routes;
import javafx.stage.Stage;

/**
 * main view controller
 */
public class MainController implements Initializable {

    @FXML
    public Text OutputText;
    @FXML
    public AnchorPane background;
    @FXML
    public Button euclidian;

    /**
     * initializes drag and drop function for button
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        background.setOnDragOver((EventHandler<Event>) event -> {
            DragEvent devent = (DragEvent) event;
            if (devent.getDragboard().hasFiles()) {
                devent.acceptTransferModes(TransferMode.ANY);
            }
            event.consume();
        });
        background.setOnDragDropped(event -> {
            List<File> files = event.getDragboard().getFiles();
            FileHandler(files.get(0));
            event.consume();
        });
    }

    @FXML
    private void displayEuclidean() throws IOException {
        Stage stage;
        Parent root;

        stage = (Stage) euclidian.getScene().getWindow();
        root = FXMLLoader.load(Routes.viewsRoute("WorkingView.fxml"));
        Scene scene = new Scene(root, 800, 600);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * handles the file passed to the program
     */
    private void FileHandler(File file) {
        if (file.getName().endsWith(".xml")) {
            DistanceMatrix output = new DistanceMatrix(file);
            displayMatrix(output);
        } else if (file.getName().endsWith(".dm")) {
            DistanceMatrix output = DistanceMatrix.load(file);
            if (output != null) {
                displayMatrix(output);
            }
        } else {
            System.out.println("wrong file format");
        }

    }

    public void generateRandomMatrixS() {
        DistanceMatrix output = new DistanceMatrix(20, DistanceMatrix.types.SYMMETRIC);
        displayMatrix(output);
    }

    public void generateRandomMatrixAS() {
        DistanceMatrix output = new DistanceMatrix(20, DistanceMatrix.types.ASYMMETRIC);
        displayMatrix(output);
    }

    private void displayMatrix(DistanceMatrix dm) {
        Stage stage = new Stage();
        Group group = new Group();
        int size = 800;
        Scene scene = new Scene(group, size, size);
        stage.setScene(scene);
        stage.setTitle("Distance Matrix");
        int sideLength = dm.matrix.length;
        // double offSet = size / (double) sideLength;
        DecimalFormat df = new DecimalFormat("0.000");
        GridPane gl = new GridPane();
        for (int i = 0; i < sideLength; i++) {
            for (int j = 0; j < sideLength; j++) {
                Label newL = new Label(df.format(dm.matrix[i][j]));
                newL.setTooltip(new Tooltip(i + " -> " + j));
                gl.add(newL, i, j);
            }
        }
        gl.setHgap(5);
        gl.setVgap(5);
        group.getChildren().add(gl);
        final Point delta = new Point();
        group.setOnMousePressed(mouseEvent -> {
            // record a delta distance for the drag and drop operation.
            delta.x = (int) (group.getLayoutX() - mouseEvent.getSceneX());
            delta.y = (int) (group.getLayoutY() - mouseEvent.getSceneY());
        });

        group.setOnMouseDragged(mouseEvent -> {
            group.setLayoutX(mouseEvent.getSceneX() + delta.x);
            group.setLayoutY(mouseEvent.getSceneY() + delta.y);
        });

        group.setOnScroll(scrollEvent -> {
            double newScale = group.getScaleX() + scrollEvent.getDeltaY() / 80 > 0
                    ? group.getScaleX() + scrollEvent.getDeltaY() / 80
                    : 0.4;
            group.setScaleX(newScale);
            System.out.println(newScale);
            group.setScaleY(newScale);
        });
        stage.show();
    }

}
