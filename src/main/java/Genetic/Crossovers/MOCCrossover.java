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
        // System.out.println("res1LeftRight: " + res1LeftRight);
        // System.out.println("res1Right: " + res1Right);
        // System.out.println("res2Left: " + res2Left);
        // System.out.println("res2LeftRight: " + res2LeftRight);
        // System.out.println("res2Right: " + res2Right);

        // Fill res1Child1 with res1Left without elemets res1LeftRight and rest of null
        for (int i = 0; i < res1Left.size(); i++) {
            if (res1LeftRight.indexOf(res1Left.get(i)) == -1) {
                resChild1[i] = res1Left.get(i);
            } else {
                resChild1[i] = null;
            }
        }

        // Fill res1Child2 with res2Left without elemets res2LeftRight and rest of null
        for (int i = 0; i < res2Left.size(); i++) {
            if (res2LeftRight.indexOf(res2Left.get(i)) == -1) {
                resChild2[i] = res2Left.get(i);
            } else {
                resChild2[i] = null;
            }
        }

        // Fill res1Child1 elements from res1 if are in res2LeftRight and set them on
        // index from res1
        for (int i = 0; i < res2LeftRight.size(); i++) {
            if (res1.indexOf(res2LeftRight.get(i)) != -1) {
                resChild1[res1.indexOf(res2LeftRight.get(i))] = res2LeftRight.get(i);
            }
        }

        // Fill res1Child2 elements from res2 if are in res1LeftRight and set them on
        // index from res2
        for (int i = 0; i < res1LeftRight.size(); i++) {
            if (res2.indexOf(res1LeftRight.get(i)) != -1) {
                resChild2[res2.indexOf(res1LeftRight.get(i))] = res1LeftRight.get(i);
            }
        }

        // Fill all null elements from res1Child1 with elements from res2Right which are
        // in res1
        for (int i = 0; i < resChild1.length; i++) {
            if (resChild1[i] == null) {
                for (int j = 0; j < res2Right.size(); j++) {
                    if (res1.indexOf(res2Right.get(j)) != -1
                            && Arrays.asList(resChild1).indexOf(res2Right.get(j)) == -1) {
                        resChild1[i] = res2Right.get(j);
                        break;
                    }
                }
            }
        }

        // Fill all null elements from res1Child2 with elements from res1Right which are
        // in res2
        for (int i = 0; i < resChild2.length; i++) {
            if (resChild2[i] == null) {
                for (int j = 0; j < res1Right.size(); j++) {
                    if (res2.indexOf(res1Right.get(j)) != -1
                            && Arrays.asList(resChild2).indexOf(res1Right.get(j)) == -1) {
                        resChild2[i] = res1Right.get(j);
                        break;
                    }
                }
            }
        }

        // Fill all null elements from res1Child1 with elements from res1 if are'n in
        // res1Child1 on correct index
        for (int i = 0; i < resChild1.length; i++) {
            if (resChild1[i] == null) {
                for (int j = 0; j < res1.size(); j++) {
                    if (Arrays.asList(resChild1).indexOf(res1.get(j)) == -1) {
                        resChild1[i] = res1.get(j);
                        break;
                    }
                }
            }
        }

        // Fill all null elements from res1Child2 with elements from res2 if are'n in
        // res1Child2 on correct index
        for (int i = 0; i < resChild2.length; i++) {
            if (resChild2[i] == null) {
                for (int j = 0; j < res2.size(); j++) {
                    if (Arrays.asList(resChild2).indexOf(res2.get(j)) == -1) {
                        resChild2[i] = res2.get(j);
                        break;
                    }
                }
            }
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