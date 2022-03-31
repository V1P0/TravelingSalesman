import helpers.DistanceMatrix;
import helpers.Euclidean;
import helpers.TSPLoader;
import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
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

    String destination = "C:\\Users\\oem\\PycharmProjects\\MetaCsv\\";

    @Test
    public void testRandom() throws Exception {
        Euclidean eu = TSPLoader.returnScanner(new File("data/berlin52.tsp"));
        DistanceMatrix dm = new DistanceMatrix(eu);
        //różne wartości k dla krandom
        PrintWriter writer = new PrintWriter(destination + "random_different_k_values.csv", StandardCharsets.UTF_8);
        for (int k = 1; k <= 100000; k+=10) {
            writer.println(k + " , "+dm.cost(dm.kRandom(k)));
        }
        writer.close();
    }

    @Test
    public void test2Opt() throws Exception {
        Euclidean eu = TSPLoader.returnScanner(new File("data/berlin52.tsp"));
        DistanceMatrix dm = new DistanceMatrix(eu);
        PrintWriter writer = new PrintWriter(destination+"2opt_differences.csv", StandardCharsets.UTF_8);
        for (int k = 1; k <= 1000; k+=10) {
            List<Integer> list = dm.kRandom(100);
            writer.println(dm.cost(list) + " , "+dm.cost(dm.twoOptAcc(list)));
        }
        writer.close();
    }

    @Test
    public void test2Opt2() throws Exception {
        Euclidean eu = TSPLoader.returnScanner(new File("data/berlin52.tsp"));
        DistanceMatrix dm = new DistanceMatrix(eu);
        PrintWriter writer = new PrintWriter(destination+"2opt_histogram.csv", StandardCharsets.UTF_8);
        for (int k = 1; k <= 100000; k+=10) {
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
        PrintWriter writer = new PrintWriter(destination+"nearest_different_starting_points.csv", StandardCharsets.UTF_8);
        for (int i = 0; i < 52; i++) {
            writer.println(i+" , "+dm.cost(dm.nearest(i)));
        }
        writer.close();
    }

    @Test
    public void twoOptTimeTest() throws Exception {
        Euclidean eu = TSPLoader.returnScanner(new File("data/berlin52.tsp"));
        DistanceMatrix dm = new DistanceMatrix(eu);
        PrintWriter writer = new PrintWriter(destination+"2opt_time_test.csv", StandardCharsets.UTF_8);
        for (int k = 1; k <= 1000; k++) {
            List<Integer> list = dm.kRandom(100);
            long time = System.currentTimeMillis();
            List<Integer> result = dm.twoOpt(list);
            time = System.currentTimeMillis() - time;
            writer.println(dm.cost(list) + " , "+time);
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
        PrintWriter writer = new PrintWriter(destination+"multithreaded_k_random.csv", StandardCharsets.UTF_8);
        long time = System.currentTimeMillis();
        dm.kRandom(576000);
        time = System.currentTimeMillis() - time;
        //writer.println(1 + " , " +time);
        System.out.println(time);
        for (int i = 1; i < 13; i++) {
            int count = 576000/i;
            time = System.currentTimeMillis();
            dm.kRandomThreaded(count, i);
            time = System.currentTimeMillis() - time;
            writer.println(i + " , " +time);
            System.out.println(time);
        }
        writer.close();
    }

    @Test
    public void threeOptTest() throws Exception {
        Euclidean eu = TSPLoader.returnScanner(new File("data/berlin52.tsp"));
        DistanceMatrix dm = new DistanceMatrix(eu);
        PrintWriter writer = new PrintWriter(destination+"3opt_test.csv", StandardCharsets.UTF_8);
        for (int k = 1; k <= 1000; k++) {
            List<Integer> list = dm.kRandom(100);
            long time2opt = System.currentTimeMillis();
            List<Integer> result = dm.twoOptAcc(list);
            time2opt = System.currentTimeMillis() - time2opt;
            List<Integer> xd = new ArrayList<>(list);
            long time3opt = System.currentTimeMillis();
            List<Integer> result3 = dm.threeOptAcc(xd);
            time3opt = System.currentTimeMillis() - time3opt;
            writer.println(time2opt + " , "+time3opt + " , "+(dm.cost(list) - dm.cost(result))+ " , "+(dm.cost(list) - dm.cost(result3)));
        }
        writer.close();
    }

    @Test
    public void threevstwoTest() throws Exception {
        Euclidean eu = TSPLoader.returnScanner(new File("data/berlin52.tsp"));
        DistanceMatrix dm = new DistanceMatrix(eu);
        PrintWriter writer = new PrintWriter(destination+"3vs2.csv", StandardCharsets.UTF_8);
        for (int k = 1; k <= 1000; k++) {
            List<Integer> list = dm.kRandom(100);
            List<Integer> xd = new ArrayList<>(list);
            List<Integer> result3 = dm.threeOptAcc(xd);
            List<Integer> result = dm.twoOptAcc(list);
            writer.println(k + " , "+(dm.cost(result3) - dm.cost(result)));
        }
        writer.close();
    }
}
