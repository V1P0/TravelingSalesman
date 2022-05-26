package Genetic.Killers;

import Genetic.Population;

public class PureCostKiller implements Killer {
    @Override
    public void kill(Population population) {
        population.getSpecimens().sort((s1, s2) -> (int) (s1.getCost() - s2.getCost()));
        //remove bottom half
        int size = population.getSpecimens().size();
        population.getSpecimens().subList(population.getExpectedSize() / 2, size).clear();
    }
}
