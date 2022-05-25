package Genetic.Mutators;

import Genetic.Specimen;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RandomSwapMutator implements Mutator {
    private final Random r;

    public RandomSwapMutator() {
        r = new Random();
    }

    @Override
    public void mutate(Specimen specimen, int[][] costMatrix) {
        List<Integer> result = specimen.getResult();
        int i = r.nextInt(result.size());
        int j = r.nextInt(result.size());
        Collections.swap(result, i, j);
        specimen.setCost(Specimen.cost(result, costMatrix));
    }
}

