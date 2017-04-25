package kpfu.terentyev.quantum.KazanModel;

import jcuda.cuDoubleComplex;
import kpfu.terentyev.quantum.emulator.api.QuantumManager;
import kpfu.terentyev.quantum.emulator.core.Complex;

import java.util.HashMap;
import java.util.Map;

import static kpfu.terentyev.quantum.emulator.api.QuantumManager.*;

/**
 * Created by aleksandrterentev on 29.03.16.
 */
public class QuantumMemory {
    private QuantumMemoryInfo info;

    QuantumMemoryInfo getInfo() {
        return info;
    }

    void setInfo(QuantumMemoryInfo info) {
        this.info = info;
    }

    QuantumMemory (QuantumMemoryInfo info, QuantumProccessorHelper helper) {
        this.info = info;
        this.helper = helper;
    }

    QuantumProccessorHelper helper;

    private Map<QuantumMemoryAddress, Qubit> qubits = new HashMap<QuantumMemoryAddress, QuantumManager.Qubit>();

    private boolean addressIsUsed (QuantumMemoryAddress address){
        return qubits.containsKey(address);
    }

    private boolean addressIsOutOfRanges (QuantumMemoryAddress address){
        return address.getFrequency() > info.getMaximumAvailableFrequency()
                || address.getFrequency() < info.getMinimumAvailableFrequency()
                || address.getTimeDelay() > info.getTimeInterval();
    }

    Qubit initQubitForAddress(QuantumMemoryAddress address,
                              cuDoubleComplex alpha, cuDoubleComplex beta) throws Exception {
        if (addressIsUsed(address)){
            throw new Exception("This address is already used!");
        }

        if (addressIsOutOfRanges(address)){
            throw new Exception("Address is out of available range");
        }

        Qubit qubit = helper.initNewQubit(alpha, beta);
        qubits.put(address, qubit);
        return qubit;
    }

    Qubit initQubitForAddress(QuantumMemoryAddress address) throws Exception {
        cuDoubleComplex alpha = Complex.zero(), beta = Complex.zero();
        switch (address.getMemoryHalf()){
            case HALF_0:
                alpha = Complex.unit();
                break;
            case HALF_1:
                beta = Complex.unit();
                break;
        }
        return initQubitForAddress(address, alpha, beta);
    }


    void saveQubit (QuantumMemoryAddress address, Qubit qubit){
        qubits.put(address, qubit);
    }

    Qubit popQubit(QuantumMemoryAddress address){
        Qubit qubit = qubits.get(address);
        qubits.remove(address);
        return qubit;
    }
}