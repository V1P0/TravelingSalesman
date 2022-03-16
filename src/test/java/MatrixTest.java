import helpers.DistanceMatrix;
import org.junit.Test;
import org.junit.Assert;

import java.io.File;


public class MatrixTest {
    @Test
    public void asymmetricTest(){
        DistanceMatrix dm = new DistanceMatrix(20, DistanceMatrix.types.ASYMMETRIC);
        for(int i = 0; i<20; i++){
            Assert.assertEquals(dm.matrix[i][i], -1, 0.0);
        }
    }
    @Test
    public void symmetricTest(){
        DistanceMatrix dm = new DistanceMatrix(20, DistanceMatrix.types.SYMMETRIC);
        for(int i = 0; i<20; i++){
            for(int k = 0; k<20; k++){
                if(i==k){
                    Assert.assertEquals(dm.matrix[i][k], -1, 0.0);
                }else{
                    Assert.assertEquals(dm.matrix[i][k], dm.matrix[k][i], 0.0);
                }
            }
        }
    }

    @Test
    public void saveTest() {
        DistanceMatrix dm = new DistanceMatrix(20, DistanceMatrix.types.ASYMMETRIC);
        dm.save("test.dm");
        File file = new File("test.dm");
        DistanceMatrix dm2 = DistanceMatrix.load(file);
        Assert.assertNotNull(dm2);
        for(int i = 0; i<20; i++){
            for(int k = 0; k<20; k++){
                Assert.assertEquals(dm.matrix[i][k], dm2.matrix[i][k], 0.0);
            }
        }
        file.delete();
    }
    @Test
    public void costTest(){
        DistanceMatrix dm = new DistanceMatrix(20, DistanceMatrix.types.SYMMETRIC);
        double sum = dm.matrix[1][3] + dm.matrix[3][6] + dm.matrix[6][8] + dm.matrix[8][1];
        Assert.assertEquals(sum, dm.cost(1,3,6,8), 0.0);
    }
}
