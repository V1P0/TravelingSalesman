package Genetic.Crossovers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Genetic.Specimen;

public class CXCrossover implements Crossover {

    @Override
    public Specimen[] crossover(Specimen parent1, Specimen parent2, int[][] costMatrix) {
        List<Integer> res1 = parent1.getResult();
        List<Integer> res1Help = new ArrayList<Integer>(res1.subList(0, res1.size()));
        List<Integer> res2 = parent2.getResult();
        List<Integer> res2Help = new ArrayList<Integer>(res2.subList(0, res2.size()));
        Integer[] resChild1 = new Integer[res1.size()];
        Integer[] resChild2 = new Integer[res1.size()];
        String lastParent = "res1";

        // Res2 and res1 set all elements null
        for (int i = 0; i < res1.size(); i++) {
            res1Help.set(i, null);
            res2Help.set(i, null);
        }

        // Cycle Crossover for res1Help
        for (int i = 0; i < res1Help.size(); i++) {
            if (res1Help.indexOf(res1.get(i)) == -1 && lastParent.equals("res1")) {
                res1Help.set(i, res1.get(i));
                lastParent = "res2";
            } else if (res1Help.indexOf(res2.get(i)) == -1 && lastParent.equals("res2")) {
                res1Help.set(i, res2.get(i));
                lastParent = "res1";
            } else if (res1Help.indexOf(res1.get(i)) == -1) {
                res1Help.set(i, res1.get(i));
            } else if (res1Help.indexOf(res2.get(i)) == -1) {
                res1Help.set(i, res2.get(i));
            }
        }
        lastParent = "res2";
        // Cycle Crossover for res2Help
        for (int i = 0; i < res2Help.size(); i++) {
            if (res2Help.indexOf(res2.get(i)) == -1 && lastParent.equals("res2")) {
                res2Help.set(i, res2.get(i));
                lastParent = "res1";
            } else if (res2Help.indexOf(res1.get(i)) == -1 && lastParent.equals("res1")) {
                res2Help.set(i, res1.get(i));
                lastParent = "res2";
            } else if (res2Help.indexOf(res2.get(i)) == -1) {
                res2Help.set(i, res2.get(i));
            } else if (res2Help.indexOf(res1.get(i)) == -1) {
                res2Help.set(i, res1.get(i));
            }
        }

        // res1Help fill elements which are null elements from res1 which aren't in
        // res1Help
        for (int i = 0; i < res1Help.size(); i++) {
            if (res1Help.get(i) == null) {
                for (int j = 0; j < res1.size(); j++) {
                    if (res1Help.indexOf(res1.get(j)) == -1) {
                        res1Help.set(i, res1.get(j));
                    }
                }
            }
        }
        // res2Help fill elements which are null elements from res2 which aren't in
        // res2Help
        for (int i = 0; i < res2Help.size(); i++) {
            if (res2Help.get(i) == null) {
                for (int j = 0; j < res2.size(); j++) {
                    if (res2Help.indexOf(res2.get(j)) == -1) {
                        res2Help.set(i, res2.get(j));
                    }
                }
            }
        }

        // res1Help to resChild1 and res2Help to resChild2
        for (int i = 0; i < res1Help.size(); i++) {
            resChild1[i] = res1Help.get(i);
            resChild2[i] = res2Help.get(i);
        }

        System.out.println(res1Help);
        System.out.println(res2Help);

        Specimen child1 = new Specimen();
        child1.setResult(Arrays.asList(resChild1));
        child1.setCost(Specimen.cost(Arrays.asList(resChild1), costMatrix));
        Specimen child2 = new Specimen();
        child2.setResult(Arrays.asList(resChild2));
        child2.setCost(Specimen.cost(Arrays.asList(resChild2), costMatrix));

        return new Specimen[] { child1, child2 };
    }

}
