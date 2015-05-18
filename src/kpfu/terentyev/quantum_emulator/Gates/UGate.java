package kpfu.terentyev.quantum_emulator.Gates;

import kpfu.terentyev.quantum_emulator.Complex;
import kpfu.terentyev.quantum_emulator.QuantumGate;

/**
 * Created by alexandrterentyev on 12.04.15.
 */
public class UGate extends QuantumGate {
    private Complex[][] matrix;
    public UGate (int qubitsNumber, Complex [][] uMatrix){
        this.qubitsNumber = qubitsNumber;
        this.size = Math.pow(2, qubitsNumber);
        this.matrix = uMatrix;
    }
    @Override
    public Complex[][] getMatrix() {
        return matrix;
    }
}
