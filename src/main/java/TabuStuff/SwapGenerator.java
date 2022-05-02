package TabuStuff;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SwapGenerator implements AreaGenerator {
    @Override
    public List<Integer> generateArea(List<Integer> currentPoint, int[][] costMatrix, boolean[][] bannedMatrix,
            int[][] tabuList, int tabuIndex) {
        List<Integer> result = new ArrayList<>(currentPoint);
        int currentCost = cost(currentPoint, costMatrix);
        int bestCost = Integer.MAX_VALUE;
        int[] bestSwap = new int[2];

        for (int n = 0; n < costMatrix.length; n++) {
            for (int m = n + 1; m < costMatrix.length; m++) {
                if (bannedMatrix[n][m])
                    continue;
                int nPrev = n - 1;
                int nNext = n + 1;
                int mPrev = m - 1;
                int mNext = m + 1;
                int newCost;

                if ((n == mPrev && m == nNext) || nNext == mPrev) {
                    int beforeSwapCost = currentCost - (costMatrix[result.get(nPrev)][result.get(n)]
                            + costMatrix[result.get(m)][result.get(mNext)]);
                    newCost = beforeSwapCost
                            + caseHaveSameNeighbor(costMatrix, result, nPrev, n, nNext, mPrev, m, mNext);
                } else {
                    int beforeSwapCost = currentCost - (costMatrix[result.get(nPrev)][result.get(n)]
                            + costMatrix[result.get(n)][result.get(nNext)]
                            + costMatrix[result.get(mPrev)][result.get(m)]
                            + costMatrix[result.get(m)][result.get(mNext)]);
                    newCost = beforeSwapCost + caseAreAway(costMatrix, result, nPrev, n, nNext, mPrev, m, mNext);
                }

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
        Collections.swap(result, bestSwap[0], bestSwap[1]);
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

    public int caseHaveSameNeighbor(int[][] costMatrix, List<Integer> result, int nPrev, int n, int nNext, int mPrev,
            int m, int mNext) {

        return costMatrix[result.get(nPrev)][result.get(m)]
                + costMatrix[result.get(n)][result.get(mNext)];
    }

    public int caseAreAway(int[][] costMatrix, List<Integer> result, int nPrev, int n, int nNext, int mPrev, int m,
            int mNext) {

        return costMatrix[result.get(mPrev)][result.get(n)]
                + costMatrix[result.get(n)][result.get(mNext)]
                + costMatrix[result.get(nPrev)][result.get(m)]
                + costMatrix[result.get(m)][result.get(nNext)];
    }
}
