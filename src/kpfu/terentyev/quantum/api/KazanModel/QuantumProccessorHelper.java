package kpfu.terentyev.quantum.api.KazanModel;

import jcuda.cuDoubleComplex;
import kpfu.terentyev.quantum.api.QuantumManager;
import kpfu.terentyev.quantum.emulator.Complex;

/**
 * Created by aleksandrterentev on 29.03.16.
 */
public class QuantumProccessorHelper extends QuantumManager {
    void physicalQET (Qubit a, Qubit b, double thetaPhaseInRadians) throws Exception {
        RegisterInfo regInfo = checkAndMergeRegistersIfNeedForQubits(a, b);
        cuDoubleComplex[][] matrix = {
                {Complex.unit(), Complex.zero(), Complex.zero(), Complex.zero()},
                {Complex.zero(), cuDoubleComplex.cuCmplx((float) Math.cos(thetaPhaseInRadians/2), 0),cuDoubleComplex.cuCmplx(0, (float) Math.sin(thetaPhaseInRadians/2)), Complex.zero()},
                {Complex.zero(), cuDoubleComplex.cuCmplx(0, (float) Math.sin(thetaPhaseInRadians/2)), cuDoubleComplex.cuCmplx((float) Math.cos(thetaPhaseInRadians/2), 0), Complex.zero()},
                {Complex.zero(), Complex.zero(), Complex.zero(), Complex.unit()}
        };
        performTransitionForQubits(null, matrix, regInfo, a, b);
    }
    void physicalCQET (Qubit a, Qubit control, Qubit b, double thetaInRadians) throws Exception {
        RegisterInfo regInfo = checkAndMergeRegistersIfNeedForQubits(a, control, b);
        int minAddressOfAB  = Math.min(qubitAddressInRegister(a), qubitAddressInRegister(b));
        int maxAddressOfAB  = Math.max(qubitAddressInRegister(a), qubitAddressInRegister(b));
        cuDoubleComplex z = Complex.zero();
        cuDoubleComplex u = Complex.unit();
        cuDoubleComplex c =  cuDoubleComplex.cuCmplx((float) Math.cos(thetaInRadians/2), 0);
        cuDoubleComplex is = cuDoubleComplex.cuCmplx(0, (float) Math.sin(thetaInRadians/2));

        if (qubitAddressInRegister(control) < qubitAddressInRegister(a) &&
                qubitAddressInRegister(control) < qubitAddressInRegister(b)) {
            //control qubit is first
            cuDoubleComplex[][] matrix  = {
                    {u, z, z, z, z,z,z,z},
                    {z, c, is, z, z,z,z,z},
                    {z, is, c, z, z,z,z,z},
                    {z, z, z, u, z,z,z,z},

                    {z,z,z,z, u, z, z, z},
                    {z,z,z,z, z, u, z, z},
                    {z,z,z,z, z, z, u, z},
                    {z,z,z,z, z, z, z, u}
            };
            performTransitionForQubits(null, matrix,
                    regInfo, a, b, control);
        }else if (qubitAddressInRegister(control) > minAddressOfAB &&
                qubitAddressInRegister(control) < maxAddressOfAB){
//            //control qubit is between a and b
            cuDoubleComplex[][] matrix  = {
                    {z,z, u, z, z, z, z,z},
                    {z,z, z, c, is, z,z,z},

                    { u, z, z,z,z,z, z, z},
                    { z, u, z,z,z,z, z, z},

                    {z,z, z, is, c, z, z,z},
                    {z,z, z, z, z, u, z,z},

                    {z, z, z,z,z,z, u, z},
                    {z, z, z,z,z,z, z, u}
            };
            performTransitionForQubits(null, matrix,
                    regInfo, a, b, control);
        }else{
            //control qubit is last
            cuDoubleComplex[][] matrix  = {
                    {z,z,z,z, u, z, z, z},
                    {u, z, z, z, z,z,z,z},

                    {z,z,z,z, z, c, is, z},
                    { z, u, z, z, z,z,z,z,},

                    {z,z,z,z, z, is, c, z},
                    {z, z, u, z, z,z,z,z},

                    {z,z,z,z, z, z, z, u},
                    {z, z, z, u, z,z,z,z}
            };

            performTransitionForQubits(null, matrix,
                    regInfo,a, b, control);
        }
    }
    void physicalPHASE (Qubit a, Qubit b, double thetaInRadians) throws Exception {
        RegisterInfo regInfo = checkAndMergeRegistersIfNeedForQubits(a, b);
        cuDoubleComplex[][] matrix = {
                {Complex.unit(), Complex.zero(), Complex.zero(), Complex.zero()},
                {Complex.zero(), cuDoubleComplex.cuCmplx((float)Math.cos(-thetaInRadians/2.0), (float)Math.sin(-thetaInRadians/2.0)), Complex.zero(), Complex.zero()},
                {Complex.zero(), Complex.zero(), cuDoubleComplex.cuCmplx((float)Math.cos(thetaInRadians/2.0), (float) Math.sin(thetaInRadians/2.0)), Complex.zero()},
                {Complex.zero(), Complex.zero(), Complex.zero(), Complex.unit()}
        };
        performTransitionForQubits(null, matrix, regInfo, a, b);
    }

    void mergeQubits (Qubit ...qubits) throws Exception {
        checkAndMergeRegistersIfNeedForQubits(qubits);
    }
}
