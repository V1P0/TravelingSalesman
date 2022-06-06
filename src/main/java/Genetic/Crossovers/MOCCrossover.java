package Genetic.Crossovers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Genetic.Specimen;

public class MOCCrossover implements Crossover {

    @Override
    public Specimen[] crossover(Specimen parent1, Specimen parent2, int[][] costMatrix) {
        List<Integer> res1 = parent1.getResult();
        List<Integer> res2 = parent2.getResult();
        int pivot1 = (int) (Math.random() * res1.size() + 1);
        Integer[] resChild1 = new Integer[res1.size()];
        Integer[] resChild2 = new Integer[res1.size()];

        List<Integer> res1Save = new ArrayList<Integer>(res1);
        List<Integer> res2Save = new ArrayList<Integer>(res2);
        // Dzielimy listy na dwie części
        List<Integer> res1Left = new ArrayList<Integer>(res1.subList(0, pivot1));
        List<Integer> res1LeftRight = new ArrayList<Integer>(res1Left.subList(res1Left.size() / 2, res1Left.size()));
        List<Integer> res1Right = new ArrayList<Integer>(res1.subList(pivot1, res1.size()));
        List<Integer> res2Left = new ArrayList<Integer>(res2.subList(0, pivot1));
        List<Integer> res2LeftRight = new ArrayList<Integer>(res2Left.subList(res2Left.size() / 2, res2Left.size()));
        List<Integer> res2Right = new ArrayList<Integer>(res2.subList(pivot1, res2.size()));

        // System.out.println("res1Left: " + res1Left);
        // System.out.println("res1Right: " + res1Right);
        // System.out.println("res2Left: " + res2Left);
        // System.out.println("res2Right: " + res2Right);
        // System.out.println("res1LeftRight: " + res1LeftRight);
        // System.out.println("res1: " + res1);
        // System.out.println("res2LeftRight: " + res2LeftRight);

        // Remove from res1 and res2 all elements from secound half of res1Left and
        // res2Left
        for (int i = res1.indexOf(res1LeftRight.get(0)); i <= res1
                .indexOf(res1LeftRight.get(res1LeftRight.size() - 1)); i++) {
            res1.set(i, null);
        }
        for (int i = res2.indexOf(res2LeftRight.get(0)); i <= res2
                .indexOf(res2LeftRight.get(res2LeftRight.size() - 1)); i++) {
            res2.set(i, null);
        }

        // System.out.println("res1: " + res1);
        // System.out.println(res1);
        // System.out.println(res2);
        // Delete all elements which are'nt in sound half of res1Left and res2Left
        // System.out.println(res1Left.size() - res1LeftRight.size());
        for (int i = res1Left.size() - res1LeftRight.size(); i < res1.size(); i++) {
            if (res2LeftRight.indexOf(res1.get(i)) == -1) {
                res1.set(i, null);
            }
        }
        for (int i = res2Left.size() / 2; i < res2.size(); i++) {
            if (res1LeftRight.indexOf(res2.get(i)) == -1) {
                res2.set(i, null);
            }
        }
        // System.out.println("res1: " + res1);
        // System.out.println("res2Right: " + res2Right);

        // And now we can add elements from res2Right and res1Right to res1 and res2
        int count = 0;
        // System.out.println(res2Right.get(res2Right.size()));
        for (int i = 0; i < res1.size(); i++) {
            if (res1.get(i) == null && count < res2Right.size()) {
                if (res1.indexOf(res2Right.get(count)) == -1) {
                    res1.set(i, res2Right.get(count));
                    count++;
                }
            }
        }
        count = 0;
        for (int i = 0; i < res2.size(); i++) {
            if (res2.get(i) == null && count < res1Right.size()) {
                if (res2.indexOf(res1Right.get(count)) == -1) {
                    res2.set(i, res1Right.get(count));
                    count++;
                }
            }
        }

        // System.out.println(resChild1.length);

        // get element wich isnt in res1 but are in res1Save
        // System.out.println(res1Save);
        for (int i = 0; i < res1.size(); i++) {
            if (res1.get(i) == null) {
                for (int j = 0; j < res1Save.size(); j++) {
                    if (res1.indexOf(res1Save.get(j)) == -1) {
                        res1.set(i, res1Save.get(j));
                    } else if (res1.indexOf(res2Save.get(j)) == -1) {
                        res1.set(i, res2Save.get(j));
                    }
                }
            }
        }
        // // get element wich isnt in res2 but are in res2Save
        for (int i = 0; i < res2.size(); i++) {
            if (res2.get(i) == null) {
                for (int j = 0; j < res2Save.size(); j++) {
                    if (res2.indexOf(res2Save.get(j)) == -1) {
                        res2.set(i, res2Save.get(j));
                    } else if (res2.indexOf(res1Save.get(j)) == -1) {
                        res2.set(i, res1Save.get(j));
                    }
                }
            }
        }
        System.out.println("res1: " + res1);
        System.out.println("res2: " + res2);
        // Move elements from res1 and res2 to resChild1 and resChild2
        for (int i = 0; i < res1.size(); i++) {
            resChild1[i] = res1.get(i);
        }
        for (int i = 0; i < res2.size(); i++) {
            resChild2[i] = res2.get(i);
        }
        Specimen child1 = new Specimen();
        child1.setResult(Arrays.asList(resChild1));
        child1.setCost(Specimen.cost(Arrays.asList(resChild1), costMatrix));
        Specimen child2 = new Specimen();
        child2.setResult(Arrays.asList(resChild2));
        child2.setCost(Specimen.cost(Arrays.asList(resChild2), costMatrix));
        // System.out.println(child2.getCost());
        return new Specimen[] { child1, child2 };
    }

}
// 23125