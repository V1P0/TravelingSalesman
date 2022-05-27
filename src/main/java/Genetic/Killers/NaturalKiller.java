package Genetic.Killers;

import Genetic.Population;
import Genetic.Specimen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NaturalKiller implements Killer{
    @Override
    public void kill(Population population) {
        Random rand = new Random();
        long bc = population.getBestSpecimen().getCost();
        int size = population.getSpecimens().size();
        List<Specimen> toDelete = new ArrayList<>();
        for(Specimen specimen: population.getSpecimens()){
            double prob = deathFunction(bc, specimen.getCost(), specimen.getAge(), size, population.getExpectedSize());
            if(prob > rand.nextDouble()){
                toDelete.add(specimen);
            }
        }
        population.getSpecimens().removeAll(toDelete);
    }

    private double deathFunction(long bc, long c, long age, int size, int expectedSize){
        double bias = 0.5;
        return Math.tanh((costFunction(bc, c)*ageFunction(age) + bias)*(size/(double)expectedSize));
    }

    private double costFunction(long bc, long c){
        return (c-bc)/(double)bc;
    }

    private double ageFunction(long age){
        double a = 0.000444043;
        double b = -0.0271067;
        double c = 0.326663;
        return a*age*age+b*age+c;
    }

}
