package TabuStuff;

import java.util.ArrayList;
import java.util.List;

import static helpers.DistanceMatrix.reversePart;

public class ReverseGeneratorS implements AreaGeneratorS{
    @Override
    public List<Integer> generateArea(List<Integer> currentPoint, int[][] costMatrix, boolean[][] bannedMatrix,
                                      int[][] tabuList, int tabuIndex, long bestCurrentCost) {
        List<Integer> result = new ArrayList<>(currentPoint);
        int currentCost = cost(currentPoint, costMatrix);
        int bestCost = Integer.MAX_VALUE;
        int[] bestSwap = new int[2];
        for (int n = 0; n < costMatrix.length; n++) {
            for (int m = n + 1; m < costMatrix.length; m++) {
                int mm = (m + 1) % costMatrix.length; // m+1
                if (n == mm) {
                    continue;
                }
                int nn = ((n - 1) + costMatrix.length) % costMatrix.length; // n-1
                int newCost = currentCost - (costMatrix[result.get(nn)][result.get(n)]
                        + costMatrix[result.get(m)][result.get(mm)])
                        + costMatrix[result.get(nn)][result.get(m)]
                        + costMatrix[result.get(n)][result.get(mm)];
                if (!bannedMatrix[n][m] && bestCost > newCost) {
                    bestCost = newCost;
                    bestSwap[0] = n;
                    bestSwap[1] = m;
                }else if(bestCurrentCost > newCost){
                    bestCost = newCost;
                    bestCurrentCost = bestCost;
                    bestSwap[0] = n;
                    bestSwap[1] = m;
                }
            }
        }
        bannedMatrix[tabuList[tabuIndex][0]][tabuList[tabuIndex][1]] = false;
        bannedMatrix[tabuList[tabuIndex][1]][tabuList[tabuIndex][0]] = false;
        reversePart(result, bestSwap[0], bestSwap[1]);
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
