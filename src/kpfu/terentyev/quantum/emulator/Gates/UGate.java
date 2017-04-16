package kpfu.terentyev.quantum.emulator.Gates;

import jcuda.cuDoubleComplex;
import kpfu.terentyev.quantum.emulator.QuantumGate;

/**
 * Created by alexandrterentyev on 12.04.15.
 */
public class UGate extends QuantumGate {
    private cuDoubleComplex[][] matrix;
    public UGate (int qubitsNumber, cuDoubleComplex [][] uMatrix){
        this.qubitsNumber = qubitsNumber;
        this.size = (int) Math.pow(2, qubitsNumber);
        this.matrix = uMatrix;
    }
    @Override
    public cuDoubleComplex[][] getMatrix() {
        return matrix;
    }
}
