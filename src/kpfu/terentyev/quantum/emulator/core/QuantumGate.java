package kpfu.terentyev.quantum.emulator.core;

import jcuda.cuDoubleComplex;

/**
 * Created by alexandrterentyev on 25.02.15.
 */

public abstract class QuantumGate {
    protected int qubitsNumber;
    protected int size;


    @Override
    public String toString(){
        cuDoubleComplex[][] matrix = new cuDoubleComplex[0][];
        try {
            matrix = this.getMatrix();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String result = "";
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                result= result + matrix[i][j]+" ";
            }
            result = result + "\n";
        }
        return result;
    }

    public abstract cuDoubleComplex [][] getMatrix () throws Exception;


    //Gate matrices
    public static cuDoubleComplex [][] identityGateMatrix(){
        cuDoubleComplex result [][] = {
                {Complex.unit(),Complex.zero()},
                {Complex.zero(),Complex.unit()}
        };
        return result;
    }
    public static cuDoubleComplex [][] hadamardGateMatrix (){
        cuDoubleComplex result [][] = {
                {cuDoubleComplex.cuCmplx((float) (1/Math.sqrt(2)), 0),cuDoubleComplex.cuCmplx((float) (1/Math.sqrt(2)),0)},
                {cuDoubleComplex.cuCmplx((float) (1/Math.sqrt(2)), 0),cuDoubleComplex.cuCmplx((float) (-1/Math.sqrt(2)),0)}
        };
        return result;
    }
    public static cuDoubleComplex [][] pauliXGateMatrix (){
        cuDoubleComplex result [][] = {
                {Complex.zero(),Complex.unit()},
                {Complex.unit(),Complex.zero()}
        };
        return result;
    }
    public static cuDoubleComplex [][] pauliYGateMatrix (){
        cuDoubleComplex result [][] = {
                {Complex.zero(),cuDoubleComplex.cuCmplx(0, -1)},
                {cuDoubleComplex.cuCmplx(0,1),Complex.zero()}
        };
        return result;
    }
    public static cuDoubleComplex [][] pauliZGateMatrix (){
        cuDoubleComplex result [][] = {
                {cuDoubleComplex.cuCmplx(0,1),Complex.zero()},
                {Complex.zero(),cuDoubleComplex.cuCmplx(0, -1)}
        };
        return result;
    }
    public static cuDoubleComplex [][] swapGateMatrix(){
        cuDoubleComplex result [][] = {
                {Complex.unit(), Complex.zero(), Complex.zero(), Complex.zero()},
                {Complex.zero(), Complex.zero(), Complex.unit(), Complex.zero()},
                {Complex.zero(), Complex.unit(), Complex.zero(), Complex.zero()},
                {Complex.zero(), Complex.zero(), Complex.zero(), Complex.unit()}
        };
        return result;
    }
    public static cuDoubleComplex [][] controlledNOTGateMatrix(){
        cuDoubleComplex result [][] = {
                {Complex.unit(), Complex.zero(), Complex.zero(), Complex.zero()},
                {Complex.zero(), Complex.unit(), Complex.zero(), Complex.zero()},
                {Complex.zero(), Complex.zero(), Complex.zero(), Complex.unit()},
                {Complex.zero(), Complex.zero(), Complex.unit(), Complex.zero()}
        };
        return result;
    }
    public static cuDoubleComplex [][] controlledUGateMatrix(cuDoubleComplex[][] uMatrix) throws Exception {
        if (uMatrix.length!=2 || (uMatrix.length==2 && (uMatrix[0].length!=2 || uMatrix[1].length!=2))){
            throw new Exception();
        }
        cuDoubleComplex result [][] = {
                {Complex.unit(), Complex.zero(), Complex.zero(), Complex.zero()},
                {Complex.zero(), Complex.unit(), Complex.zero(), Complex.zero()},
                {Complex.zero(), Complex.zero(), uMatrix[0][0], uMatrix[0][1]},
                {Complex.zero(), Complex.zero(), uMatrix[1][0], uMatrix[1][1]},
        };
        return result;
    }
    public static cuDoubleComplex [][] toffoliGateMatrix(){
        cuDoubleComplex result [][] = {
                {Complex.unit(), Complex.zero(), Complex.zero(), Complex.zero(),
                        Complex.zero(), Complex.zero(), Complex.zero(), Complex.zero()},
                {Complex.zero(), Complex.unit(), Complex.zero(), Complex.zero(),
                        Complex.zero(), Complex.zero(), Complex.zero(), Complex.zero()},
                {Complex.zero(), Complex.zero(), Complex.unit(), Complex.zero(),
                        Complex.zero(), Complex.zero(), Complex.zero(), Complex.zero()},
                {Complex.zero(), Complex.zero(), Complex.zero(), Complex.unit(),
                        Complex.zero(), Complex.zero(), Complex.zero(), Complex.zero()},
                {Complex.zero(), Complex.zero(), Complex.zero(), Complex.zero(),
                        Complex.unit(), Complex.zero(), Complex.zero(), Complex.zero()},
                {Complex.zero(), Complex.zero(), Complex.zero(), Complex.zero(),
                        Complex.zero(), Complex.unit(), Complex.zero(), Complex.zero()},
                {Complex.zero(), Complex.zero(), Complex.zero(), Complex.zero(),
                        Complex.zero(), Complex.zero(), Complex.zero(), Complex.unit()},
                {Complex.zero(), Complex.zero(), Complex.zero(), Complex.zero(),
                        Complex.zero(), Complex.zero(), Complex.unit(), Complex.zero()}
        };
        return result;
    }
    public static cuDoubleComplex [][] fredkinGateMatrix(){
        cuDoubleComplex result [][] = {
                {Complex.unit(), Complex.zero(), Complex.zero(), Complex.zero(),
                        Complex.zero(), Complex.zero(), Complex.zero(), Complex.zero()},
                {Complex.zero(), Complex.unit(), Complex.zero(), Complex.zero(),
                        Complex.zero(), Complex.zero(), Complex.zero(), Complex.zero()},
                {Complex.zero(), Complex.zero(), Complex.unit(), Complex.zero(),
                        Complex.zero(), Complex.zero(), Complex.zero(), Complex.zero()},
                {Complex.zero(), Complex.zero(), Complex.zero(), Complex.unit(),
                        Complex.zero(), Complex.zero(), Complex.zero(), Complex.zero()},
                {Complex.zero(), Complex.zero(), Complex.zero(), Complex.zero(),
                        Complex.unit(), Complex.zero(), Complex.zero(), Complex.zero()},
                {Complex.unit(), Complex.zero(), Complex.zero(), Complex.zero(),
                        Complex.zero(), Complex.zero(), Complex.unit(), Complex.zero()},
                {Complex.zero(), Complex.zero(), Complex.zero(), Complex.zero(),
                        Complex.zero(), Complex.unit(), Complex.zero(), Complex.zero()},
                {Complex.zero(), Complex.zero(), Complex.zero(), Complex.zero(),
                        Complex.zero(), Complex.zero(), Complex.zero(), Complex.unit()}
        };
        return result;
    }
}
