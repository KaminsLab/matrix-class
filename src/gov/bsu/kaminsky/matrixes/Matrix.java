package gov.bsu.kaminsky.matrixes;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Matrix {

    private static final Logger LOGGER = LogManager.getLogger(Matrix.class);

    public static final int MAX = 256;
    public static final int MIN = 0;

    private double[][] matrix;
    private int rows, columns;

    Matrix(int rows, int columns) {
        setRows(rows);
        setColumns(columns);
        randomMatrix();
    }

    Matrix(double[][] matrix) {
        setRows(matrix.length);
        setColumns(matrix[0].length);
        setMatrix(matrix);
    }

    Matrix(Matrix other){
        if (other == null)
            throw new NullPointerException("Matrix object points to null.");

        setRows(other.getRows());
        setColumns(other.getColumns());
        setMatrix(other.getMatrix());
    }

    private void setRows(int rows) {
        if (rows < 1)
            throw new IllegalArgumentException("Argument rows must be more zero.");
        LOGGER.log(Level.DEBUG, "Argument \"rows\" = " + rows);
        this.rows = rows;
    }

    private void setColumns(int columns) {
        if (columns < 1)
            throw new IllegalArgumentException("Argument columns must be more zero.");
        LOGGER.log(Level.DEBUG, "Argument \"columns\" = " + columns);
        this.columns = columns;
    }

    private void randomMatrix() {
        matrix = new double[getRows()][getColumns()];

        for (int i = 0; i < getRows(); i++)
            for (int j = 0; j < getColumns(); j++)
                matrix[i][j] = MIN + (MAX * Math.random());
            LOGGER.log(Level.INFO,"Generated matrix: " + this);
    }

    private void setMatrix(double[][] matrix) {
        LOGGER.log(Level.TRACE, "Loading of method \"setMatrix\".");

        if (matrix == null)
            throw new NullPointerException("Matrix points to null.");

        if(getRows() <= 0)
            throw new IllegalArgumentException("Value of the field \"rows\" must be more zero.");
        LOGGER.log(Level.DEBUG,"Field \"rows\" = " + rows);

        if(getColumns() <= 0)
            throw new IllegalArgumentException("Value of the field \"columns\" must be more zero.");
        LOGGER.log(Level.DEBUG,"Field \"columns\" = " + columns);

        this.matrix = new double[getRows()][getColumns()];
        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix[i].length; j++)
                this.matrix[i][j] = matrix[i][j];
            LOGGER.log(Level.INFO, "Setted matrix:\n" + this);
    }

    public void setElement(int i, int j, double value){
        if (i > getRows())
            throw new ArrayIndexOutOfBoundsException("Invalid index of row.");
        LOGGER.log(Level.DEBUG, "Argument \"i\" = " + i);
        if (j > getColumns())
            throw new ArrayIndexOutOfBoundsException("Invalid index of column.");
        LOGGER.log(Level.DEBUG, "Argument \"j\" = " + j);
        if(value<MIN&&value>MAX)
            throw new IllegalArgumentException("Argument \"value\" = "+ value+" must be in the range of [MIN,MAX].");
        LOGGER.log(Level.DEBUG, "Argument \"value\" = " + value);
        matrix[i][j] = value;
    }

    public void setElement(double value){
        if(value < MIN && value > MAX)
            throw new IllegalArgumentException("Argument \"value\" = "+ value+" must be in the range of [MIN,MAX].");

        LOGGER.log(Level.DEBUG, "Argument \"value\" = " + value);

        for (int i = 0; i<getRows(); i++)
            for (int j = 0; j<getColumns(); j++)
                setElement(i,j,value);
    }

    public final int getRows() {
        return rows;
    }

    public final int getColumns() {
        return columns;
    }

    public final double[][] getMatrix() {
        if (matrix==null)
            throw new NullPointerException("Reference to field equals null.");
        if (getRows() <= 0)
            throw new IllegalArgumentException("Value of the \"rows\" = " + getRows() + " must be more zero.");
        if (getColumns() <= 0)
            throw new IllegalArgumentException("Value of the \"columns\" = " + getColumns() + " must be more zero.");

        double[][] matrix = new double[getRows()][getColumns()];

        for (int i = 0; i < getRows(); i++)
            for (int j = 0; j < getColumns(); j++)
                matrix[i][j] = this.matrix[i][j];

        return matrix;
    }

    public final double getElement(int i, int j){
        if (matrix == null)
            throw new NullPointerException("Matrix points to null.");

        if (i > getRows())
            throw new ArrayIndexOutOfBoundsException("Invalid index of row.");

        LOGGER.log(Level.DEBUG, "Argument \"i\" = " + i);

        if (j > getColumns())
            throw new ArrayIndexOutOfBoundsException("Invalid index of column.");

        LOGGER.log(Level.DEBUG, "Argument \"j\" = " + j);

        LOGGER.log(Level.INFO, "Element [i][j] = " + matrix[i][j]);

        return matrix[i][j];
    }

    public void displayMatrix() {
        validation();
        for (double[] i : matrix) {
            for (double j : i)
                System.out.print(j + " ");
            System.out.println();
        }
    }

    public Matrix add(Matrix matrixA) {
        LOGGER.log(Level.TRACE, "Method \"add\" started work");

        if (matrixA == null)
            throw new NullPointerException("Matrix points to null.");

        if (matrixA.getRows() != getRows())
            throw new IllegalArgumentException("Count of rows must be the same for addition.");
        if (matrixA.getColumns() != getColumns())
            throw new IllegalArgumentException("Count of columns must be the same for addition.");

        double[][] matrixB = new double[getRows()][getColumns()];

        for (int i = 0; i < getRows(); i++)
            for (int j = 0; j < getColumns(); j++)
                matrixB[i][j] = matrix[i][j] + matrixA.matrix[i][j];
        return new Matrix(matrixB);
    }

    public  Matrix subtract(Matrix matrixA) {
        LOGGER.log(Level.TRACE, "Method \"subtract\" started work");

        if (matrixA == null)
            throw new NullPointerException("Matrix points to null.");

        if (matrixA.getRows() != getRows())
            throw new IllegalArgumentException("Count of rows must be the same for subtraction.");
        if (matrixA.getColumns() != getColumns())
            throw new IllegalArgumentException("Count of columns must be the same for subtraction.");

        double[][] matrixB = new double[getRows()][getColumns()];

        for (int i = 0; i < getRows(); i++)
            for (int j = 0; j < getColumns(); j++)
                matrixB[i][j] = matrix[i][j] - matrixA.matrix[i][j];
        return new Matrix(matrixB);
    }

    public static Matrix multiply(Matrix matrixA, Matrix matrixB) {
        if (matrixA == null)
            throw new NullPointerException("Lhs matrix points to null.");

        if (matrixB == null)
            throw new NullPointerException("Rhs matrix points to null.");

        LOGGER.log(Level.TRACE, "Method \"multiply\" started work.");

        if (matrixA.getColumns() != matrixB.getRows())
            throw new IllegalArgumentException("Matrix cannot be multiplied by other.");

        double[][] matrixC = new double[matrixA.getRows()][matrixB.getColumns()];

        for (int i = 0; i < matrixA.getRows(); i++)
            for (int j = 0; j < matrixA.getColumns(); j++)
                for (int k = 0; k < matrixA.getColumns(); k++)
                    matrixC[i][j] += matrixA.matrix[i][k] * matrixB.matrix[k][j];
        return new Matrix(matrixC);
    }

    public int norm() {
        LOGGER.log(Level.TRACE, "Method \"norm\" started work");
        validation();
        int n = getRows(), m = getColumns();
        int norm = 0, temp = 0;

        for (int i = 0; i < m; i++)
            temp += matrix[0][i];
        norm = temp;

        for (int i = 1; i < n; i++) {
            temp = 0;
            for (int j = 0; j < m; j++)
                temp += matrix[i][j];
            if (temp < norm)
                norm = temp;
        }
        LOGGER.log(Level.INFO, "Norm of matrix = " + norm);
        return norm;
    }

    public void transpose() {
        LOGGER.log(Level.TRACE,"Method \"transpose\" started work.");
        validation();
        if (getRows() != getColumns())
            throw new IllegalArgumentException("Non-quadratic matrix cannot be transposed.");

        int n = getRows();
        double temp;
        for (int i = 0; i < n; i++)
            for (int j = i; j < n; j++) {
                temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        LOGGER.log(Level.INFO, "Transposed matrix:\n" + this);
    }

    public double determinant() {
        LOGGER.log(Level.TRACE,"Method \"transpose\" started work.");
        validation();
        if (getRows() != getColumns())
            throw new IllegalArgumentException("Determinant of non-quadratic matrix cannot be calculated.");

        int n = getRows();

        switch (n) {
            case 1: {
                return matrix[0][0];
            }
            case 2: {
                return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
            }
            default: {
                if (n < 1)
                    throw new IllegalArgumentException("Argument rows must be more zero.");
                double det = 0;
                int power = 1;
                for (int i = 0; i < n; i++) {
                    Matrix compressed = remove(i, 0);
                    det = det + power * matrix[i][0] * compressed.determinant();
                    power *= -1;
                }
                return det;
            }
        }
    }

    public Matrix remove(int iRow, int jColumn) {
        int n = getRows(), m = getColumns();
        if (iRow > n)
            throw new ArrayIndexOutOfBoundsException("Invalid index of row.");
        LOGGER.log(Level.DEBUG, "Argument \"iRow\" = " + iRow);
        if (jColumn > m)
            throw new ArrayIndexOutOfBoundsException("Invalid index of column.");
        LOGGER.log(Level.DEBUG, "Argument \"jColumn\" = " + jColumn);

        double[][] compressed = new double[n - 1][m - 1];

        for (int i = 0, row = 0; i < n; i++) {
            if (i != iRow) {
                for (int j = 0, column = 0; j < m; j++) {
                    if (j != jColumn) {
                        compressed[row][column] = matrix[i][j];
                        column++;
                    }
                }
                row++;
            }
        }
        return new Matrix(compressed);
    }

    public double averageOfRow(int iRow) {
        if (iRow < 0 || iRow >= getRows())
            throw new ArrayIndexOutOfBoundsException("Invalid index of row.");
        LOGGER.log(Level.DEBUG, "Argument \"iRow\" = " + iRow);

        double sum = 0;
        for (int j = 0; j < getColumns(); j++)
            sum += matrix[iRow][j];
        LOGGER.log(Level.INFO, "Sum elements of row = " + sum);

        return sum / getColumns();
    }

    public void subtractAverage() {
        LOGGER.log(Level.TRACE,"Method \"subtractAverage\" started work.");
        validation();
        double average;
        for (int i = 0; i < getRows(); i++) {
            average = averageOfRow(i);
            for (int j = 0; j < getColumns(); j++)
                matrix[i][j] -= average;
            LOGGER.log(Level.INFO, "Matrix after subtracting average:\n" + this);
        }
    }

    private double[][] removeColumn(int jColumn) {
        int n = getRows(), m = getColumns();
        if (jColumn > m)
            throw new ArrayIndexOutOfBoundsException("Invalid index of column.");
        LOGGER.log(Level.DEBUG, "Argument \"jColumn\" = " + jColumn);

        double[][] compressed = new double[n][m - 1];

        for (int i = 0; i < n; i++)
            for (int j = 0, column = 0; j < m; j++)
                if (j != jColumn) {
                    compressed[i][column] = matrix[i][j];
                    column++;
                }

        return compressed;
    }

    public double findMin() {
        LOGGER.log(Level.TRACE,"Method \"findMin\" started work.");
        validation();
        double min = matrix[0][0];
        for (int i = 0; i < getRows(); i++)
            for (int j = 1; j < getColumns(); j++)
                if (matrix[i][j] < min)
                    min = matrix[i][j];
                LOGGER.log(Level.INFO, "Minimal element of matrix = " + min);
        return min;
    }

    public double findMax(int column){
        int n = getRows(), m = getColumns();
        if(column<0 || column>=getColumns())
            throw new ArrayIndexOutOfBoundsException("Invalid index of column = " + column + ".");
        LOGGER.log(Level.DEBUG, "Argument \"column\" = " + column);
        double max = matrix[0][column];
        for (int i = 0; i<n; i++)
          if(max<matrix[i][column])
              max = matrix[i][column];
          LOGGER.log(Level.INFO,"Max of column" + column +" = " + max);
          return max;
    }

    public void removeColumns(double element) {
        LOGGER.log(Level.TRACE,"Method \"removeColumns\" started work.");
        validation();
        LOGGER.log(Level.DEBUG, "Argument element = " + element);

        int n = getRows(), m = getColumns();
        double[][] compressed = getMatrix();

        int columns = 0;
        boolean exist = false;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                if (matrix[i][j] == element) {
                    compressed = removeColumn(j);
                    setColumns(--m);
                    j--;
                    setMatrix(compressed);
                    exist = true;
                }
        if (!exist)
            throw new IllegalArgumentException("There is no number " + element + "in the matrix.");

        LOGGER.log(Level.INFO, "Compressed matrix:\n" + this);
    }

    public void selectionSort(int column) {
        LOGGER.log(Level.TRACE,"Method \"selectionSort\" started work.");
        validation();
        int n = getRows();
        if (column < 0 || column >= getColumns())
            throw new ArrayIndexOutOfBoundsException("Illegal index of column.");
        LOGGER.log(Level.DEBUG,"Argument column = " + column);

        for (int i = 0; i < n; i++) {
            double min = matrix[i][column];
            int position = i;
            for (int j = i + 1; j < n; j++) {
                if (matrix[j][column] > min) {
                    min = matrix[j][column];
                    position = j;
                }
            }
            double[] temp = matrix[i];
            matrix[i] = matrix[position];
            matrix[position] = temp;
        }
        LOGGER.log(Level.INFO, "Sorted matrix:\n" + this);
    }

    public Matrix turnOnSide() {
        LOGGER.log(Level.TRACE,"Method \"turnOnSide\" started work.");
        validation();
        int n = getRows(), m = getColumns();
        double[][] matrix = new double[m][n];
        for (int i = 0, k = m - 1; i < m; i++, k--)
            for (int j = 0; j < n; j++)
                matrix[i][j] = this.matrix[j][k];

            Matrix turned = new Matrix(matrix);

        LOGGER.log(Level.INFO, "Turned matrix:\n" + turned);
        return turned;
    }

    public int increaseSequence(){
        LOGGER.log(Level.TRACE,"Method \"increaseSequence\" started work.");
        validation();
        int increasing = 0, temp = 0;
        for (int i = 0; i<getRows(); i++)
            for (int j = 0; j<getColumns(); j++) {
                if (matrix[i][j] < matrix[i][j + 1]) {
                    temp++;
                    continue;
                }
                if (increasing < temp)
                    increasing = temp;
                temp = 0;
            }
        LOGGER.log(Level.INFO, "Count of increasing elements = " + increasing);
        return increasing;
    }

    public void steppedView(){
        LOGGER.log(Level.TRACE,"Method \"steppedView\" started work.");
        validation();

        int n = getRows(), m = getColumns();
        double max = 0.0;

        int count = 0;
        while(max==0.0){
            max = findMax(count);
            count++;
        }

        if(count == 0)
            setElement(0.0);
        else count--;


        for (int i = 0; i<n; i++)
                for (int j= 0; j<m; j++)
                    if(matrix[i][j]==max && j==count){
                        swap(i,0);
                        break;
                    }
            LOGGER.log(Level.INFO,"Matrix after swap of rows:\n" + this);
        for (int j = 0; j<m; j++)
            matrix[0][j] /= matrix[0][count];

        for (int k = 0; k<n-1; k++)
        for (int i = k+1; i<n;i++)
            for (int j = count;j<m;j++) {
                matrix[i][j] -= matrix[k][j] * matrix[i][k];
                if(matrix[i][k+1]==0.0)
                    swap(i,i+1);
                    matrix[i][j] /= matrix[i][k+1];
            }
        LOGGER.log(Level.INFO, "Stepped view of matrix:\n" + this);
    }

    public int rank(){
        LOGGER.log(Level.TRACE,"Method \"rank\" started work.");
        int rank = 0;
        steppedView();

        for (int i = 0; i<getRows(); i++)
            for (int j = 0; j<getColumns(); j++)
                if(matrix[i][j]!=0){
                    rank++;
                    break;
                }

        LOGGER.log(Level.INFO, "Rank of the matrix = " + rank);

        return rank;
    }

    public double trace(){
        LOGGER.log(Level.TRACE,"Method \"transpose\" started work.");

        validation();

        int trace = 0;
        for (int i = 0; i<getRows()||i<getColumns();i++)
            trace+=matrix[i][i];

        LOGGER.log(Level.INFO, "Trace of matrix = " + trace);

        return trace;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result;

        if(matrix==null)
            result = 0;
        else{
        double doubleBuf = 1;

        for (double[] i : matrix)
            for (double j : i)
                doubleBuf += j;
            LOGGER.log(Level.INFO, "Sum of elements = " + doubleBuf);
            if(doubleBuf-(int)doubleBuf==0) {
                result = (int) doubleBuf;
                LOGGER.log(Level.INFO, "Reinterpret cast of sum = " + result);
            }
            else{
        long longBuf = Double.doubleToLongBits(doubleBuf);
        result = (int)(longBuf - (longBuf >>> 32));
        LOGGER.log(Level.INFO, "Sum taking into account the fault = " + result);
            }

        }
        result = prime * result + getRows() + getColumns();
        LOGGER.log(Level.INFO, "Hash-code of Matrix = " + result);
        return result;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null || getClass() != object.getClass())
            return false;
        Matrix matrix = (Matrix) object;
        return getRows() == matrix.getRows()
                && getColumns() == matrix.getColumns() &&
                hashCode()==matrix.hashCode();
    }

    @Override
    public String toString(){
        String s = new String("Matrix "+getRows()+"x"+getColumns()+":\n");
        for (int i = 0; i<getRows(); i++){
            for (int j = 0; j<getColumns(); j++)
                s = String.join(" ", s, String.valueOf(matrix[i][j]));
            s = s +"\n";
        }
        return s;
    }

    private void validation(){
        if (matrix==null)
            throw new NullPointerException("Reference to field equals null.");
        if (getRows() < 0)
            throw new IllegalArgumentException("Value of the field must be more zero.");
        LOGGER.log(Level.DEBUG,"field \"rows\" = " + rows);
        if (getColumns() < 0)
            throw new IllegalArgumentException("Value of the field must be more zero.");
        LOGGER.log(Level.DEBUG,"field \"columns\" = " + columns);

    }

    void swap(int i, int j){
        double[] temp = matrix[j];
        matrix [j] = matrix[i];
        matrix[i] = temp;
    }
}