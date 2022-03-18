package helpers;

import types.TSPdata;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;

import javafx.geometry.Point2D;

public class Euclidean implements TSPdata {
    double[] xPoints;
    double[] yPoints;
    double[] costs;
    Random rand = new Random();

    public Euclidean(int n) {
        xPoints = new double[n];
        yPoints = new double[n];
        for (int i = 0; i < n; i++) {
            xPoints[i] = rand.nextInt(500);
            yPoints[i] = rand.nextInt(500);
        }
    }

    @Override
    public void save(String fileName) {
        File newFile = new File(fileName);
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(newFile + ".euc"));
            oos.writeObject(this);
            oos.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static Euclidean load(File file) {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
            Euclidean dm = (Euclidean) ois.readObject();
            ois.close();
            return dm;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public double cost(int... path) {
        double sum = 0;
        for (int i = 0; i < path.length; i++) {
            Point2D point = new Point2D(xPoints[path[i]], yPoints[path[i]]);
            for (int j = i + 1; j < path.length; j++) {
                sum += point.distance(xPoints[path[j]], yPoints[path[j]]);
            }
        }

        return sum;
    }

    public Point2D[] getPoints(int... path) {
        Point2D[] retPoints = new Point2D[path.length];
        for (int i = 0; i < path.length; i++) {
            retPoints[i] = new Point2D(xPoints[path[i]], yPoints[path[i]]);
        }

        return retPoints;
    }

}
