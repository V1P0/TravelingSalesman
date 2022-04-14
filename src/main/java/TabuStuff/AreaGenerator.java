package TabuStuff;

import java.util.List;

public interface AreaGenerator {
    public List<Integer> generateArea(List<Integer> currentPoint, int[][] costMatrix, boolean[][] bannedMatrix, int[][] tabuList, int tabuIndex);
}
