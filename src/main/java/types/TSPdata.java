package types;

import java.io.Serializable;

public interface TSPdata extends Serializable {
    void save(String fileName);
    double cost(int... path);
}
