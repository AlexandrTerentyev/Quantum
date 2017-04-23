package kpfu.terentyev.quantum.KazanModel;

import kpfu.terentyev.quantum.emulator.api.QuantumManager.Qubit;

/**
 * Created by aleksandrterentev on 29.03.16.
 */
public class ProcessingUnitCell {
    private Qubit qubit;

    void loadQubit (Qubit qubit){
        this.qubit = qubit;
    }

    Qubit unloadQubit (){
        Qubit result = qubit;
        qubit = null;
        return result;
    }

    Qubit getQubit() {
        return qubit;
    }
}
