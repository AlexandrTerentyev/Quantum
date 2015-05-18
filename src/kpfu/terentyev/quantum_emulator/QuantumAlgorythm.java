package kpfu.terentyev.quantum_emulator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by alexandrterentyev on 09.03.15.
 */

//Algorythm is array of steps. Every step is represented by map {qubitNumber:gateId, params...}, and gates summary:
// (Map) gates :{gateID:specifications (Quantum gate)}
//

public class QuantumAlgorythm {
    final String GateIDKey = "GateID";
    private class AlgorythmStep{
        int qubitsNumber;
        /**
         * Map step : {
         *     QubitNum: gateID or GateID and params
         * }
         */
        Map <Number, Object> step;
        /**
         * Map gates:
         * {
         *     GateID:QuantumGate gate
         * }
         *
         */
        Map <String, QuantumGate> gates;
        void generateStepMatrix(){

        }
    }
    private  class AlgorythmOneQubitStep extends AlgorythmStep{
        int qubitsNumber;
        /**
         * Map step : {
         *     QubitNum:{
         *         gateID:id,...
         *     }
         * }
         */
        Map <Number, Object> step;

        /**
         * Map gates:
         * {
         *     GateID:QuantumGate gate
         * }
         *
         */
        Map <String, QuantumGate> gates;

        String mainGateID;

        Complex [][] stepMatrix;
        public AlgorythmOneQubitStep(Map <String, QuantumGate> gates, int qubitsNumber, Map<Number, Object> step, String mainGateID){
            this.gates = gates;
            this.qubitsNumber = qubitsNumber;
            this.step=step;
            this.mainGateID = mainGateID;
        }
        @Override
        void generateStepMatrix(){
            int mainGateIndexesSum = 0;
            int count=0;
            ArrayList<Number> mainGateQubits = new ArrayList<Number>();
            for (int i=0; i<qubitsNumber; i++){//loop for each qubit
                Number ind = i;
                Map <String, Object> gateParams = (Map<String, Object>) step.get(ind);
                String gateID = (String) gateParams.get(GateIDKey);
                if (gateID.equals(mainGateID)){
                    mainGateIndexesSum+=i;
                    count++;
                }
            }
            //check Type of gravityCenter (maybe needs double)
            int gravityCenter=0;
            if (count>0){
                gravityCenter = mainGateIndexesSum/count;
            }

        }
    }

    private int qubitsNumber;
    private int stepsNumber;
    private AlgorythmStep [] steps;
}