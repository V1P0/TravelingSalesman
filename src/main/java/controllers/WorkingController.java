package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import helpers.Euclidean;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class WorkingController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
    }

    public void generateRandomEuclidean() {
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

        // // Creating a scene object
        // Scene scene = new Scene(root, 500, 500);

        // // Setting title to the Stage
        // stage.setTitle("Line Chart");
        // // Adding scene to the stage
        // stage.setScene(scene);
        // // Displaying the contents of the stage
        // stage.show();

    }

}
