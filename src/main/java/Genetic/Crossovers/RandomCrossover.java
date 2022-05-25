package Genetic.Crossovers;

import Genetic.Specimen;

public class RandomCrossover implements Crossover{

    @Override
    public Specimen[] crossover(Specimen parent1, Specimen parent2, int[][] costMatrix) {
        return new Specimen[]{Specimen.getRandomSpecimen(costMatrix)};
    }
}
