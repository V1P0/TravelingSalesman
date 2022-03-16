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
    public void saveTest() throws InterruptedException {
        DistanceMatrix dm = new DistanceMatrix(20, DistanceMatrix.types.ASYMMETRIC);
        dm.save("test.dm");
        DistanceMatrix dm2 = DistanceMatrix.load("test.dm");
        File file = new File("test.dm");
        Assert.assertNotNull(dm2);
        for(int i = 0; i<20; i++){
            for(int k = 0; k<20; k++){
                Assert.assertEquals(dm.matrix[i][k], dm2.matrix[i][k], 0.0);
            }
        }
        file.delete();
    }
}
