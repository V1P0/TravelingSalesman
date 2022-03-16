package helpers;

import types.TSPdata;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;

public class Euclidean implements TSPdata {
    double[] xPoints;
    double[] yPoints;
    Random rand = new Random();

    public Euclidean(int n) {
        xPoints = new double[n];
        yPoints = new double[n];
        for (int i = 0; i < n; i++) {
            xPoints[i] = rand.nextInt(1000);
            yPoints[i] = rand.nextInt(1000);
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
        return 0;
    }

    // !TODO:
    // Liczymy koszty dla podanych liczb, gdzie koszt to odleglosc miedzy tymi
    // punktami, wszystkimi i zwracamy sume
    // I wyswietlanie jakies fajnie ezez. Wyseitlanei punktow

}
