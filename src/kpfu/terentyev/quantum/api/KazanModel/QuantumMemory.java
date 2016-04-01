package kpfu.terentyev.quantum.api.KazanModel;

import kpfu.terentyev.quantum.api.QuantumManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by aleksandrterentev on 29.03.16.
 */
public class QuantumMemory {
    private QuantumMemoryInfo info;

    public QuantumMemoryInfo getInfo() {
        return info;
    }

    public void setInfo(QuantumMemoryInfo info) {
        this.info = info;
    }

    public QuantumMemory(QuantumMemoryInfo info, QuantumProccessorHelper helper) {
        this.info = info;
        this.helper = helper;
    }

    public QuantumProccessorHelper helper;

    private Map<QuantumMemoryAddress, QuantumManager.Qubit> qubits = new HashMap<QuantumMemoryAddress, QuantumManager.Qubit>();

    boolean addressIsUsed (QuantumMemoryAddress address){
        return qubits.containsKey(address);
    }

    boolean addressIsOutOfRanges (QuantumMemoryAddress address){
        return address.getFrequency() > info.getMaximumAvailableFrequency()
                || address.getFrequency() < info.getMinimumAvailableFrequency()
                || address.getTimeDelay() < info.getTimeInterval();
    }

    public void initQubitForAddress(QuantumMemoryAddress address) throws Exception {
        if (addressIsUsed(address)){
            throw new Exception("This address is already used!");
        }

        if (addressIsOutOfRanges(address)){
            throw new Exception("Address is out of available range");
        }

        QuantumManager.Qubit qubit = helper.initNewQubit();
        qubits.put(address, qubit);
    }

    public void saveQubit (QuantumMemoryAddress address, QuantumManager.Qubit qubit){
        qubits.put(address, qubit);
    }

    public QuantumManager.Qubit popQubit(QuantumMemoryAddress address){
        QuantumManager.Qubit qubit = qubits.get(address);
        qubits.remove(address);
        return qubit;
    }
}
