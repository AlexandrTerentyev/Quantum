package kpfu.terentyev.quantum.emulator;

import jcuda.cuComplex;

/**
 * Created by aleksandrterentev on 14.04.17.
 */
public class Complex {
    public static cuComplex  zero () {
        return cuComplex.cuCmplx(0, 0);
    }

    public static cuComplex unit (){
        return cuComplex.cuCmplx(1, 0);
    }

    public  static  float[] complexToCudaComplex (cuComplex[] array, int size){
        float [] result = new float[size*2];

        for (int i=0; i< size; i++){
            result [i*2] = array [i].x;
            result [i*2 + 1] = array[i].y;
        }

        return result;
    }

    public static float [][] complexMatrToCudaFloat(cuComplex[][]matr, int height, int width){
        float [][] result = new float[height][width*2];
        for (int i = 0; i < height; i++){
            result[i] = Complex.complexToCudaComplex(matr[i], width);
        }

        return result;
    }
}
