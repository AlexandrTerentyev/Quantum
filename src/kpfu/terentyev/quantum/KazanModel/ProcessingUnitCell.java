package kpfu.terentyev.quantum.KazanModel;

import kpfu.terentyev.quantum.emulator.api.QuantumManager;

/**
 * Created by aleksandrterentev on 29.03.16.
 */
public class ProcessingUnitCell {
    private QuantumManager.Qubit qubit;

    void loadQubit (QuantumManager.Qubit qubit){
        this.qubit = qubit;
    }

    QuantumManager.Qubit unloadQubit (){
        QuantumManager.Qubit result = qubit;
        qubit = null;
        return result;
    }

    QuantumManager.Qubit getQubit() {
        return qubit;
    }
}
