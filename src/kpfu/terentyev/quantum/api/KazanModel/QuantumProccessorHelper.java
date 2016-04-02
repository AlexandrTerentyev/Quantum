package kpfu.terentyev.quantum.api.KazanModel;

import kpfu.terentyev.quantum.api.QuantumManager;
import kpfu.terentyev.quantum.emulator.Complex;
import kpfu.terentyev.quantum.emulator.OneStepOneQubitGateAlgorythm;

/**
 * Created by aleksandrterentev on 29.03.16.
 */
public class QuantumProccessorHelper extends QuantumManager {
//    TODO: implement it!!!
    void physicalQET (Qubit a, Qubit b, double thetaPhaseInRadians) throws Exception {
        RegisterInfo regInfo = checkAndMergeRegistersIfNeedForQubits(a, b);
        Complex[][] matrix = {
                {Complex.unit(), Complex.zero(), Complex.zero(), Complex.zero()},
                {Complex.zero(), new Complex(Math.cos(-thetaPhaseInRadians/2.0), Math.sin(-thetaPhaseInRadians/2.0)), Complex.zero(), Complex.zero()},
                {Complex.zero(), Complex.zero(), new Complex(Math.cos(thetaPhaseInRadians/2.0), Math.sin(thetaPhaseInRadians/2.0)), Complex.zero()},
                {Complex.zero(), Complex.zero(), Complex.zero(), Complex.unit()}
        };
        performTransitionForQubits(matrix, Math.min(qubitAddressInRegister(a), qubitAddressInRegister(b)), regInfo, a, b);
    }
    void physicalCQET (Qubit a, Qubit control, Qubit b, double thetaPhaseInRadians){}
    void physicalPHASE (Qubit a, Qubit b, double thetaInRadians) throws Exception {
        RegisterInfo regInfo = checkAndMergeRegistersIfNeedForQubits(a, b);
        Complex[][] matrix = {
                {Complex.unit(), Complex.zero(), Complex.zero(), Complex.zero()},
                {Complex.zero(), new Complex(Math.cos(thetaInRadians/2), 0), new Complex(0, Math.sin(thetaInRadians/2)), Complex.zero()},
                {Complex.zero(), new Complex(0, Math.sin(thetaInRadians/2)), new Complex(Math.cos(thetaInRadians/2), 0), Complex.zero()},
                {Complex.zero(), Complex.zero(), Complex.zero(), Complex.unit()}
        };
        performTransitionForQubits(matrix, Math.min(qubitAddressInRegister(a), qubitAddressInRegister(b)), regInfo, a, b);
    }
}
