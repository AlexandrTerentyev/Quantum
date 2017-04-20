package kpfu.terentyev.quantum.emulator;

import jcuda.cuDoubleComplex;

/**
 * Created by aleksandrterentev on 14.04.17.
 */
public class Complex {


    public static cuDoubleComplex complex (double real, double imagine){
        return cuDoubleComplex.cuCmplx(real, imagine);
    }

    public static cuDoubleComplex zero () {
        return cuDoubleComplex.cuCmplx(0, 0);
    }

    public static cuDoubleComplex unit (){
        return cuDoubleComplex.cuCmplx(1, 0);
    }

    public static double [][] complexToCudaComplex (cuDoubleComplex[][] a, int height, int width){
        double [][] result = new double [height][width*2];
        for (int i = 0; i<height; i++){
            result [i] = complexToCudaComplex(a[i], width);
        }

        return result;
    }

    public static double [][] columnOrderedCudaComplex (cuDoubleComplex[][] a, int height, int width){
        double [][] result = new double [width][height*2];
        for (int i = 0; i<height; i++){
            for (int j=0; j <width; j++ ){
                result [j][i] = a[i][j].x;
                result [j][i+1] = a [i][j].y;
            }
        }

        return result;
    }

    public  static  double[] complexToCudaComplex (cuDoubleComplex[] array, int size){
        double [] result = new double[size*2];

        for (int i=0; i< size; i++){
            result [i*2] = array [i].x;
            result [i*2 + 1] = array[i].y;
        }

        return result;
    }

    public static double [][] complexMatrToCudaDouble(cuDoubleComplex[][]matr, int height, int width){
        double [][] result = new double[height][width*2];
        for (int i = 0; i < height; i++){
            result[i] = Complex.complexToCudaComplex(matr[i], width);
        }

        return result;
    }

    public static cuDoubleComplex [] getRow(cuDoubleComplex[][] matr, int row){
        return matr [row];
    }

    public static  cuDoubleComplex [] getColumn (cuDoubleComplex[][] matr, int width, int height, int col){
        //TODO: optimize it!

        cuDoubleComplex[] res = new cuDoubleComplex[height];

        for (int i = 0; i < height; i++){
            res[i] = matr [i][col];
        }

        return res;
    }
}
