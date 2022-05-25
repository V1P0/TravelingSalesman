package Genetic.Mutators;

import Genetic.Specimen;

public class SequentialMutator implements Mutator {
    Mutator[] mutators;

    public SequentialMutator(Mutator... mutators) {
        this.mutators = mutators;
    }

    @Override
    public void mutate(Specimen specimen, int[][] costMatrix) {
        for (Mutator mutator : mutators) {
            mutator.mutate(specimen, costMatrix);
        }
    }
}

