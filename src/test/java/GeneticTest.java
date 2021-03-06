import Genetic.*;
import Genetic.Crossovers.CXCrossover;
import Genetic.Crossovers.Crossover;
import Genetic.Crossovers.MOCCrossover;
// import Genetic.Crossovers.OX1Crossover;
import Genetic.Crossovers.OXCrossover;
import Genetic.Crossovers.PMXCrossover;
import Genetic.Killers.*;
import Genetic.Mutators.*;
import TabuStuff.TwoOptLikeGenerator;
import helpers.DistanceMatrix;
import helpers.TSPLoader;
import org.junit.Test;

import java.io.File;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class GeneticTest {
        @Test
        public void basics() throws Exception {
                DistanceMatrix berlin = new DistanceMatrix(TSPLoader.returnScanner(new File("data/berlin52.tsp")));
                List<Integer> result = berlin.genetic(
                                Population.getRandomPopulation(10, berlin.matrix),
                                new BagMutator(
                                                new BestReverseMutator(),
                                                new RandomSwapMutator(),
                                                new TwoOptMutator()),
                                new OXCrossover(),
                                new PureCostKiller(),
                                0.4,
                                5000);
                System.out.println(berlin.cost(result));
                System.out.println(berlin
                                .cost(berlin.tabuSearch(berlin.kRandom(500), 300, new TwoOptLikeGenerator(), 200)));
                System.out.println(berlin.cost(berlin.twoOptAcc(berlin.kRandom(100))));
        }

        @Test
        public void hawaiiTest() throws Exception {
                DistanceMatrix berlin = new DistanceMatrix(TSPLoader.returnScanner(new File("data/a280.tsp")));
                List<Integer> result = berlin.island_genetic(
                                new Population[] {
                                        Population.getRandomPopulation(48, berlin.matrix),
                                        new Population(Population.getNearestNeighbourPopulation(4, berlin), Population.getTwoOptedPopulation(4, berlin), Population.getRandomPopulation(40, berlin.matrix)),
                                        new Population(Population.getNearestNeighbourPopulation(8, berlin), Population.getTwoOptedPopulation(2, berlin), Population.getRandomPopulation(38, berlin.matrix)),
                                        Population.getRandomPopulation(48, berlin.matrix),
                                        new Population(Population.getNearestNeighbourPopulation(2, berlin), Population.getTwoOptedPopulation(8, berlin), Population.getRandomPopulation(38, berlin.matrix))
                                },
                                new Mutator[] {
                                                new RandomSwapMutator(),
                                                new BestReverseMutator(),
                                                new BagMutator(
                                                                new BestReverseMutator(),
                                                                new RandomSwapMutator(),
                                                                new SequentialMutator(
                                                                                new RandomSwapMutator(),
                                                                                new TwoOptMutator())),
                                                new BagMutator(
                                                                new BestReverseMutator(),
                                                                new RandomSwapMutator()),
                                                new BagMutator(
                                                                new SequentialMutator(
                                                                                new RandomSwapMutator(),
                                                                                new TwoOptMutator()),
                                                                new RandomSwapMutator())
                                },
                                new Crossover[] {
                                                new OXCrossover(),
                                                new OXCrossover(),
                                                new OXCrossover(),
                                                new OXCrossover(),
                                                new OXCrossover()
                                },
                                new Killer[] {
                                                new PureCostKiller(),
                                                new PureCostStagnationKiller(),
                                                new TournamentKiller(),
                                                new NaturalKiller(),
                                                new PureCostStagnationKiller()
                                },
                                new double[] {
                                                0.3,
                                                0.2,
                                                0.4,
                                                0.35,
                                                0.25
                                },
                                500L,
                                20);
                System.out.println(result);
                System.out.println(berlin.cost(result));
        }

        @Test
        public void naturalKillerTest() throws Exception {
                DistanceMatrix berlin = new DistanceMatrix(TSPLoader.returnScanner(new File("data/berlin52.tsp")));
                Population x = Population.getRandomPopulation(48, berlin.matrix);
                System.out.println(berlin.cost(x.getBestSpecimen().getResult()));
                List<Integer> result = berlin.genetic(
                                x,
                                new BagMutator(
                                                new BestReverseMutator(),
                                                new RandomSwapMutator()),
                                new CXCrossover(),
                                new RouletteKiller(),
                                0.3,
                                3000);
                System.out.println(x.getSpecimens().size());
                System.out.println(berlin.cost(result));
        }

        @Test
        public void koxTest() throws Exception {
                DistanceMatrix berlin = new DistanceMatrix(TSPLoader.returnScanner(new File("data/berlin52.tsp")));
                Population x = Population.getRandomPopulation(100, berlin.matrix);
                int[] seen = new int[berlin.matrix.length];
                Arrays.fill(seen, 0);
                System.out.println(x);
                /*
                 * List<Integer> result = berlin.genetic(
                 * x,
                 * new BagMutator(
                 * new BestReverseMutator(),
                 * new RandomSwapMutator(),
                 * new TwoOptMutator()),
                 * new CXCrossover(),
                 * new RouletteKiller(),
                 * 0.4,
                 * 100);
                 * System.out.println(x.getSpecimens().size());
                 * System.out.println(berlin.cost(result));
                 */
                x.setCrossover(new MOCCrossover());
                x.crossover();
                for (Specimen s : x.getSpecimens()) {
                        for (int i : s.getResult()) {
                                seen[i]++;
                        }
                        for (int i = 0; i < seen.length; i++) {
                                if (seen[i] > 1) {
                                        System.out.println("more than one in " + i);
                                }
                                if (seen[i] == 0) {
                                        System.out.println("no one in " + i);
                                }
                        }
                        Arrays.fill(seen, 0);
                }
        }

        @Test
        public void naturalVsCostTest() throws Exception {
                String PATH = "E:\\PycharmProjects\\wykresiki\\";
                DistanceMatrix a = new DistanceMatrix(TSPLoader.returnScanner(new File("data/bier127.tsp")));
                Population x1 = Population.getRandomPopulation(50, a.matrix);
                Population x2 = (Population) x1.clone();
                Population x3 = (Population) x1.clone();
                Population x4 = (Population) x1.clone();
                PrintWriter writer1 = new PrintWriter(PATH + "pure.csv");
                PrintWriter writer2 = new PrintWriter(PATH + "natural.csv");
                PrintWriter writer3 = new PrintWriter(PATH + "pure_stagnation.csv");
                long time = 3000;
                a.genetic(x1,
                                new BagMutator(
                                                new BestReverseMutator(),
                                                new RandomSwapMutator()),
                                new OXCrossover(),
                                new TournamentKiller(),
                                0.3,
                                time);
                System.out.println(x1.overallBest.getCost());
                System.out.println(x1.getSpecimens().size());
                System.out.println(x1.getYear());
                System.out.println();
                a.genetic(x2,
                                new BagMutator(
                                                new BestReverseMutator(),
                                                new RandomSwapMutator()),
                                new OXCrossover(),
                                new NaturalKiller(),
                                0.3,
                                time);
                System.out.println(x2.overallBest.getCost());
                System.out.println(x2.getSpecimens().size());
                System.out.println(x2.getYear());
                System.out.println();
                a.genetic(x3,
                                new BagMutator(
                                                new BestReverseMutator(),
                                                new RandomSwapMutator()),
                                new OXCrossover(),
                                new PureCostStagnationKiller(),
                                0.3,
                                time);
                System.out.println(x3.overallBest.getCost());
                System.out.println(x3.getSpecimens().size());
                System.out.println(x3.getYear());
                System.out.println();
                a.genetic(x4,
                                new BagMutator(
                                                new BestReverseMutator(),
                                                new RandomSwapMutator()),
                                new OXCrossover(),
                                new RouletteKiller(),
                                0.3,
                                time);
                System.out.println(x4.overallBest.getCost());
                System.out.println(x4.getSpecimens().size());
                System.out.println(x4.getYear());
                System.out.println();
                writer1.close();
                writer2.close();
                writer3.close();

        }

        @Test
        public void bigTest() throws Exception {
                String PATH = "E:\\PycharmProjects\\wykresiki\\";
                DistanceMatrix a = new DistanceMatrix(TSPLoader.returnScanner(new File("data/a280.tsp")));
                Population x1 = Population.getRandomPopulation(50, a.matrix);
                for (int i = 0; i < 100; i++) {
                        System.out.print("\riteration:" + i);
                        PrintWriter writer1 = new PrintWriter(PATH + "pure" + i + ".csv");
                        PrintWriter writer2 = new PrintWriter(PATH + "natural" + i + ".csv");
                        PrintWriter writer3 = new PrintWriter(PATH + "pure_stagnation" + i + ".csv");
                        a.genetic_log((Population) x1.clone(),
                                        new BagMutator(
                                                        new BestReverseMutator(),
                                                        new RandomSwapMutator()),
                                        new OXCrossover(),
                                        new PureCostKiller(),
                                        0.3,
                                        10000,
                                        writer1);
                        a.genetic_log((Population) x1.clone(),
                                        new BagMutator(
                                                        new BestReverseMutator(),
                                                        new RandomSwapMutator()),
                                        new OXCrossover(),
                                        new NaturalKiller(),
                                        0.3,
                                        10000,
                                        writer2);
                        a.genetic_log((Population) x1.clone(),
                                        new BagMutator(
                                                        new BestReverseMutator(),
                                                        new RandomSwapMutator()),
                                        new OXCrossover(),
                                        new PureCostStagnationKiller(),
                                        0.3,
                                        10000,
                                        writer3);
                        writer1.close();
                        writer2.close();
                        writer3.close();
                }
        }
}
