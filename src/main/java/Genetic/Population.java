package Genetic;

import Genetic.Crossovers.Crossover;
import Genetic.Killers.Killer;
import Genetic.Mutators.Mutator;
import helpers.DistanceMatrix;

import java.util.*;

public class Population implements Cloneable{
    private List<Specimen> specimens;
    private Mutator mutator;
    private Crossover crossover;
    private Killer killer;
    private double mutationChance;
    public int[][] costMatrix;
    int expectedSize;
    long year;
    public Specimen overallBest;

    private Population(){
        year = 0;
        specimens = new ArrayList<>();
    }

    public static Population getRandomPopulation(int size, int[][] costMatrix){
        Population pop = new Population();
        pop.expectedSize = size;
        pop.costMatrix = costMatrix;
        pop.overallBest = Specimen.getRandomSpecimen(costMatrix);
        for(int i = 0; i < size; i++){
            pop.getSpecimens().add(Specimen.getRandomSpecimen(costMatrix));
        }
        return pop;
    }

    public static Population getTwoOptedPopulation(int size, DistanceMatrix dm, double twoOptPercent){
        int[][] costMatrix = dm.matrix;
        int twoOptSize = (int)(size*twoOptPercent);
        Population pop = new Population();
        pop.expectedSize = size;
        pop.costMatrix = costMatrix;
        pop.overallBest = Specimen.getRandomSpecimen(costMatrix);
        for(int i = 0; i < twoOptSize; i++){
            Specimen rand = Specimen.getRandomSpecimen(costMatrix);
            List<Integer> newResult = dm.twoOptAcc(rand.getResult());
            rand.setResult(newResult);
            rand.setCost(Specimen.cost(newResult, costMatrix));
            pop.getSpecimens().add(rand);
        }
        for(int i = twoOptSize; i< size; i++){
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
        Specimen bk = getBestSpecimen();
        if(bk.getCost() < overallBest.getCost()){
            overallBest = bk;
        }
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

    public List<Specimen> getBestPercent(double percent){
        getSpecimens().sort((s1, s2) -> (int) (s1.getCost() - s2.getCost()));
        List<Specimen> bests = new ArrayList<>(getSpecimens().subList(0, (int)(expectedSize*percent)));
        specimens = new ArrayList<>(specimens.subList((int)(expectedSize*percent), specimens.size()));
        return bests;
    }

    public static void shuffle_populations(double percent, Population... pops){
        List<Integer> permutation = new ArrayList<>();
        for(int i = 0; i< pops.length; i++){
            permutation.add(i);
        }
        Collections.shuffle(permutation);
        List<List<Specimen>> bests = new ArrayList<>();
        for (int i = 0; i< pops.length; i++) {
            bests.add(pops[i].getBestPercent(percent));
        }
        for(int i = 0; i< pops.length; i++){
            pops[permutation.get(i)].getSpecimens().addAll(bests.get(permutation.get((i+1)%pops.length)));
        }
    }

    public int getAverageAge(){
        int age = 0;
        int size = 0;
        for(Specimen s : specimens){
            age+=s.getAge();
            size++;
        }
        return age/size;
    }

    @Override
    public Object clone(){
        Population pop = null;
        try{
            pop = (Population) super.clone();
            pop.specimens = new ArrayList<>();
            for(Specimen s: specimens){
                pop.specimens.add((Specimen)s.clone());
            }
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return pop;
    }
}
