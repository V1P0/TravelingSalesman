package Genetic.Mutators;

import Genetic.Specimen;

public class BagMutator implements Mutator {
    Mutator[] mutators;

    public BagMutator(Mutator... mutators) {
        this.mutators = mutators;
    }

    @Override
    public void mutate(Specimen specimen, int[][] costMatrix) {
        //use random number generator to select a mutator
        Mutator mutator = mutators[(int) (Math.random() * mutators.length)];
        mutator.mutate(specimen, costMatrix);
    }
}

