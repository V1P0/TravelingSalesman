package Genetic.Crossovers;

import Genetic.Specimen;

import java.util.ArrayList;

public class NotCrossover implements Crossover{

    @Override
    public Specimen[] crossover(Specimen parent1, Specimen parent2, int[][] costMatrix) {
        Specimen child1 = new Specimen();
        child1.setResult(new ArrayList<>(parent1.getResult()));
        child1.setCost(Specimen.cost(child1.getResult(), costMatrix));
        Specimen child2 = new Specimen();
        child2.setResult(new ArrayList<>(parent2.getResult()));
        child2.setCost(Specimen.cost(child2.getResult(), costMatrix));
        return new Specimen[]{child1, child2};
    }
}
