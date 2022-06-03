package controllers;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.List;
import java.util.ResourceBundle;

import helpers.DistanceMatrix;
import helpers.Euclidean;
import helpers.TSPLoader;

public class WorkingController implements Initializable {

    @FXML
    public Pane surface;
    @FXML
    public Label Label;

    FileChooser fileChooser = new FileChooser();
    DistanceMatrix matrix;
    Euclidean euc;
    int[] xPoints;
    int[] yPoints;
    Group displaGroup;
    List<Integer> pointsTrace;

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
        displaGroup = new Group();
        surface.getChildren().add(displaGroup);
    }

    public void generateRandomEuclidean() {
        euc = new Euclidean(50);
        matrix = new DistanceMatrix(euc);
        displayEuclidean(euc, matrix);
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
                matrix = new DistanceMatrix(euc);
                displayEuclidean(euc, matrix);
            }
        } else if (file.getName().endsWith(".tsp")) {
            euc = TSPLoader.returnScanner(file);
            matrix = new DistanceMatrix(euc);
            displayEuclidean(euc, matrix);

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
            matrix.save(matrix + ".dm");
        }
        if (euc != null) {
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

    public void generateLines(Event e) {
        displaGroup.getChildren().removeIf(node -> node instanceof Line);
        System.out.println();
        Point2D[] points = euc.getPoints();
        switch (((MenuItem) (e.getSource())).getText()) {
            case "K-Random":
                pointsTrace = matrix.kRandom(500);
                break;
            case "K-Random Threaded":
                pointsTrace = matrix.kRandomThreaded(300, 4);
                break;
            case "nearest":
                pointsTrace = matrix.nearest();
                break;
            case "2-opt":
                if (pointsTrace == null)
                    return;
                pointsTrace = matrix.twoOpt(pointsTrace);
                break;
            case "2-opt Acc":
                if (pointsTrace == null)
                    return;
                pointsTrace = matrix.twoOptAcc(pointsTrace);
                break;
            case "3-opt":
                if (pointsTrace == null)
                    return;
                pointsTrace = matrix.threeOpt(pointsTrace);
                break;
            default:
                break;
        }

        Label.setText("Total cost: " + matrix.cost(pointsTrace));
        for (int i = 0; i < pointsTrace.size(); i++) {
            Line line = new Line(points[pointsTrace.get(i)].getX(), points[pointsTrace.get(i)].getY(),
                    points[pointsTrace.get((i + 1) % pointsTrace.size())].getX(),
                    points[pointsTrace.get((i + 1) % pointsTrace.size())].getY());

            line.setStrokeWidth(2);
            displaGroup.getChildren().add(line);
            line.addEventHandler(MouseEvent.MOUSE_ENTERED,
                    event -> Label.setText("Cost this line is: " + new Point2D(line.getStartX(),
                            line.getStartY())
                            .distance(line.getEndX(), line.getEndY())));
            line.addEventHandler(MouseEvent.MOUSE_EXITED,
                    event -> Label.setText("Total cost: " + matrix.cost(pointsTrace)));
        }
        setEvents();
    }

    private void displayMatrix(DistanceMatrix dm) {
        displaGroup.getChildren().clear();
        int sideLength = dm.matrix.length;
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
        displaGroup.getChildren().add(gl);
        setEvents();

    }

    private void displayEuclidean(Euclidean euc, DistanceMatrix matrix) {
        displaGroup.getChildren().clear();
        Point2D[] points = euc.getPoints();
        Label.setText("Total cost: ");

        Circle[] pointsToDisaply = new Circle[points.length];
        for (int i = 0; i < points.length; i++) {
            Circle circle = new Circle(points[i].getX(), points[i].getY(), 3);
            pointsToDisaply[i] = circle;
        }

        // Creating a Group object
        displaGroup.getChildren().addAll(pointsToDisaply);

        setEvents();
    }

    private void setEvents() {
        final Point delta = new Point();
        displaGroup.setOnMousePressed(mouseEvent -> {
            // record a delta distance for the drag and drop operation.
            delta.x = (int) (displaGroup.getLayoutX() - mouseEvent.getSceneX());
            delta.y = (int) (displaGroup.getLayoutY() - mouseEvent.getSceneY());
        });

        displaGroup.setOnMouseDragged(mouseEvent -> {
            displaGroup.setLayoutX(mouseEvent.getSceneX() + delta.x);
            displaGroup.setLayoutY(mouseEvent.getSceneY() + delta.y);
        });

        displaGroup.setOnScroll(scrollEvent -> {
            double newScale = displaGroup.getScaleX() + scrollEvent.getDeltaY() / 80 > 0
                    ? displaGroup.getScaleX() + scrollEvent.getDeltaY() / 80
                    : 0.4;
            displaGroup.setScaleX(newScale);
            displaGroup.setScaleY(newScale);
        });
    }
}
