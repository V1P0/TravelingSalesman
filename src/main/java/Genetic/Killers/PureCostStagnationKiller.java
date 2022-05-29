package Genetic.Killers;

import Genetic.Population;
import Genetic.Specimen;

public class PureCostStagnationKiller implements Killer{
    @Override
    public void kill(Population population) {
        int age = 0;
        for(Specimen s: population.getSpecimens()){
            age+=s.getAge();
        }
        age /= population.getExpectedSize();
        if(age > 100){
            Population newPop = Population.getRandomPopulation(population.getExpectedSize(), population.costMatrix);
            population.getSpecimens().clear();
            population.getSpecimens().addAll(newPop.getSpecimens());
        }
        population.getSpecimens().sort((s1, s2) -> (int) (s1.getCost() - s2.getCost()));
        //remove bottom half
        int size = population.getSpecimens().size();
        population.getSpecimens().subList(population.getExpectedSize() / 2, size).clear();
    }
}
