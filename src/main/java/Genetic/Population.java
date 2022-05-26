package Genetic;

import Genetic.Crossovers.Crossover;
import Genetic.Killers.Killer;
import Genetic.Mutators.Mutator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Population {
    private List<Specimen> specimens;
    private Mutator mutator;
    private Crossover crossover;
    private Killer killer;
    private double mutationChance;
    int[][] costMatrix;
    int expectedSize;
    long year;

    private Population(){
        year = 0;
        specimens = new ArrayList<>();
    }

    public static Population getRandomPopulation(int size, int[][] costMatrix){
        Population pop = new Population();
        pop.expectedSize = size;
        pop.costMatrix = costMatrix;
        for(int i = 0; i < size; i++){
            pop.getSpecimens().add(Specimen.getRandomSpecimen(costMatrix));
        }
        return pop;
    }

    public List<Specimen> getSpecimens() {
        return specimens;
    }

    public int getExpectedSize(){
        return expectedSize;
    }

    public void setMutator(Mutator mutator){
        this.mutator = mutator;
    }

    public void setCrossover(Crossover crossover){
        this.crossover = crossover;
    }

    public void setKiller(Killer killer){
        this.killer = killer;
    }

    public List<Integer> getBestResult(){
        return getBestSpecimen().getResult();
    }

    public Specimen getBestSpecimen(){
        Specimen bestSpecimen = specimens.get(0);
        for(Specimen specimen : specimens){
            if(specimen.getCost() < bestSpecimen.getCost()){
                bestSpecimen = specimen;
            }
        }
        return bestSpecimen;
    }

    public void setMutationChance(double mutationChance){
        this.mutationChance = mutationChance;
    }

    public void mutate(){
        if(mutator == null){
            return;
        }
        Random r = new Random();

        for(int i = expectedSize/2; i < specimens.size(); i++){
            if(r.nextDouble() < mutationChance){
                mutator.mutate(specimens.get(i), costMatrix);
            }
        }
    }

    public void killWorst(){
        if(killer == null){
            return;
        }
        killer.kill(this);
    }

    public void crossover(){
        if(crossover == null){
            return;
        }
        //shuffle specimens
        Random r = new Random();
        for(int i = specimens.size() - 1; i > 0; i--){
            int j = r.nextInt(i + 1);
            Specimen specimen = specimens.get(i);
            specimens.set(i, specimens.get(j));
            specimens.set(j, specimen);
        }
        //crossover
        int startingSize = specimens.size();
        for(int i = 0; i < startingSize - 1; i += 2){
            Specimen specimen1 = specimens.get(i);
            Specimen specimen2 = specimens.get(i + 1);
            Specimen[] newSpec = crossover.crossover(specimen1, specimen2, costMatrix);
            specimens.addAll(Arrays.asList(newSpec));
        }
    }

    public void updateAges(){
        for(Specimen specimen : specimens){
            specimen.updateAge();
        }
        year++;
    }

    public long getYear(){
        return year;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(Specimen specimen : specimens){
            sb.append(specimen.toString()).append("\n");
        }
        sb.append("year:").append(year).append("\n");
        return sb.toString();
    }
}