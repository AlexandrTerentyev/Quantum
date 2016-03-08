package kpfu.terentyev.quantum_api;

import kpfu.terentyev.quantum_emulator.Complex;
import kpfu.terentyev.quantum_emulator.OneStepAlgorythm;
import kpfu.terentyev.quantum_emulator.Gates.PhaseGate;
import kpfu.terentyev.quantum_emulator.OneStepTwoQubitControlledGateAlgorythm;

/**
 * Created by aleksandrterentev on 08.03.16.
 */
public class QuantumMemoryManager extends QuantumManager {

//    Base operations
    void phase (double thetaInRadians, Qubit qubit) throws Exception {
        RegisterInfo registerInfo = registers.get(qubit.registerAddress);
        OneStepAlgorythm oneStepAlgorythm = new OneStepAlgorythm(registerInfo.register.getQubitsNumber(),
                new PhaseGate(thetaInRadians),
                qubit.addressInRegister
                );
        registerInfo.register.performAlgorythm(oneStepAlgorythm);
    }

    void QET (double thetaInRadians, Qubit qubit) throws Exception {
        RegisterInfo registerInfo = registers.get(qubit.registerAddress);
        Complex[][] matrix = {
                {new Complex(Math.cos(thetaInRadians/2), 0), new Complex(0, Math.sin(thetaInRadians/2))},
                {new Complex(0, Math.sin(thetaInRadians/2)), new Complex(Math.cos(thetaInRadians/2), 0)}
        };
        OneStepAlgorythm oneStepAlgorythm = new OneStepAlgorythm(registerInfo.register.getQubitsNumber(),
                matrix,
                qubit.addressInRegister
        );
        registerInfo.register.performAlgorythm(oneStepAlgorythm);
    }

    void cQET (double thetaInRadians, Qubit controllingQubit, Qubit controlledQubit) throws Exception {
        RegisterInfo registerInfo = checkAndMergeRegistersIfNeedForQubits(controllingQubit, controlledQubit);
//        TODO: fill matrix!!!
        Complex[][] matrix = {};
        OneStepTwoQubitControlledGateAlgorythm algorythm = new OneStepTwoQubitControlledGateAlgorythm(
                registerInfo.register.getQubitsNumber(),
                controllingQubit.addressInRegister,
                controlledQubit.addressInRegister,
                matrix
                );
        registerInfo.register.performAlgorythm(algorythm);
    }

    int measure (Qubit qubit) throws Exception {
        return registers.get(qubit.registerAddress).register.measureQubit(qubit.addressInRegister);
    }
}
