package kpfu.terentyev.quantum.api.KazanModel;

import kpfu.terentyev.quantum.api.KazanModel.QuantumMemory.QuantumMemoryAddress;
import kpfu.terentyev.quantum.api.QuantumManager;

/**
 * Created by aleksandrterentev on 29.03.16.
 */
public class Emulator {
    private QuantumProccessorHelper helper = new QuantumProccessorHelper();
    private QuantumMemory memory = new QuantumMemory(helper);

    private int processingUnitsCount = 3;
    private ProcessingUnit[] processingUnits = new ProcessingUnit[processingUnitsCount];

    public int getProcessingUnitsCount() {
        return processingUnitsCount;
    }

    public void initQubitForAddress (QuantumMemoryAddress address){
        try {
            memory.initQubitForAddress(address);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void load (QuantumMemoryAddress addressInMemory, ProcessingAddress processingAddress){
        QuantumManager.Qubit qubit = memory.popQubit(addressInMemory);
        if (qubit != null) {
            cellForProcessingAddress(processingAddress).loadQubit(qubit);
        }
    }
    public void save (ProcessingAddress processingAddress, QuantumMemoryAddress addressInMemory){
        QuantumManager.Qubit qubit = cellForProcessingAddress(processingAddress).unloadQubit();
        memory.saveQubit(addressInMemory, qubit);
    }

    public void QET (int proccessingUnitNum, double phase) {
        try {
            processingUnits[proccessingUnitNum].QET(phase);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void cQET (int proccessingUnitNum, double phase) {
        try {
            processingUnits[proccessingUnitNum].cQET(phase);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void PHASE (int proccessingUnitNum, double phase){
        try {
            processingUnits[proccessingUnitNum].PHASE(phase);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int measure (QuantumMemoryAddress address) {
        // что возвращать в случае неудачи?
        QuantumManager.Qubit qubit = memory.popQubit(address);
        try {
            return helper.measure (qubit);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

//    Service methods
    private ProcessingUnitCell cellForProcessingAddress(ProcessingAddress processingAddress){
        return processingUnits[processingAddress.getProccessingUnitNumber()]
                .cellForUnitAddress(processingAddress.getProccessingUnitCellAddress())
    }
}
