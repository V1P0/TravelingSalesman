package helpers;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class TSPLoader {
    ArrayList<String[]> storing;

    public TSPLoader(File file) throws Exception {
        Scanner sc = new Scanner(file);
        storing = new ArrayList<String[]>();
        String nextValue = null;
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if ("NODE_COORD_SECTION".equals(line)) {
                while (sc.hasNextLine()) {
                    nextValue = sc.nextLine();
                    storing.add(nextValue.trim().split("\\s+"));
                }
            }
        }
        sc.close();
    }

    public static Euclidean returnScanner(File file) throws Exception {
        ArrayList<String[]> tree = new TSPLoader(file).storing;
        int[] xPoints = new int[tree.size() - 1];
        int[] yPoints = new int[tree.size() - 1];
        for (int i = 0; i < tree.size() - 2; i++) {
            try {
                xPoints[i] = Integer.parseInt(tree.get(i)[1]);
                yPoints[i] = Integer.parseInt(tree.get(i)[2]);
            } catch (NumberFormatException e) {
                xPoints[i] = (int) (Double.parseDouble(tree.get(i)[1]) + 0.5);
                yPoints[i] = (int) (Double.parseDouble(tree.get(i)[2]) + 0.5);
            }
        }
        return new Euclidean(xPoints, yPoints);
    }

}