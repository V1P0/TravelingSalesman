package Genetic.Mutators;

import Genetic.Specimen;

import java.util.ArrayList;
import java.util.List;

import static helpers.DistanceMatrix.reversePart;

public class TwoOptMutator implements Mutator{
    @Override
    public void mutate(Specimen specimen, int[][] costMatrix) {
        List<Integer> result = new ArrayList<>(specimen.getResult());
        for (int n = 0; n < costMatrix.length; n++) {
            for (int m = n + 1; m < costMatrix.length; m++) {
                int mm = (m + 1) % costMatrix.length;
                if (n == mm)
                    continue;
                int nn = ((n - 1) + costMatrix.length) % costMatrix.length;
                boolean skip = costMatrix[result.get(nn)][result.get(n)]
                        + costMatrix[result.get(m)][result.get(mm)] > costMatrix[result.get(nn)][result.get(m)]
                        + costMatrix[result.get(n)][result.get(mm)];
                if (skip) {
                    reversePart(result, n, m);
                    n = -1;
                    break;
                }
            }
        }
        specimen.setResult(result);
        specimen.setCost(Specimen.cost(result, costMatrix));
    }
}
