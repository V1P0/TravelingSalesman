package helpers;

import javafx.geometry.Point2D;
import types.TSPdata;

public class Euclidean implements TSPdata {
    Point2D[] points;

    public Euclidean(int n) {
        points = new Point2D[n];
    }
}
