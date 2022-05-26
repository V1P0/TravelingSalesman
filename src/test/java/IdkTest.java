import java.io.File;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import TabuStuff.InsertGenerator;
import TabuStuff.SwapGenerator;
import TabuStuff.TwoOptLikeGenerator;
import TabuStuff.twoOptThreadedGenerator;
import helpers.DistanceMatrix;
import helpers.Euclidean;
import helpers.TSPLoader;

public class IdkTest {
    String destination = "P:\\TravelingSalesman\\data";

    @Test
    public void WilcoxTestData0() throws Exception {
        Euclidean eu = TSPLoader.returnScanner(new File("data/berlin52.tsp"));
        DistanceMatrix dm = new DistanceMatrix(eu);
        PrintWriter writer = new PrintWriter("E:\\TravelingSalesman\\Start.csv", StandardCharsets.UTF_8);
        for (int i = 0; i < 20; i++) {
            List<Integer> list = dm.kRandom(5);
            writer.print(dm.cost(list) + ",");
        }
        writer.close();
    }

    @Test
    public void WilcoxTestData1() throws Exception {
        Euclidean eu = TSPLoader.returnScanner(new File("data/berlin52.tsp"));
        DistanceMatrix dm = new DistanceMatrix(eu);
        PrintWriter writer = new PrintWriter("E:\\TravelingSalesman\\IGBerlin.csv", StandardCharsets.UTF_8);
        for (int i = 0; i < 20; i++) {
            List<Integer> list = dm.kRandom(5);
            List<Integer> result = dm.tabuSearch(list, 1000, new InsertGenerator(), 40);
            writer.print(dm.cost(result) + ",");
        }
        writer.close();
    }

    @Test
    public void WilcoxTestData2() throws Exception {
        Euclidean eu = TSPLoader.returnScanner(new File("data/berlin52.tsp"));
        DistanceMatrix dm = new DistanceMatrix(eu);
        PrintWriter writer = new PrintWriter("E:\\TravelingSalesman\\SGBerlin.csv", StandardCharsets.UTF_8);
        for (int i = 0; i < 20; i++) {
            List<Integer> list = dm.kRandom(5);
            List<Integer> result = dm.tabuSearch(list, 1000, new SwapGenerator(), 40);
            writer.print(dm.cost(result) + ",");
        }
        writer.close();
    }

    @Test
    public void WilcoxTestData3() throws Exception {
        Euclidean eu = TSPLoader.returnScanner(new File("data/berlin52.tsp"));
        DistanceMatrix dm = new DistanceMatrix(eu);
        PrintWriter writer = new PrintWriter("E:\\TravelingSalesman\\RGBerlin.csv", StandardCharsets.UTF_8);
        for (int i = 0; i < 20; i++) {
            List<Integer> list = dm.kRandom(5);
            List<Integer> result = dm.tabuSearch(list, 1000, new TwoOptLikeGenerator(), 40);
            writer.print(dm.cost(result) + ",");
        }
        writer.close();
    }

    @Test
    public void WilcoxTestData4() throws Exception {
        Euclidean eu = TSPLoader.returnScanner(new File("data/berlin52.tsp"));
        DistanceMatrix dm = new DistanceMatrix(eu);
        PrintWriter writer = new PrintWriter("E:\\TravelingSalesman\\RTGBerlin.csv", StandardCharsets.UTF_8);
        for (int i = 0; i < 20; i++) {
            List<Integer> list = dm.kRandom(5);
            List<Integer> result = dm.tabuSearch(list, 1000, new twoOptThreadedGenerator(2), 40);
            writer.print(dm.cost(result) + ",");
        }
        writer.close();
    }

    // a280.tsp

    @Test
    public void WilcoxTestData5() throws Exception {
        Euclidean eu = TSPLoader.returnScanner(new File("data/a280.tsp"));
        DistanceMatrix dm = new DistanceMatrix(eu);
        PrintWriter writer = new PrintWriter("E:\\TravelingSalesman\\IGa280.csv", StandardCharsets.UTF_8);
        for (int i = 0; i < 20; i++) {
            List<Integer> list = dm.kRandom(5);
            List<Integer> result = dm.tabuSearch(list, 1000, new InsertGenerator(), 40);
            writer.print(dm.cost(result) + ",");
        }
        writer.close();
    }

    @Test
    public void WilcoxTestData6() throws Exception {
        Euclidean eu = TSPLoader.returnScanner(new File("data/a280.tsp"));
        DistanceMatrix dm = new DistanceMatrix(eu);
        PrintWriter writer = new PrintWriter("E:\\TravelingSalesman\\SGa280.csv", StandardCharsets.UTF_8);
        for (int i = 0; i < 20; i++) {
            List<Integer> list = dm.kRandom(5);
            List<Integer> result = dm.tabuSearch(list, 1000, new SwapGenerator(), 40);
            writer.print(dm.cost(result) + ",");
        }
        writer.close();
    }

    @Test
    public void WilcoxTestData7() throws Exception {
        Euclidean eu = TSPLoader.returnScanner(new File("data/a280.tsp"));
        DistanceMatrix dm = new DistanceMatrix(eu);
        PrintWriter writer = new PrintWriter("E:\\TravelingSalesman\\RGa280.csv", StandardCharsets.UTF_8);
        for (int i = 0; i < 20; i++) {
            List<Integer> list = dm.kRandom(5);
            List<Integer> result = dm.tabuSearch(list, 1000, new TwoOptLikeGenerator(), 40);
            writer.print(dm.cost(result) + ",");
        }
        writer.close();
    }

    @Test
    public void WilcoxTestData8() throws Exception {
        Euclidean eu = TSPLoader.returnScanner(new File("data/a280.tsp"));
        DistanceMatrix dm = new DistanceMatrix(eu);
        PrintWriter writer = new PrintWriter("E:\\TravelingSalesman\\RTGa280.csv", StandardCharsets.UTF_8);
        for (int i = 0; i < 20; i++) {
            List<Integer> list = dm.kRandom(5);
            List<Integer> result = dm.tabuSearch(list, 1000, new twoOptThreadedGenerator(2), 40);
            writer.print(dm.cost(result) + ",");
        }
        writer.close();
    }
    // ch130.tsp

    @Test
    public void WilcoxTestData9() throws Exception {
        Euclidean eu = TSPLoader.returnScanner(new File("data/ch130.tsp"));
        DistanceMatrix dm = new DistanceMatrix(eu);
        PrintWriter writer = new PrintWriter("E:\\TravelingSalesman\\IGch130.csv", StandardCharsets.UTF_8);
        for (int i = 0; i < 20; i++) {
            List<Integer> list = dm.kRandom(5);
            List<Integer> result = dm.tabuSearch(list, 1000, new InsertGenerator(), 40);
            writer.print(dm.cost(result) + ",");
        }
        writer.close();
    }

    @Test
    public void WilcoxTestData10() throws Exception {
        Euclidean eu = TSPLoader.returnScanner(new File("data/ch130.tsp"));
        DistanceMatrix dm = new DistanceMatrix(eu);
        PrintWriter writer = new PrintWriter("E:\\TravelingSalesman\\SGch130.csv", StandardCharsets.UTF_8);
        for (int i = 0; i < 20; i++) {
            List<Integer> list = dm.kRandom(5);
            List<Integer> result = dm.tabuSearch(list, 1000, new SwapGenerator(), 40);
            writer.print(dm.cost(result) + ",");
        }
        writer.close();
    }

    @Test
    public void WilcoxTestData11() throws Exception {
        Euclidean eu = TSPLoader.returnScanner(new File("data/ch130.tsp"));
        DistanceMatrix dm = new DistanceMatrix(eu);
        PrintWriter writer = new PrintWriter("E:\\TravelingSalesman\\RGch130.csv", StandardCharsets.UTF_8);
        for (int i = 0; i < 20; i++) {
            List<Integer> list = dm.kRandom(5);
            List<Integer> result = dm.tabuSearch(list, 1000, new TwoOptLikeGenerator(), 40);
            writer.print(dm.cost(result) + ",");
        }
        writer.close();
    }

    @Test
    public void WilcoxTestData12() throws Exception {
        Euclidean eu = TSPLoader.returnScanner(new File("data/ch130.tsp"));
        DistanceMatrix dm = new DistanceMatrix(eu);
        PrintWriter writer = new PrintWriter("E:\\TravelingSalesman\\RTGch130.csv", StandardCharsets.UTF_8);
        for (int i = 0; i < 20; i++) {
            List<Integer> list = dm.kRandom(5);
            List<Integer> result = dm.tabuSearch(list, 1000, new twoOptThreadedGenerator(2), 40);
            writer.print(dm.cost(result) + ",");
        }
        writer.close();
    }

    @Test
    public void tabuSizeTest() throws Exception {
        DistanceMatrix a280 = new DistanceMatrix(TSPLoader.returnScanner(new File("data/a280.tsp")));
        DistanceMatrix ch130 = new DistanceMatrix(TSPLoader.returnScanner(new File("data/ch130.tsp")));
        DistanceMatrix berlin = new DistanceMatrix(TSPLoader.returnScanner(new File("data/berlin52.tsp")));

        int time = 100;

        for (int i = 1; i < 1000; i *= 3) {
            PrintWriter writer = new PrintWriter("E:\\TravelingSalesman\\TabuTestSwap\\tabuSizeSwap" + i + ".csv",
                    StandardCharsets.UTF_8);
            List<Integer> list = berlin.kRandom(1);
            berlin.tabuSearchLog(new ArrayList<>(list), time, new InsertGenerator(), i, writer, Integer.MAX_VALUE);
            writer.close();
        }

        for (int i = 1; i < 1000; i *= 3) {
            PrintWriter writer = new PrintWriter("E:\\TravelingSalesman\\TabuTestInsert\\tabuSizeInsert" + i + ".csv",
                    StandardCharsets.UTF_8);
            List<Integer> list = berlin.kRandom(1);
            berlin.tabuSearchLog(new ArrayList<>(list), time, new InsertGenerator(), i, writer, Integer.MAX_VALUE);
            writer.close();
        }

        for (int i = 1; i < 1000; i *= 3) {
            PrintWriter writer = new PrintWriter("E:\\TravelingSalesman\\TabuTestRevers\\tabuSizeReverse" + i + ".csv",
                    StandardCharsets.UTF_8);
            List<Integer> list = berlin.kRandom(1);
            berlin.tabuSearchLog(new ArrayList<>(list), time, new InsertGenerator(), i, writer, Integer.MAX_VALUE);
            writer.close();
        }
    }
}
