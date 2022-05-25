package Genetic.Mutators;

import Genetic.Specimen;

import java.util.List;

import static helpers.DistanceMatrix.reversePart;

public class BestReverseMutator implements Mutator {


    @Override
    public void mutate(Specimen specimen, int[][] costMatrix) {
        List<Integer> result = specimen.getResult();
        long currentCost = specimen.getCost();
        long bestCost = Long.MAX_VALUE;
        int[] bestSwap = new int[2];
        for (int n = 0; n < costMatrix.length; n++) {
            for (int m = n + 1; m < costMatrix.length; m++) {
                int mm = (m + 1) % costMatrix.length; // m+1
                if (n == mm)
                    continue;
                int nn = ((n - 1) + costMatrix.length) % costMatrix.length; // n-1
                long newCost = currentCost - (costMatrix[result.get(nn)][result.get(n)]
                        + costMatrix[result.get(m)][result.get(mm)])
                        + costMatrix[result.get(nn)][result.get(m)]
                        + costMatrix[result.get(n)][result.get(mm)];
                boolean skip = bestCost > newCost;
                if (skip) {
                    bestCost = newCost;
                    bestSwap[0] = n;
                    bestSwap[1] = m;
                }
            }
        }
        reversePart(result, bestSwap[0], bestSwap[1]);
        specimen.setCost(Specimen.cost(result, costMatrix));
    }
}
