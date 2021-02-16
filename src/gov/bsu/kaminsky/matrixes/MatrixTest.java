package gov.bsu.kaminsky.matrixes;

import org.testng.Assert;
import org.testng.annotations.*;

public class MatrixTest {

    @Test
    public void addTest(){
        double[][] matrix1 = {{-3,25,12,4},{67,12,0,23},{74,19,42,1}};
        Matrix matrixA = new Matrix(matrix1);
        double[][] matrix2 = {{34,2,77,11},{0,16,22,54},{10,3,13,86}};
        Matrix matrixB = new Matrix(matrix2);
        boolean same = true;
        Assert.assertEquals(matrixA,matrixB);
    }
}
