package kpfu.terentyev.quantum.api.KazanModel;

import kpfu.terentyev.quantum.api.QuantumManager;

/**
 * Created by aleksandrterentev on 29.03.16.
 */
public class Emulator {
    private QuantumProccessorHelper helper = new QuantumProccessorHelper();
    private QuantumMemory memory0;
    private QuantumMemory memory1;

    public Emulator (double maxMemoryFrequency, double minMemoryFrequency, double memoryTimeCycle,
                     int processingUnitsCount){
        memory0 = new QuantumMemory(new QuantumMemoryInfo(maxMemoryFrequency, minMemoryFrequency, memoryTimeCycle), helper);
        memory1 = new QuantumMemory(new QuantumMemoryInfo(maxMemoryFrequency, minMemoryFrequency, memoryTimeCycle), helper);
        processingUnits = new ProcessingUnit[processingUnitsCount];
        this.processingUnitsCount = processingUnitsCount;
        for (int i=0; i< processingUnitsCount; i++){
            processingUnits[i] = new ProcessingUnit(helper);
        }
    }

    private int processingUnitsCount;
    private ProcessingUnit[] processingUnits;

    public int getProcessingUnitsCount() {
        return processingUnitsCount;
    }
    public QuantumMemoryInfo getMemoryInfo(){
        return memory0.getInfo();
    }

    public void initLogicalQubit(double frequency, double timeDelay){
        QuantumMemoryAddress mem0Addr = new QuantumMemoryAddress(frequency, timeDelay, MemoryHalf.HALF_0);
        QuantumMemoryAddress mem1Addr = new QuantumMemoryAddress(frequency, timeDelay, MemoryHalf.HALF_1);
        try {
            QuantumManager.Qubit impulse0 = memory0.initQubitForAddress(mem0Addr);
            QuantumManager.Qubit impulse1 = memory1.initQubitForAddress(mem1Addr);
            //связываем кубиты
            helper.mergeQubits(impulse0, impulse1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void load (QuantumMemoryAddress addressInMemory, ProcessingAddress processingAddress){

        QuantumManager.Qubit qubit = memoryForAddress(addressInMemory).popQubit(addressInMemory);
        if (qubit != null) {
            cellForProcessingAddress(processingAddress).loadQubit(qubit);
        }
    }
    public void save (ProcessingAddress processingAddress, QuantumMemoryAddress addressInMemory){
        QuantumManager.Qubit qubit = cellForProcessingAddress(processingAddress).unloadQubit();
        memoryForAddress(addressInMemory).saveQubit(addressInMemory, qubit);
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
        QuantumManager.Qubit qubit = memoryForAddress(address).popQubit(address);
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
                .cellForUnitAddress(processingAddress.getProccessingUnitCellAddress());
    }

    private QuantumMemory memoryForAddress (QuantumMemoryAddress address){
        return address.getMemoryHalf()==MemoryHalf.HALF_0 ? memory0 : memory1;
    }
}
