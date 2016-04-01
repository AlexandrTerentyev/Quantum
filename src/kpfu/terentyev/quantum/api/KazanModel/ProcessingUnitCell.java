package kpfu.terentyev.quantum.api.KazanModel;

import kpfu.terentyev.quantum.api.QuantumManager;
import kpfu.terentyev.quantum.api.QuantumManager.Qubit;

/**
 * Created by aleksandrterentev on 29.03.16.
 */
public class ProcessingUnitCell {
    private Qubit qubit;

//    FIXME: что делать в случае, если кубит уже был загружен и загружаем ещё один?
    public void loadQubit (Qubit qubit){
        this.qubit = qubit;
    }

    public Qubit unloadQubit (){
        Qubit result = qubit;
        qubit = null;
        return result;
    }

    public Qubit getQubit() {
        return qubit;
    }
}
