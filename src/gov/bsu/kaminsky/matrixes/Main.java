package gov.bsu.kaminsky.matrixes;


public class Main {
    public static void main(String[] args) {
        double[][] matrix = {{-1,1,-1,-2,0},{2,2,6,0,-4},{4,3,11,1,-7}};
        Matrix m = new Matrix(matrix);
        m.displayMatrix();
        double n = m.findMin();
        m.removeColumns(n);
        m.displayMatrix();
    }
}