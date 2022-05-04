import TabuStuff.*;
import helpers.DistanceMatrix;
import helpers.Euclidean;
import helpers.TSPLoader;
import org.junit.Test;

import java.io.File;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
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
        for(int i = 0; i < 100; i++) {
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
        for(int i = 0; i < 100; i++) {
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
        for(int i = 0; i < 100; i++) {
            end = threaded6.generateArea(start, dm.matrix, banned, tabuList, 0);
        }
        System.out.println("Threaded6: " + (System.currentTimeMillis() - time) + "ms");
        System.out.println(end);
    }

    @Test
    public void threadTest() throws Exception {
        Euclidean eu = TSPLoader.returnScanner(new File("data/a280.tsp"));
        DistanceMatrix dm = new DistanceMatrix(1000, DistanceMatrix.types.SYMMETRIC);
        PrintWriter writer = new PrintWriter(DESTINATION_PATH + "multithreaded_two_opt_gen.csv", StandardCharsets.UTF_8);

        for(int n = 1; n<=10; n++) {
            dm = new DistanceMatrix(n*100, DistanceMatrix.types.SYMMETRIC);
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
                writer.println((n*100)+","+i + "," + (System.currentTimeMillis() - time));


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

        PrintWriter writer = new PrintWriter(DESTINATION_PATH + "berlin_otoczenia.csv", StandardCharsets.UTF_8);
        long cost1 = 0, cost2 = 0, cost3 = 0, cost4 = 0, cost5 = 0;
        for(int t = 10; t<1000; t+=200) {
            for (int i = 0; i < 10; i++) {
                cost1 += berlin.cost(berlin.tabuSearch(berlin.kRandom(100), t, new TwoOptLikeGenerator(), 42));
                cost2 += berlin.cost(berlin.tabuSearch(berlin.kRandom(100), t, new SwapGenerator(), 42));
                cost3 += berlin.cost(berlin.tabuSearch(berlin.kRandom(100), t, new InsertGenerator(), 42));
            }
            System.out.println("Done with t = " + t);
            writer.println(t + "," + cost1/10 + "," + cost2/10 + "," + cost3/10);
            cost1 = 0;
            cost2 = 0;
            cost3 = 0;
        }
        writer.close();
    }
}
