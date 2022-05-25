package Genetic.Mutators;

import Genetic.Specimen;

public interface Mutator {
    void mutate(Specimen specimen, int[][] costMatrix);
}
