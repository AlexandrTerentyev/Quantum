package kpfu.terentyev.quantum.emulator;

import jcuda.cuComplex;

/**
 * Created by alexandrterentyev on 25.02.15.
 */

public abstract class QuantumGate {
    protected int qubitsNumber;
    protected int size;


    @Override
    public String toString(){
        cuComplex[][] matrix = new cuComplex[0][];
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

    public abstract cuComplex [][] getMatrix () throws Exception;


    //Gate matrices
    public static cuComplex [][] identityGateMatrix(){
        cuComplex result [][] = {
                {Complex.unit(),Complex.zero()},
                {Complex.zero(),Complex.unit()}
        };
        return result;
    }
    public static cuComplex [][] hadamardGateMatrix (){
        cuComplex result [][] = {
                {cuComplex.cuCmplx((float) (1/Math.sqrt(2)), 0),cuComplex.cuCmplx((float) (1/Math.sqrt(2)),0)},
                {cuComplex.cuCmplx((float) (1/Math.sqrt(2)), 0),cuComplex.cuCmplx((float) (-1/Math.sqrt(2)),0)}
        };
        return result;
    }
    public static cuComplex [][] pauliXGateMatrix (){
        cuComplex result [][] = {
                {Complex.zero(),Complex.unit()},
                {Complex.unit(),Complex.zero()}
        };
        return result;
    }
    public static cuComplex [][] pauliYGateMatrix (){
        cuComplex result [][] = {
                {Complex.zero(),cuComplex.cuCmplx(0, -1)},
                {cuComplex.cuCmplx(0,1),Complex.zero()}
        };
        return result;
    }
    public static cuComplex [][] pauliZGateMatrix (){
        cuComplex result [][] = {
                {cuComplex.cuCmplx(0,1),Complex.zero()},
                {Complex.zero(),cuComplex.cuCmplx(0, -1)}
        };
        return result;
    }
    public static cuComplex [][] swapGateMatrix(){
        cuComplex result [][] = {
                {Complex.unit(), Complex.zero(), Complex.zero(), Complex.zero()},
                {Complex.zero(), Complex.zero(), Complex.unit(), Complex.zero()},
                {Complex.zero(), Complex.unit(), Complex.zero(), Complex.zero()},
                {Complex.zero(), Complex.zero(), Complex.zero(), Complex.unit()}
        };
        return result;
    }
    public static cuComplex [][] controlledNOTGateMatrix(){
        cuComplex result [][] = {
                {Complex.unit(), Complex.zero(), Complex.zero(), Complex.zero()},
                {Complex.zero(), Complex.unit(), Complex.zero(), Complex.zero()},
                {Complex.zero(), Complex.zero(), Complex.zero(), Complex.unit()},
                {Complex.zero(), Complex.zero(), Complex.unit(), Complex.zero()}
        };
        return result;
    }
    public static cuComplex [][] controlledUGateMatrix(cuComplex[][] uMatrix) throws Exception {
        if (uMatrix.length!=2 || (uMatrix.length==2 && (uMatrix[0].length!=2 || uMatrix[1].length!=2))){
            throw new Exception();
        }
        cuComplex result [][] = {
                {Complex.unit(), Complex.zero(), Complex.zero(), Complex.zero()},
                {Complex.zero(), Complex.unit(), Complex.zero(), Complex.zero()},
                {Complex.zero(), Complex.zero(), uMatrix[0][0], uMatrix[0][1]},
                {Complex.zero(), Complex.zero(), uMatrix[1][0], uMatrix[1][1]},
        };
        return result;
    }
    public static cuComplex [][] toffoliGateMatrix(){
        cuComplex result [][] = {
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
    public static cuComplex [][] fredkinGateMatrix(){
        cuComplex result [][] = {
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
