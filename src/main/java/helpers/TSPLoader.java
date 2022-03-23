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

    public static ArrayList<String[]> returnScanner(File file) throws Exception {
        TSPLoader tree = new TSPLoader(file);
        return tree.storing;
    }

}