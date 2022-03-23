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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import helpers.DistanceMatrix;
import helpers.Euclidean;
import helpers.TSPLoader;

public class WorkingController implements Initializable {

    @FXML
    public AnchorPane surface;

    FileChooser fileChooser = new FileChooser();
    DistanceMatrix matrix;
    Euclidean euc;
    int[] xPoints;
    int[] yPoints;

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
            try {
                FileHandler(files.get(0));
            } catch (Exception e) {
                e.printStackTrace();
            }
            event.consume();
        });
    }

    public void generateRandomEuclidean() {
        euc = new Euclidean(50);
        displayEuclidean(euc);
    }

    /**
     * handles the file passed to the program
     * 
     * @throws Exception
     */
    private void FileHandler(File file) throws Exception {
        if (file.getName().endsWith(".xml")) {
            matrix = new DistanceMatrix(file);
            displayMatrix(matrix);
        } else if (file.getName().endsWith(".dm")) {
            matrix = DistanceMatrix.load(file);
            if (matrix != null) {
                displayMatrix(matrix);
            }
        } else if (file.getName().endsWith(".euc")) {
            euc = Euclidean.load(file);
            if (euc != null) {
                displayEuclidean(euc);
            }
        } else if (file.getName().endsWith(".tsp")) {
            ArrayList<String[]> storedValues = TSPLoader.returnScanner(file);
            xPoints = new int[storedValues.size() - 1];
            yPoints = new int[storedValues.size() - 1];
            for (int i = 0; i < storedValues.size() - 2; i++) {
                xPoints[i] = Integer.parseInt(storedValues.get(i)[1]);
                yPoints[i] = Integer.parseInt(storedValues.get(i)[2]);
            }
            euc = new Euclidean(xPoints, yPoints);
            displayEuclidean(euc);

        } else {
            System.out.println("wrong file format");
        }

    }

    public void loadFile() throws Exception {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(stage);
        FileHandler(file);
    }

    public void saveFile() {
        if (matrix != null) {
            matrix.save(matrix.toString() + ".dm");
        } else if (euc != null) {
            euc.save(euc.toString());
        }
    }

    public void generateRandomMatrixS() {
        matrix = new DistanceMatrix(20, DistanceMatrix.types.SYMMETRIC);
        displayMatrix(matrix);
    }

    public void generateRandomMatrixAS() {
        matrix = new DistanceMatrix(20, DistanceMatrix.types.ASYMMETRIC);
        displayMatrix(matrix);
    }

    private void displayMatrix(DistanceMatrix dm) {
        surface.getChildren().clear();
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
            group.setScaleY(newScale);
        });
        surface.getChildren().add(group);
    }

    private void displayEuclidean(Euclidean euc) {
        surface.getChildren().clear();
        Point2D[] points = euc.getPoints();
        Label labe = new Label("Total cost: " + euc.cost(1, 3, 6, 7));

        Circle[] pointsToDisaply = new Circle[points.length];
        for (int i = 0; i < points.length; i++) {
            Circle circle = new Circle(points[i].getX(), points[i].getY(), 3);
            pointsToDisaply[i] = circle;
        }

        // Creating a Group object
        Group root = new Group();
        root.getChildren().addAll(pointsToDisaply);
        surface.getChildren().add(labe);

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
        final Point delta = new Point();
        root.setOnMousePressed(mouseEvent -> {
            // record a delta distance for the drag and drop operation.
            delta.x = (int) (root.getLayoutX() - mouseEvent.getSceneX());
            delta.y = (int) (root.getLayoutY() - mouseEvent.getSceneY());
        });

        root.setOnMouseDragged(mouseEvent -> {
            root.setLayoutX(mouseEvent.getSceneX() + delta.x);
            root.setLayoutY(mouseEvent.getSceneY() + delta.y);
        });

        root.setOnScroll(scrollEvent -> {
            double newScale = root.getScaleX() + scrollEvent.getDeltaY() / 80 > 0
                    ? root.getScaleX() + scrollEvent.getDeltaY() / 80
                    : 0.4;
            root.setScaleX(newScale);
            root.setScaleY(newScale);
        });

        surface.getChildren().add(root);
    }
}
