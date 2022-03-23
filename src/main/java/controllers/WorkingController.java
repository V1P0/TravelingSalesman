package controllers;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Group;

import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.awt.*;
import java.io.File;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.List;
import java.util.ResourceBundle;

import helpers.DistanceMatrix;
import helpers.Euclidean;

public class WorkingController implements Initializable {

    @FXML
    public AnchorPane surface;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        surface.setOnDragOver((EventHandler<Event>) event -> {
            DragEvent devent = (DragEvent) event;
            if (devent.getDragboard().hasFiles()) {
                devent.acceptTransferModes(TransferMode.ANY);
            }
            event.consume();
        });
        surface.setOnDragDropped(event -> {
            List<File> files = event.getDragboard().getFiles();
            FileHandler(files.get(0));
            event.consume();
        });
    }

    public void generateRandomEuclidean() {
        surface.getChildren().clear();
        Euclidean euc = new Euclidean(1000);
        displayEuclidean(euc);
    }

    private void displayEuclidean(Euclidean euc) {

        Point2D[] points = euc.getPoints(1, 3, 6, 7);
        Label labe = new Label("Total cost: " + euc.cost(1, 3, 6, 7));

        Circle[] pointsToDisaply = new Circle[points.length];
        for (int i = 0; i < points.length; i++) {
            Circle circle = new Circle(points[i].getX(), points[i].getY(), 3);
            pointsToDisaply[i] = circle;
        }

        // Creating a Group object
        Group root = new Group();
        root.getChildren().addAll(pointsToDisaply);
        root.getChildren().add(labe);

        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < points.length; j++) {
                Line line = new Line(points[i].getX(), points[i].getY(), points[j].getX(), points[j].getY());
                line.setStrokeWidth(2);
                root.getChildren().add(line);
                line.addEventHandler(MouseEvent.MOUSE_ENTERED,
                        event -> labe.setText("Cost this line is: " + new Point2D(line.getStartX(), line.getStartY())
                                .distance(line.getEndX(), line.getEndY())));
                line.addEventHandler(MouseEvent.MOUSE_EXITED,
                        event -> labe.setText("Total cost: " + euc.cost(1, 3, 6, 7)));
            }
        }

        surface.getChildren().add(root);

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
        surface.getChildren().clear();
        DistanceMatrix output = new DistanceMatrix(20, DistanceMatrix.types.SYMMETRIC);
        displayMatrix(output);
    }

    public void generateRandomMatrixAS() {
        surface.getChildren().clear();
        DistanceMatrix output = new DistanceMatrix(20, DistanceMatrix.types.ASYMMETRIC);
        displayMatrix(output);
    }

    private void displayMatrix(DistanceMatrix dm) {
        Group group = new Group();
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
        surface.getChildren().add(group);
    }
}
