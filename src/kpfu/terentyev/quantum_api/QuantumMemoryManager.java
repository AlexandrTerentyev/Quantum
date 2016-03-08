package kpfu.terentyev.quantum_api;

import kpfu.terentyev.quantum_emulator.QuantumManager;

/**
 * Created by aleksandrterentev on 08.03.16.
 */
public class QuantumMemoryManager extends QuantumManager {

//    Base operations
    void phase (double angleInRadians, Qubit a, Qubit b) throws Exception {
        checkAndMergeRegistersIfNeedForQubits(a, b);
//        для текущего регистра необходимо составить матрицу преобразования (то есть составить схему алгоритма, вычислить матрицу и применить)
    }
}
