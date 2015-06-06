package kpfu.terentyev.quantum_emulator;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by alexandrterentyev on 25.02.15.
 */
public class QuantumRegister {
    private int qubitsNumber;
    private int size;
    private Complex [] vector;

    public QuantumRegister (int qubitsNumber) {
        this.setQubitsNumber(qubitsNumber);
    }
    public QuantumRegister (int qubitsNumber, Complex [] vector) throws Exception {
        this.qubitsNumber = qubitsNumber;
        size = ((int) Math.pow(2, qubitsNumber));
        this.vector =vector;
        if (size != vector.length){
            throw new Exception();
        }
    }

    public int getQubitsNumber() {
        return qubitsNumber;
    }

    public void setQubitsNumber(int qubitsNumber) {
        this.qubitsNumber = qubitsNumber;
        this.size = ((int) Math.pow(2, qubitsNumber));
        this.vector = new Complex[size];
        this.vector[0]=Complex.unit();
        for (int i=1; i<this.vector.length; i++){
            vector[i]=Complex.zero();
        }
    }

    public void multiplyOnMatrix(Complex[][]matrix) throws Exception {
        if (matrix.length!=size){
            throw new Exception();
        }
        this.vector=ComplexMath.multiplication(matrix, size, vector);
    }

    @Override
    public String toString() {
        String result ="";
        for (int i=0; i<size; i++){
            result= result+ vector[i] +" |"+i+">\n";
        }
        return result;
    }

    public void performAlgorythm (QuantumAlgorythm algorythm) throws Exception {
        vector = ComplexMath.multiplication(algorythm.getMatrix(), size, vector);
    }
}
