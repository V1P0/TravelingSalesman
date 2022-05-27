import Genetic.*;
import Genetic.Crossovers.Crossover;
import Genetic.Crossovers.OXCrossover;
import Genetic.Killers.Killer;
import Genetic.Killers.NaturalKiller;
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
        System.out.println(berlin.cost(berlin.tabuSearch(berlin.kRandom(500), 300, new TwoOptLikeGenerator(), 200)));
        System.out.println(berlin.cost(berlin.twoOptAcc(berlin.kRandom(100))));
    }

    @Test
    public void hawaiiTest() throws Exception{
        DistanceMatrix berlin = new DistanceMatrix(TSPLoader.returnScanner(new File("data/berlin52.tsp")));
        List<Integer> result = berlin.island_genetic(
                new Population[]{
                        Population.getRandomPopulation(30, berlin.matrix),
                        Population.getTwoOptedPopulation(30, berlin, 0.2),
                        Population.getTwoOptedPopulation(30, berlin, 0.6),
                        Population.getRandomPopulation(30, berlin.matrix),
                        Population.getTwoOptedPopulation(30, berlin, 0.1)
                },
                new Mutator[]{
                        new RandomSwapMutator(),
                        new BestReverseMutator(),
                        new BagMutator(
                                new BestReverseMutator(),
                                new RandomSwapMutator(),
                                new TwoOptMutator()
                        ),
                        new BagMutator(
                                new BestReverseMutator(),
                                new RandomSwapMutator()
                        ),
                        new BagMutator(
                                new TwoOptMutator(),
                                new RandomSwapMutator()
                        )
                },
                new Crossover[]{
                        new OXCrossover(),
                        new OXCrossover(),
                        new OXCrossover(),
                        new OXCrossover(),
                        new OXCrossover()
                },
                new Killer[]{
                        new PureCostKiller(),
                        new PureCostKiller(),
                        new PureCostKiller(),
                        new PureCostKiller(),
                        new PureCostKiller()
                },
                new double[]{
                        0.3,
                        0.2,
                        0.4,
                        0.35,
                        0.25
                },
                500L,
                20
        );
        System.out.println(result);
        System.out.println(berlin.cost(result));
    }

    @Test
    public void naturalKillerTest() throws Exception {
        DistanceMatrix berlin = new DistanceMatrix(TSPLoader.returnScanner(new File("data/berlin52.tsp")));
        Population x = Population.getRandomPopulation(100, berlin.matrix);
        x.setKiller(new NaturalKiller());
        x.setCrossover(new OXCrossover());
        for(int i = 0; i< 100; i++){
            x.killWorst();
            x.crossover();
            x.updateAges();
            System.out.println(x.getSpecimens().size());
        }

        System.out.println(x);
        System.out.println(x.getSpecimens().size());
    }

    @Test
    public void koxTest() throws Exception {
        DistanceMatrix berlin = new DistanceMatrix(TSPLoader.returnScanner(new File("data/berlin52.tsp")));
        Population x = Population.getRandomPopulation(30, berlin.matrix);
        List<Integer> result = berlin.genetic(
                x,
                new BagMutator(
                        new BestReverseMutator(),
                        new RandomSwapMutator(),
                        new TwoOptMutator()),
                new OXCrossover(),
                new NaturalKiller(),
                0.4,
                500);
        System.out.println(x);
        System.out.println(berlin.cost(result));
    }
}

