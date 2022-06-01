package Genetic.Crossovers;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Genetic.Specimen;

public class MOCCrossover implements Crossover {

    @Override
    public Specimen[] crossover(Specimen parent1, Specimen parent2, int[][] costMatrix) {
        List<Integer> res1 = parent1.getResult();
        List<Integer> res2 = parent2.getResult();
        int pivot1 = (int) (Math.random() * res1.size());
        Integer[] resChild1 = new Integer[res1.size()];
        Integer[] resChild2 = new Integer[res1.size()];

        // Dzielimy listy na dwie części
        List<Integer> res1Left = new ArrayList<Integer>(res1.subList(0, pivot1));
        List<Integer> res1Right = new ArrayList<Integer>(res1.subList(pivot1, res1.size()));
        List<Integer> res2Left = new ArrayList<Integer>(res2.subList(0, pivot1));
        List<Integer> res2Right = new ArrayList<Integer>(res2.subList(pivot1, res2.size()));

        System.out.println("res1Left: " + res1Left);
        System.out.println("res1Right: " + res1Right);
        System.out.println("res2Left: " + res2Left);
        System.out.println("res2Right: " + res2Right);

        // Remove from res1 and res2 all elements from secound half of res1Left and
        // res2Left
        for (int i = res1Left.size() / 2; i < res1Left.size(); i++) {
            res1.remove(i);
            res1.add(i, null);
        }
        for (int i = res2Left.size() / 2; i < res2Left.size(); i++) {
            res2.remove(i);
            res2.add(i, null);
        }

        // Delete all elements which are'nt in sound half of res1Left and res2Left
        for (int i = res2Left.size() / 2; i < res2Left.size(); i++) {
            if (res1.indexOf(res2Left.get(i)) == -1) {
                res1.remove(res2Left.get(i));
                res1.add(res2Left.get(i), null);
            }
        }
        for (int i = res1Left.size() / 2; i < res1Left.size(); i++) {
            if (res2.indexOf(res1Left.get(i)) == -1) {
                res2.remove(res1Left.get(i));
                res2.add(res1Left.get(i), null);
            }
        }

        // And now we can add elements from res2Right and res1Right to res1 and res2
        res2Right.get(0);
        for (int i = 0; i < res1.size(); i++) {
            if (res1.get(i) == null) {
                res1.remove(i);
                res1.add(i, res2Right.get(0));
                if (res2Right.size() > 1) {
                    res2Right.remove(0);
                }
            }
        }
        for (int x = 0; x < res2.size(); x++) {
            if (res2.get(x) == null) {
                res2.remove(x);
                res2.add(x, res1Right.get(0));
                if (res1Right.size() > 1) {
                    res1Right.remove(0);
                }
            }
        }

        System.out.println("res1: " + res1);
        System.out.println("res2: " + res2);

        // Move elements from res1 and res2 to resChild1 and resChild2
        for (int i = 0; i < res1.size() - 1; i++) {
            resChild1[i] = res1.get(i);
        }
        for (int i = 0; i < res2.size() - 1; i++) {
            resChild2[i] = res2.get(i);
        }

        Specimen child1 = new Specimen();
        child1.setResult(Arrays.asList(resChild1));
        child1.setCost(Specimen.cost(Arrays.asList(resChild1), costMatrix));
        Specimen child2 = new Specimen();
        child2.setResult(Arrays.asList(resChild2));
        child2.setCost(Specimen.cost(Arrays.asList(resChild2), costMatrix));

        return new Specimen[] { child1, child2 };
    }

}
