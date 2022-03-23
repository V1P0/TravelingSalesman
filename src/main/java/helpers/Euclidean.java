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
    int[] xPoints;
    int[] yPoints;
    double[] costs;
    Random rand = new Random();

    public Euclidean(int n) {
        xPoints = new int[n];
        yPoints = new int[n];
        for (int i = 0; i < n; i++) {
            xPoints[i] = rand.nextInt(950);
            yPoints[i] = rand.nextInt(650);
        }
    }

    public Euclidean(int[] xPointsProp, int[] yPointsProp) {
        this.xPoints = new int[xPointsProp.length];
        xPoints = xPointsProp;
        this.yPoints = new int[yPointsProp.length];
        yPoints = yPointsProp;
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

    public long cost(int... path) {
        int sum = 0;
        for (int i = 0; i < path.length; i++) {
            Point2D point = new Point2D(xPoints[path[i]], yPoints[path[i]]);
            for (int j = i + 1; j < path.length; j++) {
                sum += point.distance(xPoints[path[j]], yPoints[path[j]]);
            }
        }

        return sum;
    }

    public Point2D[] getPoints(int... path) {
        Point2D[] retPoints = new Point2D[xPoints.length];
        for (int i = 0; i < xPoints.length; i++) {
            retPoints[i] = new Point2D(xPoints[i], yPoints[i]);
        }

        return retPoints;
    }

}
