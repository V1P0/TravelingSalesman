// package Genetic.Crossovers;

// import java.util.ArrayList;
// import java.util.List;

// import Genetic.Specimen;

// public class OX1Crossover implements Crossover {

// @Override
// public Specimen[] crossover(Specimen parent1, Specimen parent2, int[][]
// costMatrix) {
// List<Integer> res1 = parent1.getResult();
// List<Integer> res2 = parent2.getResult();
// Integer[] resChild1 = new Integer[res1.size()];
// Integer[] resChild2 = new Integer[res1.size()];

// System.out.println(res1);
// System.out.println(res2);

// int pivot1 = (int) (Math.random() * res1.size() + 1);
// int pivot2 = (int) (Math.random() * res1.size() + (res1.size() / 2));

// List<Integer> res1Left = new ArrayList<Integer>(res1.subList(0, pivot1));
// List<Integer> res1Center = new ArrayList<Integer>(res1.subList(pivot1,
// pivot2));
// List<Integer> res1Right = new ArrayList<Integer>(res1.subList(pivot2,
// res1.size()));
// List<Integer> res2Left = new ArrayList<Integer>(res2.subList(0, pivot1));
// List<Integer> res2Center = new ArrayList<Integer>(res2.subList(pivot1,
// pivot2));
// List<Integer> res2Right = new ArrayList<Integer>(res2.subList(pivot2,
// res2.size()));

// return new Specimen[] { res1, res2 };
// }
// }