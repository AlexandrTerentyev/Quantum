package kpfu.terentyev.quantum.emulator;

import jcuda.cuDoubleComplex;
import kpfu.terentyev.quantum.emulator.Gates.UGate;

import java.util.HashMap;
import java.util.List;

/**
 * Created by aleksandrterentev on 02.04.16.
 */

/**
 * Algorythm matrix must math to qubits order in algorythm scheme.
 *
 * To not use controlQubit pass value NotAnIndex
 * */

public class OneStepAlgorythm extends QuantumAlgorithm {

    public static final int NotAnIndex = -1;

    public OneStepAlgorythm(int qubitsInRegister,
                            int controlQubitIndex,
                            List<Integer> gateQubitIndexes,
                            cuDoubleComplex[][] transformationMatrix) throws Exception {
        stepsNumber = 1;
        QuantumSchemeStepQubitAttributes [][] algSheme = new QuantumSchemeStepQubitAttributes[qubitsInRegister][1];
        String gateId = "Gate";
        for (int i = 0; i < qubitsInRegister; i++){
            if (gateQubitIndexes.contains(new Integer(i))){
                algSheme[i][0] = new QuantumSchemeStepQubitAttributes(gateId, false);
            }else if(i == controlQubitIndex){
                algSheme[i][0] = new QuantumSchemeStepQubitAttributes(gateId, true);
            }else{
                algSheme[i][0] = new QuantumSchemeStepQubitAttributes();
            }
        }
        gates = new HashMap<String, QuantumGate>();
        mainGateIDs = new String[]{gateId};
        QuantumGate gate = new UGate(gateQubitIndexes.size(), transformationMatrix);
        gates.put(gateId, gate);
        algorithmSchemeMatrix = algSheme;
        qubitsNumber = qubitsInRegister;
        size = (int) Math.pow(2, qubitsNumber);
    }
}
