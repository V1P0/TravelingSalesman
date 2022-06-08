import Genetic.*;
import Genetic.Crossovers.CXCrossover;
import Genetic.Crossovers.MOCCrossover;
import Genetic.Crossovers.OXCrossover;
import Genetic.Crossovers.PMXCrossover;
import Genetic.Crossovers.RandomCrossover;
import Genetic.Killers.*;
import Genetic.Mutators.*;
import helpers.DistanceMatrix;
import helpers.TSPLoader;
import org.junit.Test;

import java.io.File;
import java.util.List;

public class ZawojTests {

    @Test
    public void naturalKillerTest() throws Exception {
        DistanceMatrix berlin = new DistanceMatrix(TSPLoader.returnScanner(new File("data/att532.tsp")));
        DistanceMatrix berlin1 = (DistanceMatrix) berlin;
        DistanceMatrix berlin2 = (DistanceMatrix) berlin;
        DistanceMatrix berlin3 = (DistanceMatrix) berlin;
        DistanceMatrix berlin4 = (DistanceMatrix) berlin;
        // Radnom corrsvoer
        System.out.println("48");
        Population x = Population.getRandomPopulation(48, berlin.matrix);
        System.out.println(berlin.cost(x.getBestSpecimen().getResult()));
        System.out.print("[");
        for (int i = 0; i < 10; i++) {
            List<Integer> result = berlin.genetic(
                    x,
                    new BagMutator(
                            new BestReverseMutator(),
                            new RandomSwapMutator()),
                    new CXCrossover(),
                    new RouletteKiller(),
                    0.3,
                    1000);
            // System.out.println(x.getSpecimens().size());
            System.out.print(berlin.cost(result));
            if (i != 9) {
                System.out.print(", ");
            }
        }
        System.out.print("]");

        System.out.println("\n96");
        x = Population.getRandomPopulation(96, berlin1.matrix);
        System.out.println(berlin1.cost(x.getBestSpecimen().getResult()));
        System.out.print("[");
        for (int i = 0; i < 10; i++) {
            List<Integer> result = berlin1.genetic(
                    x,
                    new BagMutator(
                            new BestReverseMutator(),
                            new RandomSwapMutator()),
                    new CXCrossover(),
                    new RouletteKiller(),
                    0.3,
                    1000);
            // System.out.println(x.getSpecimens().size());
            System.out.print(berlin1.cost(result) + ',');
            if (i != 9) {
                System.out.print(", ");
            }
        }
        System.out.print("]");
        System.out.println("\n144");
        x = Population.getRandomPopulation(144, berlin2.matrix);
        System.out.println(berlin2.cost(x.getBestSpecimen().getResult()));
        System.out.print("[");
        for (int i = 0; i < 10; i++) {
            List<Integer> result = berlin2.genetic(
                    x,
                    new BagMutator(
                            new BestReverseMutator(),
                            new RandomSwapMutator()),
                    new CXCrossover(),
                    new RouletteKiller(),
                    0.3,
                    1000);
            // System.out.println(x.getSpecimens().size());
            System.out.print(berlin2.cost(result) + ',');
            if (i != 9) {
                System.out.print(", ");
            }
        }
        System.out.print("]");
        System.out.println("\n192");
        x = Population.getRandomPopulation(192, berlin3.matrix);
        System.out.println(berlin3.cost(x.getBestSpecimen().getResult()));
        System.out.print("[");
        for (int i = 0; i < 10; i++) {
            List<Integer> result = berlin3.genetic(
                    x,
                    new BagMutator(
                            new BestReverseMutator(),
                            new RandomSwapMutator()),
                    new CXCrossover(),
                    new RouletteKiller(),
                    0.3,
                    1000);
            // System.out.println(x.getSpecimens().size());
            System.out.print(berlin3.cost(result) + ',');
            if (i != 9) {
                System.out.print(", ");
            }
        }
        System.out.print("]");
        System.out.println("\n240");
        System.out.println(berlin4.cost(x.getBestSpecimen().getResult()));
        x = Population.getRandomPopulation(240, berlin4.matrix);
        System.out.print("[");
        for (int i = 0; i < 10; i++) {
            List<Integer> result = berlin4.genetic(
                    x,
                    new BagMutator(
                            new BestReverseMutator(),
                            new RandomSwapMutator()),
                    new CXCrossover(),
                    new RouletteKiller(),
                    0.3,
                    1000);
            // System.out.println(x.getSpecimens().size());
            System.out.print(berlin4.cost(result) + ',');
            if (i != 9) {
                System.out.print(", ");
            }
        }
        System.out.print("]");
    }

}
