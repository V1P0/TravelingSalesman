package TabuStuff;

import java.util.ArrayList;
import java.util.List;

public class InsertGenerator implements AreaGenerator {

    @Override
    public List<Integer> generateArea(List<Integer> currentPoint, int[][] costMatrix, boolean[][] bannedMatrix,
            int[][] tabuList, int tabuIndex) {
        List<Integer> result = new ArrayList<>(currentPoint);
        int currentCost = cost(currentPoint, costMatrix);
        int bestCost = Integer.MAX_VALUE;
        int[] bestSwap = new int[2];

        for (int n = 0; n < costMatrix.length; n++) {
            for (int m = 0; m < costMatrix.length; m++) {
                if (bannedMatrix[n][m])
                    continue;

                int nPrev = n - 1;
                int mPrev = m - 1;
                int mNext = m + 1;
                int newCost;

                int beforeSwapCost = currentCost - (costMatrix[result.get(nPrev)][result.get(n)]
                        + costMatrix[result.get(mPrev)][result.get(m)] + costMatrix[result.get(m)][result.get(mNext)]);

                newCost = beforeSwapCost + (costMatrix[result.get(nPrev)][result.get(m)]
                        + costMatrix[result.get(m)][result.get(n)] + costMatrix[result.get(mPrev)][result.get(mNext)]);

                boolean skip = bestCost > newCost;
                if (skip) {
                    bestCost = newCost;
                    bestSwap[0] = n;
                    bestSwap[1] = m;
                }

            }
        }
        bannedMatrix[tabuList[tabuIndex][0]][tabuList[tabuIndex][1]] = false;
        bannedMatrix[tabuList[tabuIndex][1]][tabuList[tabuIndex][0]] = false;
        result.remove(bestSwap[1]);
        result.add(bestSwap[0] - 1, bestSwap[1]);
        bannedMatrix[bestSwap[0]][bestSwap[1]] = true;
        bannedMatrix[bestSwap[1]][bestSwap[0]] = true;
        tabuList[tabuIndex][0] = bestSwap[0];
        tabuList[tabuIndex][1] = bestSwap[1];

        return result;
    }

    public int cost(List<Integer> path, int[][] matrix) {
        int sum = 0;
        for (int i = 0; i < path.size(); i++) {
            sum += matrix[path.get(i)][path.get((i + 1) % path.size())];
        }
        return sum;
    }
}
