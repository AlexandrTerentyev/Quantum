package kpfu.terentyev.quantum.api.KazanModel;

import jcuda.cuComplex;
import kpfu.terentyev.quantum.api.QuantumManager;
import kpfu.terentyev.quantum.emulator.Complex;

/**
 * Created by aleksandrterentev on 29.03.16.
 */
public class QuantumProccessorHelper extends QuantumManager {
    void physicalQET (Qubit a, Qubit b, double thetaPhaseInRadians) throws Exception {
        RegisterInfo regInfo = checkAndMergeRegistersIfNeedForQubits(a, b);
        cuComplex[][] matrix = {
                {Complex.unit(), Complex.zero(), Complex.zero(), Complex.zero()},
                {Complex.zero(), cuComplex.cuCmplx((float) Math.cos(thetaPhaseInRadians/2), 0),cuComplex.cuCmplx(0, (float) Math.sin(thetaPhaseInRadians/2)), Complex.zero()},
                {Complex.zero(), cuComplex.cuCmplx(0, (float) Math.sin(thetaPhaseInRadians/2)), cuComplex.cuCmplx((float) Math.cos(thetaPhaseInRadians/2), 0), Complex.zero()},
                {Complex.zero(), Complex.zero(), Complex.zero(), Complex.unit()}
        };
        performTransitionForQubits(null, matrix, regInfo, a, b);
    }
    void physicalCQET (Qubit a, Qubit control, Qubit b, double thetaInRadians) throws Exception {
        RegisterInfo regInfo = checkAndMergeRegistersIfNeedForQubits(a, control, b);
        int minAddressOfAB  = Math.min(qubitAddressInRegister(a), qubitAddressInRegister(b));
        int maxAddressOfAB  = Math.max(qubitAddressInRegister(a), qubitAddressInRegister(b));
        cuComplex z = Complex.zero();
        cuComplex u = Complex.unit();
        cuComplex c =  cuComplex.cuCmplx((float) Math.cos(thetaInRadians/2), 0);
        cuComplex is = cuComplex.cuCmplx(0, (float) Math.sin(thetaInRadians/2));

        if (qubitAddressInRegister(control) < qubitAddressInRegister(a) &&
                qubitAddressInRegister(control) < qubitAddressInRegister(b)) {
            //control qubit is first
            cuComplex[][] matrix  = {
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
            cuComplex[][] matrix  = {
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
            cuComplex[][] matrix  = {
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
        cuComplex[][] matrix = {
                {Complex.unit(), Complex.zero(), Complex.zero(), Complex.zero()},
                {Complex.zero(), cuComplex.cuCmplx((float)Math.cos(-thetaInRadians/2.0), (float)Math.sin(-thetaInRadians/2.0)), Complex.zero(), Complex.zero()},
                {Complex.zero(), Complex.zero(), cuComplex.cuCmplx((float)Math.cos(thetaInRadians/2.0), (float) Math.sin(thetaInRadians/2.0)), Complex.zero()},
                {Complex.zero(), Complex.zero(), Complex.zero(), Complex.unit()}
        };
        performTransitionForQubits(null, matrix, regInfo, a, b);
    }

    void mergeQubits (Qubit ...qubits) throws Exception {
        checkAndMergeRegistersIfNeedForQubits(qubits);
    }
}
