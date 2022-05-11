package TabuStuff;

import java.util.List;

public interface AreaGeneratorS {
    public List<Integer> generateArea(List<Integer> currentPoint, int[][] costMatrix, boolean[][] bannedMatrix, int[][] tabuList, int tabuIndex, long currentBestCost);

}
