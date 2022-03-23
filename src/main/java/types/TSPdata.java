package types;

import java.io.Serializable;

public interface TSPdata extends Serializable {
    void save(String fileName);

    long cost(int... path);
    // int[] kRandom(int k);
}
