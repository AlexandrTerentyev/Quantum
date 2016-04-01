package kpfu.terentyev.quantum.api.KazanModel;

import kpfu.terentyev.quantum.api.QuantumManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by aleksandrterentev on 29.03.16.
 */
public class QuantumMemory {

    private double maximumAvailableFrequency;
    private double minimumAvailableFrequency;

    private double timeInterval;

    public QuantumProccessorHelper helper;

    public QuantumMemory (QuantumProccessorHelper helper){
        this.helper = helper;
    }

    private Map<QuantumMemoryAddress, QuantumManager.Qubit> qubits = new HashMap<QuantumMemoryAddress, QuantumManager.Qubit>();

    boolean addressIsUsed (QuantumMemoryAddress address){
        return qubits.containsKey(address);
    }

    public void initQubitForAddress(QuantumMemoryAddress address) throws Exception {
        if (addressIsUsed(address)){
            throw new Exception("This address is already used!");
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
