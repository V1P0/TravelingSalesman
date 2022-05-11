import TabuStuff.*;
import helpers.DistanceMatrix;
import helpers.Euclidean;
import helpers.TSPLoader;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class GenTests {

    String DESTINATION_PATH = "C:\\Users\\oem\\PycharmProjects\\metaCSV2\\data\\";

    @Test
    public void test1() throws Exception {
        Euclidean eu = TSPLoader.returnScanner(new File("data/a280.tsp"));
        DistanceMatrix dm = new DistanceMatrix(100, DistanceMatrix.types.SYMMETRIC);

        AreaGenerator normal = new TwoOptLikeGenerator();
        AreaGenerator threaded1 = new twoOptThreadedGenerator(1);
        AreaGenerator threaded6 = new twoOptThreadedGenerator(6);

        List<Integer> start = dm.kRandom(100);
        List<Integer> end = dm.kRandom(100);

        boolean[][] banned = new boolean[dm.matrix.length][dm.matrix.length];
        int[][] tabuList = new int[5][2];
        for (int i = 0; i < dm.matrix.length; i++) {
            for (int j = 0; j < dm.matrix.length; j++) {
                banned[i][j] = false;
            }
        }

        for (int i = 0; i < tabuList.length; i++) {
            tabuList[i][0] = 0;
            tabuList[i][1] = 0;
        }
        long time = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            end = normal.generateArea(start, dm.matrix, banned, tabuList, 0);
        }
        System.out.println("Normal: " + (System.currentTimeMillis() - time) + "ms");
        System.out.println(end);
        for (int i = 0; i < dm.matrix.length; i++) {
            for (int j = 0; j < dm.matrix.length; j++) {
                banned[i][j] = false;
            }
        }

        for (int i = 0; i < tabuList.length; i++) {
            tabuList[i][0] = 0;
            tabuList[i][1] = 0;
        }
        time = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            end = threaded1.generateArea(start, dm.matrix, banned, tabuList, 0);
        }
        System.out.println("Threaded1: " + (System.currentTimeMillis() - time) + "ms");
        System.out.println(end);

        for (int i = 0; i < dm.matrix.length; i++) {
            for (int j = 0; j < dm.matrix.length; j++) {
                banned[i][j] = false;
            }
        }

        for (int i = 0; i < tabuList.length; i++) {
            tabuList[i][0] = 0;
            tabuList[i][1] = 0;
        }
        time = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            end = threaded6.generateArea(start, dm.matrix, banned, tabuList, 0);
        }
        System.out.println("Threaded6: " + (System.currentTimeMillis() - time) + "ms");
        System.out.println(end);
    }

    @Test
    public void threadTest() throws Exception {
        Euclidean eu = TSPLoader.returnScanner(new File("data/a280.tsp"));
        DistanceMatrix dm = new DistanceMatrix(1000, DistanceMatrix.types.SYMMETRIC);
        PrintWriter writer = new PrintWriter(DESTINATION_PATH + "multithreaded_two_opt_gen.csv",
                StandardCharsets.UTF_8);

        for (int n = 1; n <= 10; n++) {
            dm = new DistanceMatrix(n * 100, DistanceMatrix.types.SYMMETRIC);
            for (int i = 1; i <= 12; i++) {
                twoOptThreadedGenerator t = new twoOptThreadedGenerator(i);
                List<Integer> start = dm.kRandom(100);
                List<Integer> end = dm.kRandom(100);

                boolean[][] banned = new boolean[dm.matrix.length][dm.matrix.length];
                int[][] tabuList = new int[5][2];
                for (int k = 0; k < dm.matrix.length; k++) {
                    for (int m = 0; m < dm.matrix.length; m++) {
                        banned[k][m] = false;
                    }
                }

                for (int k = 0; k < tabuList.length; k++) {
                    tabuList[k][0] = 0;
                    tabuList[k][1] = 0;
                }
                long time = System.currentTimeMillis();
                for (int m = 0; m < 100; m++) {
                    end = t.generateArea(end, dm.matrix, banned, tabuList, 0);
                }
                writer.println((n * 100) + "," + i + "," + (System.currentTimeMillis() - time));

            }
            System.out.println("Done with n = " + n);
        }
        writer.close();
    }

    @Test
    public void timeTest() throws Exception {
        DistanceMatrix berlin = new DistanceMatrix(TSPLoader.returnScanner(new File("data/berlin52.tsp")));
        DistanceMatrix bier = new DistanceMatrix(TSPLoader.returnScanner(new File("data/bier127.tsp")));
        DistanceMatrix a280 = new DistanceMatrix(TSPLoader.returnScanner(new File("data/a280.tsp")));
        DistanceMatrix ch130 = new DistanceMatrix(TSPLoader.returnScanner(new File("data/ch130.tsp")));
        DistanceMatrix ring = new DistanceMatrix(TSPLoader.returnScanner(new File("data/ring.tsp")));
        long time = 5000;
        List<Integer> start = berlin.kRandom(100);
        PrintWriter writer = new PrintWriter(DESTINATION_PATH + "berlin_reverse.csv", StandardCharsets.UTF_8);
        berlin.tabuSearchLog(new ArrayList<>(start), time, new TwoOptLikeGenerator(), 42, writer, 100);
        writer.close();
        writer = new PrintWriter(DESTINATION_PATH + "berlin_swap.csv", StandardCharsets.UTF_8);
        berlin.tabuSearchLog(new ArrayList<>(start), time, new SwapGenerator(), 42, writer, 100);
        writer.close();
        writer = new PrintWriter(DESTINATION_PATH + "berlin_insert.csv", StandardCharsets.UTF_8);
        berlin.tabuSearchLog(new ArrayList<>(start), time, new InsertGenerator(), 42, writer,100);
        writer.close();

        start = bier.kRandom(100);
        writer = new PrintWriter(DESTINATION_PATH + "bier_reverse.csv", StandardCharsets.UTF_8);
        bier.tabuSearchLog(new ArrayList<>(start), time, new TwoOptLikeGenerator(), 42, writer, 100);
        writer.close();
        writer = new PrintWriter(DESTINATION_PATH + "bier_swap.csv", StandardCharsets.UTF_8);
        bier.tabuSearchLog(new ArrayList<>(start), time, new SwapGenerator(), 42, writer, 100);
        writer.close();
        writer = new PrintWriter(DESTINATION_PATH + "bier_insert.csv", StandardCharsets.UTF_8);
        bier.tabuSearchLog(new ArrayList<>(start), time, new InsertGenerator(), 42, writer, 100);
        writer.close();

        start = a280.kRandom(100);
        writer = new PrintWriter(DESTINATION_PATH + "a280_reverse.csv", StandardCharsets.UTF_8);
        a280.tabuSearchLog(new ArrayList<>(start), time, new TwoOptLikeGenerator(), 42, writer, 100);
        writer.close();
        writer = new PrintWriter(DESTINATION_PATH + "a280_swap.csv", StandardCharsets.UTF_8);
        a280.tabuSearchLog(new ArrayList<>(start), time, new SwapGenerator(), 42, writer, 100);
        writer.close();
        writer = new PrintWriter(DESTINATION_PATH + "a280_insert.csv", StandardCharsets.UTF_8);
        a280.tabuSearchLog(new ArrayList<>(start), time, new InsertGenerator(), 42, writer, 100);
        writer.close();

        start = ch130.kRandom(100);
        writer = new PrintWriter(DESTINATION_PATH + "ch130_reverse.csv", StandardCharsets.UTF_8);
        ch130.tabuSearchLog(new ArrayList<>(start), time, new TwoOptLikeGenerator(), 42, writer, 100);
        writer.close();
        writer = new PrintWriter(DESTINATION_PATH + "ch130_swap.csv", StandardCharsets.UTF_8);
        ch130.tabuSearchLog(new ArrayList<>(start), time, new SwapGenerator(), 42, writer, 100);
        writer.close();
        writer = new PrintWriter(DESTINATION_PATH + "ch130_insert.csv", StandardCharsets.UTF_8);
        ch130.tabuSearchLog(new ArrayList<>(start), time, new InsertGenerator(), 42, writer, 100);
        writer.close();

        start = ring.kRandom(100);
        writer = new PrintWriter(DESTINATION_PATH + "ring_reverse.csv", StandardCharsets.UTF_8);
        ring.tabuSearchLog(new ArrayList<>(start), time, new TwoOptLikeGenerator(), 42, writer, 100);
        writer.close();
        writer = new PrintWriter(DESTINATION_PATH + "ring_swap.csv", StandardCharsets.UTF_8);
        ring.tabuSearchLog(new ArrayList<>(start), time, new SwapGenerator(), 42, writer, 100);
        writer.close();
        writer = new PrintWriter(DESTINATION_PATH + "ring_insert.csv", StandardCharsets.UTF_8);
        ring.tabuSearchLog(new ArrayList<>(start), time, new InsertGenerator(), 42, writer, 100);
        writer.close();

    }

    @Test
    public void patienceTest() throws Exception {
        DistanceMatrix berlin = new DistanceMatrix(TSPLoader.returnScanner(new File("data/berlin52.tsp")));
        DistanceMatrix bier = new DistanceMatrix(TSPLoader.returnScanner(new File("data/bier127.tsp")));
        DistanceMatrix a280 = new DistanceMatrix(TSPLoader.returnScanner(new File("data/a280.tsp")));
        DistanceMatrix ch130 = new DistanceMatrix(TSPLoader.returnScanner(new File("data/ch130.tsp")));
        DistanceMatrix ring = new DistanceMatrix(TSPLoader.returnScanner(new File("data/ring.tsp")));

        int time = 500;
        List<Integer> start = bier.kRandom(100);
        for (int i = 10; i < 1000; i*=1.5) {
            System.out.println(i);
            PrintWriter writer = new PrintWriter(DESTINATION_PATH + "patience\\bier"+i+".csv", StandardCharsets.UTF_8);
            bier.tabuSearchLog(new ArrayList<>(start), time, new TwoOptLikeGenerator(), 42, writer, i);
            writer.close();
        }
        PrintWriter writer = new PrintWriter(DESTINATION_PATH + "patience\\bier"+"max"+".csv", StandardCharsets.UTF_8);
        bier.tabuSearchLog(new ArrayList<>(start), time, new TwoOptLikeGenerator(), 42, writer, Integer.MAX_VALUE);
        writer.close();

        start = berlin.kRandom(100);
        for (int i = 10; i < 1000; i*=1.5) {
            System.out.println(i);
            writer = new PrintWriter(DESTINATION_PATH + "patience\\berlin"+i+".csv", StandardCharsets.UTF_8);
            berlin.tabuSearchLog(new ArrayList<>(start), time, new TwoOptLikeGenerator(), 42, writer, i);
            writer.close();
        }
        writer = new PrintWriter(DESTINATION_PATH + "patience\\berlin"+"max"+".csv", StandardCharsets.UTF_8);
        berlin.tabuSearchLog(new ArrayList<>(start), time, new TwoOptLikeGenerator(), 42, writer, Integer.MAX_VALUE);
        writer.close();

        start = ch130.kRandom(100);
        for (int i = 10; i < 1000; i*=1.5) {
            System.out.println(i);
            writer = new PrintWriter(DESTINATION_PATH + "patience\\ch130"+i+".csv", StandardCharsets.UTF_8);
            ch130.tabuSearchLog(new ArrayList<>(start), time, new TwoOptLikeGenerator(), 42, writer, i);
            writer.close();
        }
        writer = new PrintWriter(DESTINATION_PATH + "patience\\ch130"+"max"+".csv", StandardCharsets.UTF_8);
        ch130.tabuSearchLog(new ArrayList<>(start), time, new TwoOptLikeGenerator(), 42, writer, Integer.MAX_VALUE);
        writer.close();

        start = a280.kRandom(100);
        for (int i = 10; i < 1000; i*=1.5) {
            System.out.println(i);
            writer = new PrintWriter(DESTINATION_PATH + "patience\\a280"+i+".csv", StandardCharsets.UTF_8);
            a280.tabuSearchLog(new ArrayList<>(start), time, new TwoOptLikeGenerator(), 42, writer, i);
            writer.close();
        }
        writer = new PrintWriter(DESTINATION_PATH + "patience\\a280"+"max"+".csv", StandardCharsets.UTF_8);
        a280.tabuSearchLog(new ArrayList<>(start), time, new TwoOptLikeGenerator(), 42, writer, Integer.MAX_VALUE);
        writer.close();

    }

    @Test
    public void test2() throws Exception {
        DistanceMatrix dm = new DistanceMatrix(TSPLoader.returnScanner(new File("data/berlin52.tsp")));

        AreaGenerator ag = new TwoOptLikeGenerator();
        AreaGeneratorS ags = new ReverseGeneratorS();

        List<Integer> start = dm.kRandom(100);
        List<Integer> end = dm.kRandom(100);

        boolean[][] banned = new boolean[dm.matrix.length][dm.matrix.length];
        int[][] tabuList = new int[5][2];
        for (int i = 0; i < dm.matrix.length; i++) {
            for (int j = 0; j < dm.matrix.length; j++) {
                banned[i][j] = false;
            }
        }

        for (int i = 0; i < tabuList.length; i++) {
            tabuList[i][0] = 0;
            tabuList[i][1] = 0;
        }
        int l = 0;
        int c = 10000;
        end = new ArrayList<>(start);
        long time = System.currentTimeMillis();
        for (int i = 0; i < c; i++) {
            end = ag.generateArea(end, dm.matrix, banned, tabuList, l);
            l = (l+1)%tabuList.length;
            dm.cost(end);
        }
        System.out.println(System.currentTimeMillis() - time);
        for (int i = 0; i < dm.matrix.length; i++) {
            for (int j = 0; j < dm.matrix.length; j++) {
                banned[i][j] = false;
            }
        }

        for (int i = 0; i < tabuList.length; i++) {
            tabuList[i][0] = 0;
            tabuList[i][1] = 0;
        }
        l = 0;
        end = new ArrayList<>(start);
        time = System.currentTimeMillis();
        for (int i = 0; i < c; i++) {
            end = ags.generateArea(end, dm.matrix, banned, tabuList, l, dm.cost(end));
            l = (l+1)%tabuList.length;
        }
        System.out.println(System.currentTimeMillis() - time);
    }

    @Test
    public void aspirationTest() throws Exception {
        DistanceMatrix berlin = new DistanceMatrix(TSPLoader.returnScanner(new File("data/berlin52.tsp")));
        DistanceMatrix bier = new DistanceMatrix(TSPLoader.returnScanner(new File("data/bier127.tsp")));
        DistanceMatrix a280 = new DistanceMatrix(TSPLoader.returnScanner(new File("data/a280.tsp")));
        DistanceMatrix ch130 = new DistanceMatrix(TSPLoader.returnScanner(new File("data/ch130.tsp")));
        DistanceMatrix ring = new DistanceMatrix(TSPLoader.returnScanner(new File("data/ring.tsp")));

        int time = 500;
        List<Integer> start = ch130.kRandom(100);
        PrintWriter writer = new PrintWriter(DESTINATION_PATH + "aspiration_berlin.csv", StandardCharsets.UTF_8);
        ch130.tabuSearchLog(new ArrayList<>(start), time, new ReverseGeneratorS(), 100, writer, Integer.MAX_VALUE);
        writer.close();
        writer = new PrintWriter(DESTINATION_PATH + "non_aspiration_berlin.csv", StandardCharsets.UTF_8);
        ch130.tabuSearchLog(new ArrayList<>(start), time, new TwoOptLikeGenerator(), 100, writer, Integer.MAX_VALUE);
        writer.close();
    }
}
