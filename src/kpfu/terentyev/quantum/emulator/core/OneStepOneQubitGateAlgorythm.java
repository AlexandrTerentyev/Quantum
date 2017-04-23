package kpfu.terentyev.quantum.emulator.core;

import jcuda.cuDoubleComplex;
import kpfu.terentyev.quantum.emulator.core.Gates.IdentityGate;

/**
 * Created by aleksandrterentev on 08.03.16.
 */
public class OneStepOneQubitGateAlgorythm extends QuantumGate  {

    /**
     * New gate for register that for transition qubit at position
     * */

    cuDoubleComplex[][] matrix;

    public OneStepOneQubitGateAlgorythm(int qubitsNumber, QuantumGate oneQubitGate, int qubitPosition) throws Exception {
        configureGateForOneQubitTransition(qubitsNumber, oneQubitGate.getMatrix(), qubitPosition);
    }

    public OneStepOneQubitGateAlgorythm(int qubitsNumber, cuDoubleComplex[][] oneQubitGateMatrix, int qubitPosition) throws Exception {
        configureGateForOneQubitTransition(qubitsNumber, oneQubitGateMatrix, qubitPosition);
    }

    private void configureGateForOneQubitTransition(int qubitsNumber, cuDoubleComplex[][] oneQubitGateMatrix, int qubitPosition) throws Exception {
        matrix = new cuDoubleComplex[][]{{Complex.unit()}};
        QuantumGate identityGate = new IdentityGate();
        for (int i=0; i< qubitPosition; i++){
            matrix = ComplexMath.tensorMultiplication(matrix, matrix.length, matrix.length, identityGate.getMatrix(), 2,2);
        }
        matrix = ComplexMath.tensorMultiplication(matrix, matrix.length, matrix.length, oneQubitGateMatrix, 2,2);

        for (int i= qubitPosition+1; i< qubitsNumber; i++){
            matrix = ComplexMath.tensorMultiplication(matrix, matrix.length, matrix.length, identityGate.getMatrix(), 2,2);
        }

        this.qubitsNumber = qubitsNumber;
        this.size = (int) Math.pow(2, qubitsNumber);
    }

    @Override
    public cuDoubleComplex[][] getMatrix() throws Exception {
        return matrix;
    }
}
