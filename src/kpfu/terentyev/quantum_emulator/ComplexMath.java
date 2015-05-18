package kpfu.terentyev.quantum_emulator;

import java.util.ArrayList;
import java.util.Vector;
import java.util.regex.MatchResult;

/**
 * Created by alexandrterentyev on 25.03.15.
 */
public class ComplexMath {
    public static Complex[][] tensorMultiplication (Complex [][] firstMatrix, int firstMatrixHeight,
            int firstMatrixWidth, Complex[][] secondMatrix,int secondMatrixHeight, int secondMatrixWidth){
        Complex [][] result = new Complex[firstMatrixHeight*secondMatrixHeight][firstMatrixWidth*secondMatrixWidth];
        for (int iFirst = 0; iFirst< firstMatrixHeight; iFirst++){
            for (int jFirst=0; jFirst < firstMatrixWidth; jFirst++){
                for (int iSecond=0; iSecond<secondMatrixHeight; iSecond++){
                    for (int jSecond=0; jSecond<secondMatrixWidth; jSecond++){
                        result [iFirst*secondMatrixHeight+iSecond][jFirst*secondMatrixWidth+jSecond] =
                                Complex.mult(firstMatrix[iFirst][jFirst],secondMatrix[iSecond][jSecond]);
                    }
                }
            }
        }
        return result;
    }

    public static Complex[] multiplication (Complex [][] matrix, int size, Complex[] vector){
        Complex [] result = new Complex[size];
        for (int i=0; i<size;i++) {
            Complex sum = Complex.zero();
            for (int j=0; j<size; j++) {
                sum = Complex.add(sum, Complex.mult(matrix[i][j], vector[j]));
            }
            result[i]=sum;
        }
        return result;
        }
}
