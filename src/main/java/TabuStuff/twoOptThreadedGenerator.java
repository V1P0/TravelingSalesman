package TabuStuff;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static helpers.DistanceMatrix.reversePart;
import static java.lang.Thread.sleep;

/**
 * use only for problems with more than 100 cities
 */
public class twoOptThreadedGenerator implements AreaGenerator{

    int threadCount;
    Thread[] threads;


    public twoOptThreadedGenerator(int threadCount){
        this.threadCount = threadCount;
        threads = new Thread[threadCount];
    }


    public int[] generateAreaUtil(List<Integer> currentPoint, int[][] costMatrix, boolean[][] bannedMatrix, int k) {
        List<Integer> result = new ArrayList<>(currentPoint);
        int currentCost = cost(currentPoint, costMatrix);
        int bestCost = Integer.MAX_VALUE;
        int[] bestSwap = new int[2];
        for (int n = k; n < costMatrix.length; n+=threadCount) {
            for (int m = n + 1; m < costMatrix.length; m++) {
                if (bannedMatrix[n][m]) {
                    continue;
                }
                int mm = (m + 1) % costMatrix.length; // m+1
                if (n == mm)
                    continue;
                int nn = ((n - 1) + costMatrix.length) % costMatrix.length; // n-1
                int newCost = currentCost - (costMatrix[result.get(nn)][result.get(n)]
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
        return new int[]{bestSwap[0], bestSwap[1], bestCost};
    }


    @Override
    public List<Integer> generateArea(List<Integer> currentPoint, int[][] costMatrix, boolean[][] bannedMatrix,
                                      int[][] tabuList, int tabuIndex) {
        List<Integer> result = new ArrayList<>(currentPoint);
        AtomicInteger bestCost = new AtomicInteger(Integer.MAX_VALUE);
        int[] bestSwap = new int[2];
        for(int i = 0; i < threadCount; i++){
            int finalI = i;
            threads[i] = new Thread(() -> {
                int[] swap = generateAreaUtil(result, costMatrix.clone(), bannedMatrix.clone(), finalI);
                synchronized (bestSwap){
                    if(swap[2] < bestCost.get()){
                        bestCost.set(swap[2]);
                        bestSwap[0] = swap[0];
                        bestSwap[1] = swap[1];
                    }
                }
            });
            threads[i].start();
        }
        for(int i = 0; i < threadCount; i++){
            try {
                threads[i].join();
            }catch (InterruptedException e){
                e.printStackTrace();
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
