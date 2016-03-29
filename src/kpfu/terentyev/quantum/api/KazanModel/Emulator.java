package kpfu.terentyev.quantum.api.KazanModel;

import kpfu.terentyev.quantum.api.KazanModel.QuantumMemory.QuantumMemoryAddress;
import kpfu.terentyev.quantum.api.QuantumManager;

/**
 * Created by aleksandrterentev on 29.03.16.
 */
public class Emulator {
    private QuantumProccessorHelper helper = new QuantumProccessorHelper();
    private QuantumMemory memory = new QuantumMemory(helper);

    private ProcessingUnit[] processingUnits = new ProcessingUnit[3];

    public void initQubitForAddress (QuantumMemoryAddress address) throws Exception {
        memory.initQubitForAddress(address);
    }

    public void load (QuantumMemoryAddress addressInMemory, ProcessingAddress processingAddress){
//        TODO: implement it!
    }
    public void save (ProcessingAddress processingAddress, QuantumMemoryAddress addressInMemory){
        //        TODO: implement it!
    }

    public void QET (int proccessUnitNum, double phase) throws Exception {
        processingUnits[proccessUnitNum].QET(phase);
    }
    public void cQET (int proccessUnitNum, double phase) throws Exception {
        processingUnits[proccessUnitNum].cQET(phase);
    }
    public void PHASE (int proccessUnitNum, double phase) throws Exception {
        processingUnits[proccessUnitNum].PHASE(phase);
    }

    public int measure (QuantumMemoryAddress address) throws Exception {
        QuantumManager.Qubit qubit = memory.popQubit(address);
        return helper.measure (qubit);
    }
}
