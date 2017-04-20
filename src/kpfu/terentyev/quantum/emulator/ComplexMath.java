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

    public static cuDoubleComplex [][] multiplication (cuDoubleComplex [][] a, int aHeight, int aWidth,
                                                        cuDoubleComplex[][] b, int bHeight, int bWidth){
        JCublas.cublasInit();
        Pointer aOnGPU = new Pointer();
        Pointer bOnGPU = new Pointer();
        Pointer cOnGPU = new Pointer();

        int elementSize = Sizeof.DOUBLE;

        int space = 1;

        JCublas.cublasAlloc(aHeight*aWidth, elementSize, aOnGPU);
        JCublas.cublasAlloc(bHeight*bWidth*2, elementSize, bOnGPU);
        JCublas.cublasAlloc(aHeight*bWidth, elementSize*2, cOnGPU);

        double [][] aCuda = Complex.columnOrderedCudaComplex(a, aHeight, aWidth);
        double [][] bCuda = Complex.columnOrderedCudaComplex(b, bHeight, bWidth);

        JCublas.cublasSetMatrix(aHeight, aWidth, elementSize*2,
                Pointer.to(aCuda[0]), aHeight, aOnGPU,  aHeight);

        JCublas.cublasSetMatrix(bHeight, bWidth, elementSize*2,
                Pointer.to(bCuda[0]),  bHeight, bOnGPU,  bHeight);

        int cHeight = aHeight;
        int cWidth = bWidth;


        // http://peterwittek.com/cublas-matrix-c-style.html
        JCublas.cublasZgemm('n', 'n', aHeight, bWidth, bHeight, Complex.unit(), aOnGPU, aHeight, bOnGPU, bHeight,
                Complex.zero(), cOnGPU, aHeight);

        cuDoubleComplex [][] c = new cuDoubleComplex[cWidth][cHeight];
        JCublas.cublasGetMatrix(cHeight, cWidth, cOnGPU, cWidth, c[0],space, c.length);



        JCublas.cublasFree(aOnGPU);
        JCublas.cublasFree(bOnGPU);
        JCublas.cublasFree(cOnGPU);

        JCublas.cublasShutdown();

        return c;
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

        JCublas.cublasSetVector(length, a, 1, 1, aOnGPU, 8);
        JCublas.cublasSetVector(length, b, 1, 1, aOnGPU, 8);

//        JCublas.cublasSetVector(gpuArraySize, size, Pointer.to(Complex.complexToCudaComplex(a, length)),
//                1,aOnGPU, 1);
//        JCublas.cublasSetVector(gpuArraySize, size, Pointer.to(Complex.complexToCudaComplex(b, length)),
//                1,bOnGPU, 1);


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
                result [i][j] = Complex.zero();
            }
        }
        return result;
    }

    public  static cuDoubleComplex[][] squareMatricesMultiplication (cuDoubleComplex[][] matrixA, cuDoubleComplex [][] matrixB, int size){

        return multiplication(matrixA, size, size, matrixB, size, size);
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
