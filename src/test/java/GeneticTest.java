import Genetic.*;
import Genetic.Crossovers.OXCrossover;
import Genetic.Killers.PureCostKiller;
import Genetic.Mutators.BagMutator;
import Genetic.Mutators.BestReverseMutator;
import Genetic.Mutators.RandomSwapMutator;
import Genetic.Mutators.SequentialMutator;
import helpers.DistanceMatrix;
import helpers.TSPLoader;
import org.junit.Test;

import java.io.File;
import java.util.List;

public class GeneticTest {
    @Test
    public void basics() throws Exception {
        DistanceMatrix berlin = new DistanceMatrix(TSPLoader.returnScanner(new File("data/berlin52.tsp")));
        List<Integer> result = berlin.genetic(Population.getRandomPopulation(100, berlin.matrix), new BagMutator(new BestReverseMutator(), new RandomSwapMutator()), new OXCrossover(), new PureCostKiller(), 0.3, 1000);
        System.out.println(berlin.cost(result));
    }
}
