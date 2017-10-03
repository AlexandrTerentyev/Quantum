package tests.manager;

import kpfu.terentyev.quantum.emulator.api.QuantumManager;
import kpfu.terentyev.quantum.emulator.core.QuantumRegister;

import java.util.Collection;

/**
 * Created by aleksandrterentev on 04.10.17.
 */
public class TestableQuantumManager extends QuantumManager {
    Collection<RegisterInfo> allRegisters(){
        return registers.values();
    }

    QuantumRegister infoForRegisterID(String registerID){
        return registers.get(registerID).register;
    }

    String registerIDForQubit(Qubit q){
        return registerAddressOfQubit(q);
    }

    int indexInRegisterForQubit(Qubit q){
        return qubitAddressInRegister(q);
    }
}
