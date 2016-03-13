package kpfu.terentyev.quantum.emulator;

import kpfu.terentyev.quantum.emulator.Gates.UGate;

import java.util.HashMap;

/**
 * Created by aleksandrterentev on 08.03.16.
 */
public class OneStepTwoQubitControlledGateAlgorythm extends QuantumAlgorithm {
    public OneStepTwoQubitControlledGateAlgorythm (int qubitsInRegister,
                                                   int controllingQubitPosition,
                                                   int controlledQubitPosition,
                                                   Complex[][] transformationMatrix) throws Exception {
        stepsNumber = 1;
        QuantumSchemeStepQubitAttributes [][] algSheme = new QuantumSchemeStepQubitAttributes[qubitsInRegister][1];
        String gateId = "ControlledGate";
        for (int i = 0; i < qubitsInRegister; i++){
            if (i == controllingQubitPosition){
                algSheme[i][0] = new QuantumSchemeStepQubitAttributes(gateId, false);
            }else if (i == controlledQubitPosition){
                algSheme[i][0] = new QuantumSchemeStepQubitAttributes(gateId, true);
            }else {
                algSheme[i][0] = new QuantumSchemeStepQubitAttributes();
            }
        }
        gates = new HashMap<String, QuantumGate>();;
        QuantumGate gate = new UGate(2, transformationMatrix);
        gates.put(gateId, gate);
        algorithmSchemeMatrix = algSheme;
    }
}
