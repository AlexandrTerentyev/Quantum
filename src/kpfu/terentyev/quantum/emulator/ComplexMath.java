package kpfu.terentyev.quantum.emulator;
import jcuda.Pointer;
import jcuda.Sizeof;
import jcuda.cuComplex;
import jcuda.runtime.JCuda;

/**
 * Created by alexandrterentyev on 25.03.15.
 */
public class ComplexMath {

    public static cuComplex[][] tensorMultiplication (cuComplex [][] firstMatrix, int firstMatrixHeight,
            int firstMatrixWidth, cuComplex[][] secondMatrix,int secondMatrixHeight, int secondMatrixWidth){
        cuComplex [][] result = new cuComplex[firstMatrixHeight*secondMatrixHeight][firstMatrixWidth*secondMatrixWidth];
        for (int iFirst = 0; iFirst< firstMatrixHeight; iFirst++){
            for (int jFirst=0; jFirst < firstMatrixWidth; jFirst++){
                for (int iSecond=0; iSecond<secondMatrixHeight; iSecond++){
                    for (int jSecond=0; jSecond<secondMatrixWidth; jSecond++){
                        result [iFirst*secondMatrixHeight+iSecond][jFirst*secondMatrixWidth+jSecond] =
                                cuComplex.cuCmul(firstMatrix[iFirst][jFirst],secondMatrix[iSecond][jSecond]);
                    }
                }
            }
        }

        return result;
    }

    public static cuComplex[][] multiplication (cuComplex a, cuComplex [][] matrix, int size){
        cuComplex [][] result = new cuComplex[size][size];
        for (int i=0; i<size;i++) {
            for (int j=0; j<size; j++) {
                result [i][j] = cuComplex.cuCmul(a, matrix[i][j]);
            }
        }
        return result;
    }

    public static cuComplex[] multiplication (cuComplex [][] matrix, int size, cuComplex[] vector){
        cuComplex [] result = new cuComplex[size];
        for (int i=0; i<size;i++) {
            cuComplex sum = Complex.zero();
            for (int j=0; j<size; j++) {
                sum = cuComplex.cuCadd(sum, cuComplex.cuCmul(matrix[i][j], vector[j]));
            }
            result[i]=sum;
        }
        return result;
    }

    public static cuComplex[] tensorMultiplication(cuComplex[] a, cuComplex[] b){
        cuComplex [] result = new cuComplex[a.length*b.length];
        for (int i=0; i< a.length; i++){
            for (int j=0; j<b.length; j++){
                result[i*b.length+j] = cuComplex.cuCmul(a[i], b[j]);
            }
        }
        return result;
    }

    public static cuComplex[][] ketBraTensorMultiplication (cuComplex[] ket, cuComplex[] bra){
        cuComplex [][] result = new cuComplex[ket.length][bra.length];

        for (int i = 0; i < ket.length; i++){
            for (int j = 0; j < bra.length; j++){
                result [i][j] = cuComplex.cuCmul(ket[i], bra[j]);
            }
        }

        return result;
    }

    public  static cuComplex[][] multiplication(cuComplex[][] matrixA, int heightA, int widthA,
                                                cuComplex [][] matrixB, int heightB, int widthB){
        cuComplex [][]result = new cuComplex[heightA][widthB];
        for (int i=0; i<heightA; i++){
            for (int j=0; j<widthB; j++){
                cuComplex sum = Complex.zero();
                for (int z=0; z<widthA; z++){
                    sum = cuComplex.cuCadd(sum, cuComplex.cuCmul(matrixA[i][z],matrixB[z][j]));
                }
                result [i][j]=sum;
            }
        }


        float [][] floatA = Complex.complexMatrToCudaFloat(matrixA, heightA, widthA);
        float [][] floatB = Complex.complexMatrToCudaFloat(matrixB, heightB, widthB);

        

        return result;
    }

    public static cuComplex [][] zeroMatrix (int height, int width){
        cuComplex [] [] result = new cuComplex [height][width];
        for (int i=0 ; i<height; i++){
            for (int j = 0; j<width; j++){
                result [i][j] = Complex.zero();
            }
        }
        return result;
    }

    public  static cuComplex[][] squareMatricesMultiplication (cuComplex[][] matrixA, cuComplex [][] matrixB, int size){
        cuComplex [][]result = new cuComplex[size][size];
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                cuComplex sum = Complex.zero();
                for (int z=0; z<size; z++){
                    sum = cuComplex.cuCadd(sum, cuComplex.cuCmul(matrixA[i][z],matrixB[z][j]));
                }
                result [i][j]=sum;
            }
        }
        return result;
    }
    public static  cuComplex[][] hermitianTransposeForMatrix (cuComplex[][] matrix, int height, int width){
        cuComplex [][] result = new cuComplex[width][height];
        for (int i=0; i<height; i++)
            for (int j=0; j<width; j++)
                result[j][i] = cuComplex.cuConj(matrix[i][j]);
        return result;
    }

    public static cuComplex trace (cuComplex[][] matrix, int size){
        cuComplex result = Complex.zero();
        for (int i=0; i < size; i++){
            result = cuComplex.cuCadd(result, matrix[i][i]);
        }

        return result;
    }
}
