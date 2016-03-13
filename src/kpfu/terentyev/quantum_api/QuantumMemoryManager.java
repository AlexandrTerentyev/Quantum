package kpfu.terentyev.quantum_api;

import kpfu.terentyev.quantum_emulator.Complex;
import kpfu.terentyev.quantum_emulator.OneStepAlgorythm;
import kpfu.terentyev.quantum_emulator.Gates.PhaseGate;
import kpfu.terentyev.quantum_emulator.OneStepTwoQubitControlledGateAlgorythm;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by aleksandrterentev on 08.03.16.
 */
public class QuantumMemoryManager extends QuantumManager {

    private int proccessorsUnitsCount = 4;
    private static final String qubitDestroyedRegisterAddress = "Qubit destroyed";


    Set proccessorsUnitsRegisters;

    public QuantumMemoryManager(){
        proccessorsUnitsRegisters = new HashSet<Qubit>();
    }

//    Base operations

    public boolean qubitIsAlreadyInProccessor (Qubit q){
        return proccessorsUnitsRegisters.contains(q);
    }

    public void load (Qubit q) throws Exception {
        if (proccessorsUnitsRegisters.size() == proccessorsUnitsCount && !qubitIsAlreadyInProccessor(q)){
            throw (new Exception("Processor units overflow"));
        }

        proccessorsUnitsRegisters.add(q);
    }

    public void save (Qubit q){
        proccessorsUnitsRegisters.remove(q);
    }


    public void checkQubitsBeforePerformTransformation (Qubit ... qubits) throws Exception {
        for (Qubit q: qubits) {
            if (q.registerAddress.equals(qubitDestroyedRegisterAddress)) {
                throw (new Exception("One ore more qubits already destroyed!"));
            } else if (!qubitIsAlreadyInProccessor(q)) {
                throw (new Exception("Operating qubits are not loaded to proccessor units!"));
            }
        }
    }

    /**
     * This tranformation must be performed for qubit that loaded to proccessor unit
     * */
    public void phase (double thetaInRadians, Qubit qubit) throws Exception {
        checkQubitsBeforePerformTransformation(qubit);
        RegisterInfo registerInfo = registers.get(qubit.registerAddress);
        OneStepAlgorythm oneStepAlgorythm = new OneStepAlgorythm(registerInfo.register.getQubitsNumber(),
                new PhaseGate(thetaInRadians),
                qubit.addressInRegister
                );
        registerInfo.register.performAlgorythm(oneStepAlgorythm);
    }

    /**
     * This tranformation must be performed for qubit that loaded to proccessor unit
     * */
    public void QET (double thetaInRadians, Qubit qubit) throws Exception {
        checkQubitsBeforePerformTransformation(qubit);
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

    /**
     * This tranformation must be performed for qubits that loaded to proccessor units
     * */
    public void cQET (double thetaInRadians, Qubit controllingQubit, Qubit controlledQubit) throws Exception {
        checkQubitsBeforePerformTransformation(controlledQubit, controllingQubit);
        RegisterInfo registerInfo = checkAndMergeRegistersIfNeedForQubits(controllingQubit, controlledQubit);
//        TODO: fill matrix!!!
        Complex temp = new Complex(Math.cos(- thetaInRadians), Math.sin(-thetaInRadians));
        Complex[][] matrix = {
                {Complex.unit(), Complex.zero(), Complex.zero(), Complex.zero()},
                {Complex.zero(), Complex.unit(), Complex.zero(), Complex.zero()},
                {Complex.zero(), Complex.zero(), Complex.unit(), Complex.zero()},
                {Complex.zero(), Complex.zero(), Complex.zero(), temp}
        };
        OneStepTwoQubitControlledGateAlgorythm algorythm = new OneStepTwoQubitControlledGateAlgorythm(
                registerInfo.register.getQubitsNumber(),
                controllingQubit.addressInRegister,
                controlledQubit.addressInRegister,
                matrix
                );
        registerInfo.register.performAlgorythm(algorythm);
    }

    public int measure (Qubit qubit) throws Exception {
        RegisterInfo regInfo = registers.get(qubit.registerAddress);
        int result = regInfo.register.measureQubit(qubit.addressInRegister);
        regInfo.qubits.remove(qubit.addressInRegister);
        qubit.registerAddress = qubitDestroyedRegisterAddress;
        return result;
    }
}
