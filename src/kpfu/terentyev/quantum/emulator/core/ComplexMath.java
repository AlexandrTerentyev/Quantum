package kpfu.terentyev.quantum.emulator.core;
import jcuda.Pointer;
import jcuda.Sizeof;
import jcuda.cuDoubleComplex;
import jcuda.jcublas.JCublas;
import jcuda.runtime.JCuda;

import static jcuda.jcublas.JCublas.cublasZdotc;
import static jcuda.jcublas.cublasStatus.CUBLAS_STATUS_NOT_INITIALIZED;

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

    public static cuDoubleComplex [][] cpuMultiplication (cuDoubleComplex [][] a, int aHeight, int aWidth,
                                                       cuDoubleComplex[][] b, int bHeight, int bWidth) {
        cuDoubleComplex [][] res = new cuDoubleComplex[aHeight][bWidth];

        for (int i = 0; i < aHeight; i++){
            for (int j = 0; j < bWidth; j++){
                cuDoubleComplex resIJ = Complex.zero();

                for (int z = 0; z < aWidth; z ++){
                    resIJ = cuDoubleComplex.cuCadd(resIJ, cuDoubleComplex.cuCmul(a[i][z], b[z][j]));
                }

                res[i][j] = resIJ;
            }
        }

        return res;
    }

    public static cuDoubleComplex [][] multiplication (cuDoubleComplex [][] a, int aHeight, int aWidth,
                                                        cuDoubleComplex[][] b, int bHeight, int bWidth){

        if (JCublas.cublasInit() == CUBLAS_STATUS_NOT_INITIALIZED){
            return cpuMultiplication(a, aHeight, aWidth, b, bHeight, bWidth);
        }

        JCublas.setExceptionsEnabled(true);

        int elementSize = Sizeof.DOUBLE * 2;

        Pointer aOnGPU = new Pointer();
        Pointer bOnGPU = new Pointer();
        Pointer cOnGPU = new Pointer();

        int cHeight = aHeight;
        int cWidth = bWidth;


        int lda = aHeight;
        int ldb = bHeight;
        int ldc = cHeight;



        int offset = 0;

        JCublas.cublasAlloc(aHeight*aWidth, elementSize, aOnGPU);
        JCublas.cublasAlloc(bHeight*bWidth, elementSize, bOnGPU);
        JCublas.cublasAlloc(cHeight*cWidth, elementSize, cOnGPU);

//        double [][] aCuda = Complex.complexMatrToCudaDouble(a, aHeight, aWidth);
//        double [][] bCuda = Complex.complexMatrToCudaDouble(b, bHeight, bWidth);

//        JCublas.cublasSetMatrix(aHeight, aWidth, elementSize,
//                Pointer.to(aCuda[0]), aHeight, aOnGPU,  aHeight);
//
//        JCublas.cublasSetMatrix(bHeight, bWidth, elementSize*2,
//                Pointer.to(bCuda[0]),  bHeight, bOnGPU,  bHeight);



        JCublas.cublasSetMatrix(aHeight, aWidth, Complex.matrixToArray(a, aHeight, aWidth), offset, lda, aOnGPU, lda);
        JCublas.cublasSetMatrix(bHeight, bWidth, Complex.matrixToArray(b, bHeight, bWidth), offset, ldb, bOnGPU, ldb);


        // http://peterwittek.com/cublas-matrix-c-style.html
        JCublas.cublasZgemm('n', 'n', aHeight, bWidth, bHeight, Complex.unit(), aOnGPU, lda, bOnGPU, ldb,
                Complex.zero(), cOnGPU, ldc);

        cuDoubleComplex [] c = new cuDoubleComplex[cHeight*cWidth];

        JCuda.cudaDeviceSynchronize();

        double [] doubleC = new double[cHeight*cWidth*2];
        JCublas.cublasGetMatrix(cHeight, cWidth*2, elementSize/2, cOnGPU, ldc, Pointer.to(doubleC), ldc);

        cuDoubleComplex [][] res = new cuDoubleComplex[cWidth][cHeight];

        for (int i = 0; i< cHeight; i++){
            for (int j = 0; j < cWidth; j++){
                res [i][j] = cuDoubleComplex.cuCmplx(doubleC[i * cWidth * 2 + 2 * j], doubleC[i * cWidth * 2 + 2 * j + 1]);
            }
        }

//        JCublas.cublasGetMatrix(cHeight, cWidth, cOnGPU, ldc, c,offset, ldc);



        JCublas.cublasFree(aOnGPU);
        JCublas.cublasFree(bOnGPU);
        JCublas.cublasFree(cOnGPU);

        JCublas.cublasShutdown();


        return res;
    }


    public static cuDoubleComplex cpuVectorProduct (cuDoubleComplex[]a, cuDoubleComplex []b){
        cuDoubleComplex res = Complex.zero();

        for (int i =0; i < a.length; i++){
            res = cuDoubleComplex.cuCadd(res, cuDoubleComplex.cuCmul(a[i], b[i]));
        }

        return  res;
    }

    public static cuDoubleComplex vectorProduct (cuDoubleComplex[]a, cuDoubleComplex []b){

        if (JCublas.cublasInit() == CUBLAS_STATUS_NOT_INITIALIZED){
            return cpuVectorProduct(a, b);
        }

        int size = Sizeof.DOUBLE * 2;

        int length = a.length;

        Pointer aOnGPU = new Pointer();
        Pointer bOnGPU = new Pointer();

        JCublas.cublasAlloc(length, size, aOnGPU);
        JCublas.cublasAlloc(length, size, bOnGPU);

        int offset = 0;

        int inc = 1;


        JCublas.cublasSetVector(length, a, offset, inc, aOnGPU, inc);
        JCublas.cublasSetVector(length, b, offset, inc, bOnGPU, inc);

        cuDoubleComplex res = cublasZdotc(length, aOnGPU, inc, bOnGPU, inc);

//        double [] test = new double[length*2];
//
//        JCublas.cublasGetVector(length*2, size/2, aOnGPU, inc, Pointer.to(test), inc);

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
            for (int j=0; j<width; j++) {
                result[j][i] = cuDoubleComplex.cuConj(matrix[i][j]);
            }
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
