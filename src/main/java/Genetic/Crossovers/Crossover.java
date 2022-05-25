package Genetic.Crossovers;

import Genetic.Specimen;

public interface Crossover {
    Specimen[] crossover(Specimen parent1, Specimen parent2, int[][] costMatrix);
}
