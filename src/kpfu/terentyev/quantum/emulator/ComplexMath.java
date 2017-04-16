package kpfu.terentyev.quantum.emulator;
import jcuda.Pointer;
import jcuda.Sizeof;
import jcuda.cuDoubleComplex;

import static jcuda.jcublas.JCublas.cublasZdotc;

/**
 * Created by alexandrterentyev on 25.03.15.
 */
public class ComplexMath {

    public static cuDoubleComplex[][] tensorMultiplication (cuDoubleComplex [][] firstMatrix, int firstMatrixHeight,
            int firstMatrixWidth, cuDoubleComplex[][] secondMatrix,int secondMatrixHeight, int secondMatrixWidth){
        cuDoubleComplex [][] result = new cuDoubleComplex[firstMatrixHeight*secondMatrixHeight][firstMatrixWidth*secondMatrixWidth];
        for (int iFirst = 0; iFirst< firstMatrixHeight; iFirst++){
            for (int jFirst=0; jFirst < firstMatrixWidth; jFirst++){
                for (int iSecond=0; iSecond<secondMatrixHeight; iSecond++){
                    for (int jSecond=0; jSecond<secondMatrixWidth; jSecond++){
                        result [iFirst*secondMatrixHeight+iSecond][jFirst*secondMatrixWidth+jSecond] =
                                cuDoubleComplex.cuCmul(firstMatrix[iFirst][jFirst],secondMatrix[iSecond][jSecond]);
                    }
                }
            }
        }

        return result;
    }

    public static cuDoubleComplex[][] multiplication (cuDoubleComplex a, cuDoubleComplex [][] matrix, int size){
        cuDoubleComplex [][] result = new cuDoubleComplex[size][size];
        for (int i=0; i<size;i++) {
            for (int j=0; j<size; j++) {
                result [i][j] = cuDoubleComplex.cuCmul(a, matrix[i][j]);
            }
        }
        return result;
    }

    public static cuDoubleComplex[] multiplication (cuDoubleComplex [][] matrix, int size, cuDoubleComplex[] vector){
        cuDoubleComplex [] result = new cuDoubleComplex[size];
        for (int i=0; i<size;i++) {
            cuDoubleComplex sum = Complex.zero();
            for (int j=0; j<size; j++) {
                sum = cuDoubleComplex.cuCadd(sum, cuDoubleComplex.cuCmul(matrix[i][j], vector[j]));
            }
            result[i]=sum;
        }
        return result;
    }

    public static cuDoubleComplex[] tensorMultiplication(cuDoubleComplex[] a, cuDoubleComplex[] b){
        cuDoubleComplex [] result = new cuDoubleComplex[a.length*b.length];
        for (int i=0; i< a.length; i++){
            for (int j=0; j<b.length; j++){
                result[i*b.length+j] = cuDoubleComplex.cuCmul(a[i], b[j]);
            }
        }
        return result;
    }

    public static cuDoubleComplex[][] ketBraTensorMultiplication (cuDoubleComplex[] ket, cuDoubleComplex[] bra){
        cuDoubleComplex [][] result = new cuDoubleComplex[ket.length][bra.length];

        for (int i = 0; i < ket.length; i++){
            for (int j = 0; j < bra.length; j++){
                result [i][j] = cuDoubleComplex.cuCmul(ket[i], bra[j]);
            }
        }

        return result;
    }

    public  static cuDoubleComplex[][] multiplication(cuDoubleComplex[][] matrixA, int heightA, int widthA,
                                                cuDoubleComplex [][] matrixB, int heightB, int widthB){
        cuDoubleComplex [][]result = new cuDoubleComplex[heightA][widthB];
        for (int i=0; i<heightA; i++){
            for (int j=0; j<widthB; j++){
                cuDoubleComplex sum = Complex.zero();

                double [] a = Complex.complexToCudaComplex(Complex.getRow(matrixA, i), widthA);
                double [] b = Complex.complexToCudaComplex(Complex.getColumn(matrixB, widthB, heightA, j), widthA);

                int storageSpacing = Sizeof.DOUBLE;

                result [i][j] = cublasZdotc(widthA, Pointer.to(a), storageSpacing, Pointer.to(b), storageSpacing);

                for (int z=0; z<widthA; z++){
                    sum = cuDoubleComplex.cuCadd(sum, cuDoubleComplex.cuCmul(matrixA[i][z],matrixB[z][j]));
                }
                result [i][j]=sum;
            }
        }


        double [][] floatA = Complex.complexMatrToCudaDouble(matrixA, heightA, widthA);
        double [][] floatB = Complex.complexMatrToCudaDouble(matrixB, heightB, widthB);



        return result;
    }

    public static cuDoubleComplex [][] zeroMatrix (int height, int width){
        cuDoubleComplex [] [] result = new cuDoubleComplex [height][width];
        for (int i=0 ; i<height; i++){
            for (int j = 0; j<width; j++){
                result [i][j] = Complex.zero();
            }
        }
        return result;
    }

    public  static cuDoubleComplex[][] squareMatricesMultiplication (cuDoubleComplex[][] matrixA, cuDoubleComplex [][] matrixB, int size){
        cuDoubleComplex [][]result = new cuDoubleComplex[size][size];
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                cuDoubleComplex sum = Complex.zero();
                for (int z=0; z<size; z++){
                    sum = cuDoubleComplex.cuCadd(sum, cuDoubleComplex.cuCmul(matrixA[i][z],matrixB[z][j]));
                }
                result [i][j]=sum;
            }
        }
        return result;
    }
    public static  cuDoubleComplex[][] hermitianTransposeForMatrix (cuDoubleComplex[][] matrix, int height, int width){
        cuDoubleComplex [][] result = new cuDoubleComplex[width][height];
        for (int i=0; i<height; i++)
            for (int j=0; j<width; j++)
                result[j][i] = cuDoubleComplex.cuConj(matrix[i][j]);
        return result;
    }

    public static cuDoubleComplex trace (cuDoubleComplex[][] matrix, int size){
        cuDoubleComplex result = Complex.zero();
        for (int i=0; i < size; i++){
            result = cuDoubleComplex.cuCadd(result, matrix[i][i]);
        }

        return result;
    }
}
