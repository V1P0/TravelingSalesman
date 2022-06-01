package Genetic.Killers;

import Genetic.Population;
import Genetic.Specimen;

import java.util.Random;

public class RouletteKiller implements Killer{
    @Override
    public void kill(Population population) {
        long bc = population.getBestSpecimen().getCost();
        int size = population.getSpecimens().size();
        int fit_sum = 0;
        for(Specimen s : population.getSpecimens()){
            fit_sum += f(s.getCost(), bc);
        }
        Random rand = new Random();
        while(size > population.getExpectedSize()/2){
            int shot = rand.nextInt(fit_sum+1);
            int sum = 0;
            //shuffle specimens
            for(int i = size - 1; i > 0; i--){
                int j = rand.nextInt(i + 1);
                Specimen specimen = population.getSpecimens().get(i);
                population.getSpecimens().set(i, population.getSpecimens().get(j));
                population.getSpecimens().set(j, specimen);
            }
            for(int i = 0; i< size; i++){
                sum+=f(population.getSpecimens().get(i).getCost(), bc);
                if(sum > shot){
                    fit_sum -= f(population.getSpecimens().get(i).getCost(), bc);
                    population.getSpecimens().remove(i);
                    //System.out.println(size);
                    size--;
                    break;
                }
            }
            //System.out.println(size);
        }
    }

    private int f(long c, long bc){
        return (int)((c-bc)*100/bc)+1;
    }
}
