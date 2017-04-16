package kpfu.terentyev.quantum.emulator;
import kpfu.terentyev.quantum.emulator.Complex;

import jcuda.Pointer;
import jcuda.Sizeof;
import jcuda.cuDoubleComplex;
import jcuda.jcublas.JCublas;
import sun.security.jca.JCAUtil;

import static jcuda.LogLevel.*;
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
            result [i] = vectorProduct(Complex.getRow(matrix, i), vector);
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

    public static cuDoubleComplex[][] multiplication(cuDoubleComplex[][] matrixA, int heightA, int widthA,
                                                     cuDoubleComplex [][] matrixB, int heightB, int widthB){


        JCublas.setLogLevel(LOG_ERROR);
        cuDoubleComplex [][]result = new cuDoubleComplex[heightA][widthB];
        for (int i=0; i<heightA; i++){
            for (int j=0; j<widthB; j++){
                cuDoubleComplex [] a = Complex.getRow(matrixA, i);
                cuDoubleComplex [] b = Complex.getColumn(matrixB, widthB, heightA, j);

                result [i][j] = vectorProduct(a, b);
            }
        }

        return result;
    }

    public static cuDoubleComplex vectorProduct (cuDoubleComplex[]a, cuDoubleComplex []b){

        JCublas.cublasInit();

        int size = Sizeof.DOUBLE;

        int length = a.length;
        int gpuArraySize =length*2;

        Pointer aOnGPU = new Pointer();
        Pointer bOnGPU = new Pointer();

        JCublas.cublasAlloc(gpuArraySize, size, aOnGPU);
        JCublas.cublasAlloc(gpuArraySize, size, bOnGPU);

        int offset = 0;

        JCublas.cublasSetVector(gpuArraySize, size, Pointer.to(Complex.complexToCudaComplex(a, length)),
                1,aOnGPU, 1);
        JCublas.cublasSetVector(gpuArraySize, size, Pointer.to(Complex.complexToCudaComplex(b, length)),
                1,bOnGPU, 1);


        cuDoubleComplex res = cublasZdotc(gpuArraySize, aOnGPU, 1, bOnGPU, 1);

        JCublas.cublasFree(aOnGPU);
        JCublas.cublasFree(bOnGPU);

        JCublas.cublasShutdown();

        return res;
    }

    public static cuDoubleComplex [][] zeroMatrix (int height, int width){
        cuDoubleComplex [] [] result = new cuDoubleComplex [height][width];
        for (int i=0 ; i<height; i++){
            for (int j = 0; j<width; j++){
                result [i][j] =