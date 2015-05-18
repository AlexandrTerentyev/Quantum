package kpfu.terentyev.quantum_emulator;

import javafx.beans.property.IntegerProperty;

import javax.print.DocFlavor;
import java.util.Vector;

/**
 * Created by alexandrterentyev on 25.02.15.
 */

public abstract class QuantumGate {
//    Complex [][] matrix;
    protected int qubitsNumber;
    protected double size;


//    public QuantumGate (int qubitsNumber, Complex [][] matrix){
//        this.qubitsNumber = qubitsNumber;
//        this.size = Math.pow(2,qubitsNumber);
//        this.matrix=matrix;
//    }
    @Override
    public String toString(){
        Complex [][] matrix = this.getMatrix();
        String result = "";
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                result= result + matrix[i][j]+" ";
            }
            result = result + "\n";
        }
        return result;
    }

    public abstract Complex [][] getMatrix ();


    //Gate matrices
    public static Complex [][] identityGateMatrix(){
        Complex result [][] = {
                {Complex.unit(),Complex.zero()},
                {Complex.zero(),Complex.unit()}
        };
        return result;
    }
    public static Complex [][] hadamardGateMatrix (){
        Complex result [][] = {
                {new Complex(1/Math.sqrt(2), 0),new Complex(1/Math.sqrt(2),0)},
                {new Complex(1/Math.sqrt(2), 0),new Complex(-1/Math.sqrt(2),0)}
        };
        return result;
    }
    public static Complex [][] pauliXGateMatrix (){
        Complex result [][] = {
                {Complex.zero(),Complex.unit()},
                {Complex.unit(),Complex.zero()}
        };
        return result;
    }
    public static Complex [][] pauliYGateMatrix (){
        Complex result [][] = {
                {Complex.zero(),new Complex(0, -1)},
                {new Complex(0,1),Complex.zero()}
        };
        return result;
    }
    public static Complex [][] pauliZGateMatrix (){
        Complex result [][] = {
                {new Complex(0,1),Complex.zero()},
                {Complex.zero(),new Complex(0, -1)}
        };
        return result;
    }
    public static Complex [][] swapGateMatrix(){
        Complex result [][] = {
                {Complex.unit(), Complex.zero(), Complex.zero(), Complex.zero()},
                {Complex.zero(), Complex.zero(), Complex.unit(), Complex.zero()},
                {Complex.zero(), Complex.unit(), Complex.zero(), Complex.zero()},
                {Complex.zero(), Complex.zero(), Complex.zero(), Complex.unit()}
        };
        return result;
    }
    public static Complex [][] controlledNOTGateMatrix(){
        Complex result [][] = {
                {Complex.unit(), Complex.zero(), Complex.zero(), Complex.zero()},
                {Complex.zero(), Complex.unit(), Complex.zero(), Complex.zero()},
                {Complex.zero(), Complex.zero(), Complex.zero(), Complex.unit()},
                {Complex.zero(), Complex.zero(), Complex.unit(), Complex.zero()}
        };
        return result;
    }
    public static Complex [][] controlledUGateMatrix(Complex[][] uMatrix) throws Exception {
        if (uMatrix.length!=2 || (uMatrix.length==2 && (uMatrix[0].length!=2 || uMatrix[1].length!=2))){
            throw new Exception();
        }
        Complex result [][] = {
                {Complex.unit(), Complex.zero(), Complex.zero(), Complex.zero()},
                {Complex.zero(), Complex.unit(), Complex.zero(), Complex.zero()},
                {Complex.zero(), Complex.zero(), uMatrix[0][0], uMatrix[0][1]},
                {Complex.zero(), Complex.zero(), uMatrix[1][0], uMatrix[1][1]},
        };
        return result;
    }
    public static Complex [][] toffoliGateMatrix(){
        Complex result [][] = {
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
    public static Complex [][] fredkinGateMatrix(){
        Complex result [][] = {
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

    //Gates
//    public static QuantumGate identityGate(){
//        return new QuantumGate(1, QuantumGate.identityGateMatrix());
//    }
//    public static QuantumGate hadamardGate(){
//        return new QuantumGate(1, QuantumGate.hadamardGateMatrix());
//    }
//    public static QuantumGate pauliXGate(){
//        return new QuantumGate(1, QuantumGate.pauliXGateMatrix());
//    }
//    public static QuantumGate pauliYGate(){
//        return new QuantumGate(1, QuantumGate.pauliYGateMatrix());
//    }
//    public static QuantumGate pauliZGate(){
//        return new QuantumGate(1, QuantumGate.pauliZGateMatrix());
//    }
//    public static QuantumGate swapGate (){
//        return new QuantumGate (2, QuantumGate.swapGateMatrix());
//    }
//    public static QuantumGate controlledNOTGate(){
//        return new QuantumGate(2, QuantumGate.controlledNOTGateMatrix());
//    }
//    public static QuantumGate controlledUGate (Complex[][]uMatrix) throws Exception{
//        return new QuantumGate(2, QuantumGate.controlledUGateMatrix(uMatrix));
//    }
//    public static QuantumGate toffoliGate (){
//        return new QuantumGate(3, QuantumGate.toffoliGateMatrix());
//    }
//    public static QuantumGate fredkinGate (){
//        return new QuantumGate(3, QuantumGate.fredkinGateMatrix());
//    }
}
