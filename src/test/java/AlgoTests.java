import helpers.DistanceMatrix;
import helpers.Euclidean;
import helpers.TSPLoader;

import org.apache.commons.math3.stat.inference.WilcoxonSignedRankTest;
import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/*
Wymagania na oceną 5 (na za tydzień):

Przeprowadzenie eksperymentów dla wariantów algorytmów (na przykład przetestować wpływ
metody zadawania rozwiązania początkowego dla alg. 2-opt).

Zbadanie zachowania algorytmów w czasie: jak zmienia się jakość najlepszego rozwiązania w
zależności od czasu (oczywiście nie dotyczy alg. najbliższego sąsiada).
Jak zmienia się czas konieczny do zakończenia działania algorytmu 2-opt w zależności
od jakości rozwiązania startowego (losowe vs. najbliższy sąsiad)?
 */

public class AlgoTests {

    String destination = "P:\\TravelingSalesman\\data";

    @Test
    public void testRandom() throws Exception {
        Euclidean eu = TSPLoader.returnScanner(new File("data/berlin52.tsp"));
        DistanceMatrix dm = new DistanceMatrix(eu);
        // różne wartości k dla krandom
        PrintWriter writer = new PrintWriter(destination + "random_different_k_values.csv", StandardCharsets.UTF_8);
        for (int k = 1; k <= 100000; k += 10) {
            writer.println(k + " , " + dm.cost(dm.kRandom(k)));
        }
        writer.close();
    }

    @Test
    public void test2Opt() throws Exception {
        Euclidean eu = TSPLoader.returnScanner(new File("data/berlin52.tsp"));
        DistanceMatrix dm = new DistanceMatrix(eu);
        PrintWriter writer = new PrintWriter(destination + "2opt_differences.csv", StandardCharsets.UTF_8);
        for (int k = 1; k <= 1000; k += 10) {
            List<Integer> list = dm.kRandom(100);
            writer.println(dm.cost(list) + " , " + dm.cost(dm.twoOptAcc(list)));
        }
        writer.close();
    }

    @Test
    public void test2Opt2() throws Exception {
        Euclidean eu = TSPLoader.returnScanner(new File("data/berlin52.tsp"));
        DistanceMatrix dm = new DistanceMatrix(eu);
        PrintWriter writer = new PrintWriter(destination + "2opt_histogram.csv", StandardCharsets.UTF_8);
        for (int k = 1; k <= 100000; k += 10) {
            writer.println(dm.cost(dm.twoOptAcc(dm.kRandom(100))));
        }
        writer.close();
    }

    @Test
    public void basicTests() throws Exception {
        Euclidean eu = TSPLoader.returnScanner(new File("data/berlin52.tsp"));
        DistanceMatrix dm = new DistanceMatrix(eu);
        System.out.println(dm.cost(dm.nearest()));

    }

    @Test
    public void nearestTest() throws Exception {
        Euclidean eu = TSPLoader.returnScanner(new File("data/berlin52.tsp"));
        DistanceMatrix dm = new DistanceMatrix(eu);
        PrintWriter writer = new PrintWriter(destination + "nearest_different_starting_points.csv",
                StandardCharsets.UTF_8);
        for (int i = 0; i < 52; i++) {
            writer.println(i + " , " + dm.cost(dm.nearest(i)));
        }
        writer.close();
    }

    @Test
    public void twoOptTimeTest() throws Exception {
        Euclidean eu = TSPLoader.returnScanner(new File("data/berlin52.tsp"));
        DistanceMatrix dm = new DistanceMatrix(eu);
        PrintWriter writer = new PrintWriter(destination + "2opt_time_test.csv", StandardCharsets.UTF_8);
        for (int k = 1; k <= 1000; k++) {
            List<Integer> list = dm.kRandom(100);
            long time = System.currentTimeMillis();
            List<Integer> result = dm.twoOpt(list);
            time = System.currentTimeMillis() - time;
            writer.println(dm.cost(list) + " , " + time);
        }
        List<Integer> list = dm.nearest();
        long time = System.currentTimeMillis();
        List<Integer> result = dm.twoOpt(list);
        time = System.currentTimeMillis() - time;
        System.out.println(time);
        writer.close();
    }

    @Test
    public void multithreadedTest() throws Exception {
        Euclidean eu = TSPLoader.returnScanner(new File("data/berlin52.tsp"));
        DistanceMatrix dm = new DistanceMatrix(eu);
        PrintWriter writer = new PrintWriter(destination + "multithreaded_k_random.csv", StandardCharsets.UTF_8);
        long time = System.currentTimeMillis();
        dm.kRandom(576000);
        time = System.currentTimeMillis() - time;
        // writer.println(1 + " , " +time);
        System.out.println(time);
        for (int i = 1; i < 13; i++) {
            int count = 576000 / i;
            time = System.currentTimeMillis();
            dm.kRandomThreaded(count, i);
            time = System.currentTimeMillis() - time;
            writer.println(i + " , " + time);
            System.out.println(time);
        }
        writer.close();
    }

    @Test
    public void threeOptTest() throws Exception {
        Euclidean eu = TSPLoader.returnScanner(new File("data/berlin52.tsp"));
        DistanceMatrix dm = new DistanceMatrix(eu);
        PrintWriter writer = new PrintWriter(destination + "3opt_test.csv", StandardCharsets.UTF_8);
        for (int k = 1; k <= 1000; k++) {
            List<Integer> list = dm.kRandom(100);
            long time2opt = System.currentTimeMillis();
            List<Integer> result = dm.twoOptAcc(list);
            time2opt = System.currentTimeMillis() - time2opt;
            List<Integer> xd = new ArrayList<>(list);
            long time3opt = System.currentTimeMillis();
            List<Integer> result3 = dm.threeOpt(xd);
            time3opt = System.currentTimeMillis() - time3opt;
            writer.println(time2opt + " , " + time3opt + " , " + (dm.cost(list) - dm.cost(result)) + " , "
                    + (dm.cost(list) - dm.cost(result3)));
        }
        writer.close();
    }

    @Test
    public void threevstwoTest() throws Exception {
        Euclidean eu = TSPLoader.returnScanner(new File("data/berlin52.tsp"));
        DistanceMatrix dm = new DistanceMatrix(eu);
        PrintWriter writer = new PrintWriter(destination + "3vs2.csv", StandardCharsets.UTF_8);
        for (int k = 1; k <= 1000; k++) {
            List<Integer> list = dm.kRandom(5);
            List<Integer> xd = new ArrayList<>(list);
            List<Integer> result3 = dm.threeOpt(xd);
            List<Integer> result = dm.twoOptAcc(list);
            writer.println(dm.cost(result) + " " + dm.cost(result3));
        }
        writer.close();
    }

    @Test
    public void ThreeVsTwoVsTime() throws Exception {

        PrintWriter writer = new PrintWriter(destination + "bigCompare.csv", StandardCharsets.UTF_8);

        // for (int i = 50; i <= 250; i += 50) {
        Euclidean euc = new Euclidean(250);
        DistanceMatrix matrix = new DistanceMatrix(euc);
        long avgTime3opt = 0;
        long avgTime2opt = 0;
        long avgTime2optAcc = 0;
        long avgCost3opt = 0;
        long avgCost2opt = 0;
        long avgCost2optAcc = 0;

        for (int j = 0; j <= 10; j++) {
            long time3opt = 0;
            long time2optAcc = 0;
            long time2opt = 0;

            List<Integer> listBasic1 = matrix.kRandom(5);
            List<Integer> listBasic2 = new ArrayList<>(listBasic1);
            List<Integer> listBasic3 = new ArrayList<>(listBasic1);

            time3opt = System.currentTimeMillis();
            avgCost3opt += matrix.cost(matrix.threeOpt(listBasic3));
            time3opt = System.currentTimeMillis() - time3opt;

            time2optAcc = System.currentTimeMillis();
            avgCost2optAcc += matrix.cost(matrix.twoOptAcc(listBasic2));
            time2optAcc = System.currentTimeMillis() - time2optAcc;

            time2opt = System.currentTimeMillis();
            avgCost2opt += matrix.cost(matrix.twoOpt(listBasic3));
            time2opt = System.currentTimeMillis() - time2opt;

            avgTime3opt += time3opt;
            avgCost2optAcc += avgTime2optAcc += time2optAcc;
            avgTime2opt += time2opt;

        }

        avgTime3opt /= 10;
        avgTime2optAcc /= 10;
        avgTime2opt /= 10;

        avgCost3opt /= 10;
        avgCost2optAcc /= 10;
        avgCost2opt /= 10;

        writer.println("Wielkość Instancji: " + 100 + "\n" + "3opt: " + avgTime3opt + " | " + avgCost3opt + "\n"
                + "2opt: "
                + avgTime2opt + " | " + avgCost2opt + "\n" + "2optAcc: " + avgTime2optAcc + " | " + avgCost2optAcc);
        // }

        writer.close();
    }

    @Test
    public void WilixonTest() throws Exception {
        WilcoxonSignedRankTest test = new WilcoxonSignedRankTest();
        Euclidean euc = TSPLoader.returnScanner(new File("data/berlin52.tsp"));
        DistanceMatrix matrix = new DistanceMatrix(euc);
        PrintWriter writer = new PrintWriter(destination + "Wilcoxon.csv", StandardCharsets.UTF_8);
        // double[] kRadnomResults = new double[20];
        double[] twoOptResults = new double[20];
        double[] twoOptAccResults = new double[20];
        double[] threeOptResults = new double[20];
        double[] nearest = new double[20];

        for (int i = 0; i < 19; i++) {
            // List<Integer> listKRadnom = matrix.kRandom(30);
            // kRadnomResults[i] = matrix.cost(listKRadnom);
            List<Integer> nearestList = matrix.nearest(30);
            nearest[i] = matrix.cost(nearestList);

            List<Integer> list2opt = new ArrayList<>(nearestList);
            List<Integer> list2optAcc = new ArrayList<>(nearestList);
            List<Integer> list3opt = new ArrayList<>(nearestList);
            twoOptResults[i] = matrix.cost(matrix.twoOpt(list2opt));
            twoOptAccResults[i] = matrix.cost(matrix.twoOptAcc(list2optAcc));
            threeOptResults[i] = matrix.cost(matrix.threeOpt(list3opt));
        }

        writer.print("[");
        for (int j = 0; j < 19; j++) {
            writer.print(nearest[j] + " , ");
        }
        writer.print("]");
        writer.println('\n');
        writer.print("[");
        for (int j = 0; j < 19; j++) {
            writer.print(twoOptResults[j] + " , ");
        }
        writer.print("]");
        writer.println('\n');
        writer.print("[");
        for (int j = 0; j < 19; j++) {
            writer.print(twoOptAccResults[j] + " , ");
        }
        writer.print("]");
        writer.println('\n');
        writer.print("[");
        for (int j = 0; j < 19; j++) {
            writer.print(threeOptResults[j] + " , ");
        }
        writer.print("]");
        writer.close();

    }
}
