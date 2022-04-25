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
                int mm = (m + 1) % costMatrix.length;
                if (n == mm)
                    continue;
                int nn = ((n - 1) + costMatrix.length) % costMatrix.length;
                // int newCost = currentCost - (costMatrix[result.get(nn)][result.get(n)] +
                // costMatrix[result.get(m)][result.get(mm)] +
                // costMatrix[result.get(index)][nn]);

            }
        }

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
