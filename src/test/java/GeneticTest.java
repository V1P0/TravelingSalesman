import Genetic.*;
import Genetic.Crossovers.OXCrossover;
import Genetic.Killers.PureCostKiller;
import Genetic.Mutators.*;
import TabuStuff.TwoOptLikeGenerator;
import helpers.DistanceMatrix;
import helpers.TSPLoader;
import org.junit.Test;

import java.io.File;
import java.util.List;

public class GeneticTest {
    @Test
    public void basics() throws Exception {
        DistanceMatrix berlin = new DistanceMatrix(TSPLoader.returnScanner(new File("data/bier127.tsp")));
        List<Integer> result = berlin.genetic(
                Population.getRandomPopulation(30, berlin.matrix),
                new BagMutator(
                        new BestReverseMutator(),
                        new RandomSwapMutator(),
                        new TwoOptMutator()),
                new OXCrossover(),
                new PureCostKiller(),
                0.4,
                500);
        System.out.println(berlin.cost(result));
        System.out.println(berlin.cost(berlin.tabuSearch(berlin.kRandom(500), 300, new TwoOptLikeGenerator(), 200)));
        System.out.println(berlin.cost(berlin.twoOptAcc(berlin.kRandom(100))));
    }
}
