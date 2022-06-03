package Genetic.Crossovers;

import Genetic.Specimen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class PMXCrossover implements Crossover {
    Random r = new Random();
    @Override
    public Specimen[] crossover(Specimen specimen1, Specimen specimen2, int[][] costMatrix) {
        List<Integer> par1 = new ArrayList<>(specimen1.getResult());
        List<Integer> par2 = new ArrayList<>(specimen2.getResult());

        int p1 = r.nextInt(par1.size());
        int p2 = r.nextInt(par1.size());

        if(p1 > p2){
            int tmp = p1;
            p1 = p2;
            p2 = tmp;
        }

        Integer[] ch1 = new Integer[par1.size()];
        Integer[] ch2 = new Integer[par1.size()];

        int[] mapping1 = new int[par1.size()];
        int[] mapping2 = new int[par1.size()];

        boolean[] used1 = new boolean[par1.size()];
        Arrays.fill(used1, false);
        boolean[] used2 = new boolean[par1.size()];
        Arrays.fill(used2, false);


        for(int i = p1; i < p2; i++){
            ch1[i] = par2.get(i);
            used1[par2.get(i)] = true;
            ch2[i] = par1.get(i);
            used2[par1.get(i)] = true;
            mapping1[par2.get(i)] = par1.get(i);
            mapping2[par1.get(i)] = par2.get(i);
        }

        for(int i = 0; i<p1; i++){
            int x = par1.get(i);
            while(used1[x]){
                x = mapping1[x];
            }
            ch1[i] = x;

            x = par2.get(i);
            while(used2[x]){
                x = mapping2[x];
            }
            ch2[i] = x;
        }

        for(int i = p2; i<par1.size(); i++){
            int x = par1.get(i);
            while(used1[x]){
                x = mapping1[x];
            }
            ch1[i] = x;

            x = par2.get(i);
            while(used2[x]){
                x = mapping2[x];
            }
            ch2[i] = x;
        }

        Specimen child1 = new Specimen();
        child1.setResult(Arrays.asList(ch1));
        child1.setCost(Specimen.cost(Arrays.asList(ch1), costMatrix));
        Specimen child2 = new Specimen();
        child2.setResult(Arrays.asList(ch2));
        child2.setCost(Specimen.cost(Arrays.asList(ch2), costMatrix));

        return new Specimen[]{child1, child2};

    }
}
