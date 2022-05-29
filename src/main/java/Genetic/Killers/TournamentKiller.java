package Genetic.Killers;

import Genetic.Population;
import Genetic.Specimen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class TournamentKiller implements Killer{
    @Override
    public void kill(Population population) {
        Random r = new Random();
        for(int i = population.getSpecimens().size() - 1; i > 0; i--){
            int j = r.nextInt(i + 1);
            Specimen specimen = population.getSpecimens().get(i);
            population.getSpecimens().set(i, population.getSpecimens().get(j));
            population.getSpecimens().set(j, specimen);
        }
        List<Specimen> toDelete = new ArrayList<>();
        for(int i = 0; i < population.getSpecimens().size() - 1; i += 2){
            Specimen specimen1 = population.getSpecimens().get(i);
            Specimen specimen2 = population.getSpecimens().get(i + 1);
            if(specimen1.getCost() > specimen2.getCost()){
                toDelete.add(specimen1);
            }else {
                toDelete.add(specimen2);
            }
        }
        population.getSpecimens().removeAll(toDelete);
    }
}
