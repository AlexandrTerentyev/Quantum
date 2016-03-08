package kpfu.terentyev.quantum_emulator;

import kpfu.terentyev.quantum_emulator.Gates.IdentityGate;

import java.util.Map;

/**
 * Created by aleksandrterentev on 08.03.16.
 */
public class OneStepAlgorythm extends QuantumGate  {

    /**
     * New gate for register that for transition qubit at position
     * */

    Complex [][] matrix;

    public OneStepAlgorythm(int qubitsNumber, QuantumGate oneQubitGate, int qubitPosition) throws Exception {
        configureGateForOneQubitTransition(qubitsNumber, oneQubitGate.getMatrix(), qubitPosition);
    }

    public OneStepAlgorythm(int qubitsNumber, Complex[][] oneQubitGateMatrix, int qubitPosition) throws Exception {
        configureGateForOneQubitTransition(qubitsNumber, oneQubitGateMatrix, qubitPosition);
    }

    private void configureGateForOneQubitTransition(int qubitsNumber, Complex[][] oneQubitGateMatrix, int qubitPosition) throws Exception {
        matrix = new Complex[][]{{Complex.unit()}};
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
    public Complex[][] getMatrix() throws Exception {
        return matrix;
    }
}
