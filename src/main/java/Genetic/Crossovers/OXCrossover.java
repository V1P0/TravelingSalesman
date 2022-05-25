package Genetic.Crossovers;

import Genetic.Specimen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OXCrossover implements Crossover{
    @Override
    public Specimen[] crossover(Specimen parent1, Specimen parent2, int[][] costMatrix) {
        List<Integer> res1 = parent1.getResult();
        List<Integer> res2 = parent2.getResult();
        int pivot1 = (int) (Math.random() * res1.size());
        int pivot2 = (int) (Math.random() * res2.size());
        int start = Math.min(pivot1, pivot2);
        int end = Math.max(pivot1, pivot2);
        Integer[] resChild1 = new Integer[res1.size()];
        Integer[] resChild2 = new Integer[res1.size()];
        boolean[] used1 = new boolean[costMatrix.length];
        boolean[] used2 = new boolean[costMatrix.length];

        for(int i = start; i < end; i++){
            resChild1[i] = res1.get(i);
            used1[res1.get(i)] = true;
            resChild2[i] = res2.get(i);
            used2[res2.get(i)] = true;
        }
        int j = 0;
        for(int i = 0; i< start;){
            int x = res2.get(j);
            if (!used1[x]) {
                resChild1[i] = x;
                used1[x] = true;
                i++;
            }
            j++;
        }
        for(int i = end; i< resChild1.length;){
            int x = res2.get(j);
            if (!used1[x]) {
                resChild1[i] = x;
                used1[x] = true;
                i++;
            }
            j++;
        }
        j = 0;
        for(int i = 0; i< start;){
            int x = res1.get(j);
            if (!used2[x]) {
                resChild2[i] = x;
                used2[x] = true;
                i++;
            }
            j++;
        }
        for(int i = end; i< resChild1.length;){
            int x = res1.get(j);
            if (!used2[x]) {
                resChild2[i] = x;
                used2[x] = true;
                i++;
            }
            j++;
        }

        Specimen child1 = new Specimen();
        child1.setResult(Arrays.asList(resChild1));
        child1.setCost(Specimen.cost(Arrays.asList(resChild1), costMatrix));
        Specimen child2 = new Specimen();
        child2.setResult(Arrays.asList(resChild2));
        child2.setCost(Specimen.cost(Arrays.asList(resChild2), costMatrix));
        return new Specimen[]{child1, child2};
    }
}
