package kpfu.terentyev.quantum.emulator.Gates;

import jcuda.cuComplex;
import kpfu.terentyev.quantum.emulator.QuantumGate;

/**
 * Created by alexandrterentyev on 12.04.15.
 */
public class UGate extends QuantumGate {
    private cuComplex[][] matrix;
    public UGate (int qubitsNumber, cuComplex [][] uMatrix){
        this.qubitsNumber = qubitsNumber;
        this.size = (int) Math.pow(2, qubitsNumber);
        this.matrix = uMatrix;
    }
    @Override
    public cuComplex[][] getMatrix() {
        return matrix;
    }
}
