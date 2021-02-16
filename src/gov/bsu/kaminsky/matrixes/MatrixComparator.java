package gov.bsu.kaminsky.matrixes;

import java.util.Comparator;

public class MatrixComparator implements Comparator<Matrix> {
    @Override
    public int compare(Matrix m1, Matrix m2){
        if(m1.equals(m2))
            return 0;
        else if(m1.hashCode()-m2.hashCode()>0)
            return 1;
        else
            return -1;
    }
}
