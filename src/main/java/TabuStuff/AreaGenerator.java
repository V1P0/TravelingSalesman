package TabuStuff;

import java.util.List;

public interface AreaGenerator {
    List<Integer> generateArea(List<Integer> currentPoint, int[][] costMatrix, boolean[][] bannedMatrix, int[][] tabuList, int tabuIndex);
}
