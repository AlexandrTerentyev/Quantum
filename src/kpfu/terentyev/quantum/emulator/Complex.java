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
}
