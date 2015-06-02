package kpfu.terentyev.quantum_emulator;

import com.sun.org.apache.xpath.internal.operations.Bool;
import kpfu.terentyev.quantum_emulator.Gates.IdentityGate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by alexandrterentyev on 09.03.15.
 */

//Algorythm is a matrix. Each cell is the QuantumSchemeStepQubitAttributesю
// (Map) gates :{gateID:specifications (Quantum gate)}.
// Generally quantum step and algorythm are quantum gates too.


public class QuantumAlgorythm {

    private int qubitsNumber;
    private int stepsNumber;
//    private AlgorythmStep [] steps;
    QuantumSchemeStepQubitAttributes [][]algorythmMatrix;
    String [] mainGateIDs;
    Map <String, QuantumGate> gates;

    void generateStepMatrix(int step) throws Exception {
        int mainGateIndexesSum = 0;
        int count=0;
        String mainGateID = mainGateIDs[step];
        ArrayList<Number> mainGateQubits = new ArrayList<Number>();
        for (int qubitNum=0; qubitNum<qubitsNumber; qubitNum++){//loop for each qubit
            QuantumSchemeStepQubitAttributes qubitParams = algorythmMatrix[qubitNum][step];
            if (qubitParams.gateID.equals(mainGateID)){
                mainGateIndexesSum+=qubitNum;
                count++;
                mainGateQubits.add(qubitNum);
            }
        }
        Complex result[][] = {{Complex.unit()}};
        if (checkAdjustment(mainGateQubits)){
            //if qubits is near to each other just multiply identity gates and mainGate matrices (tensors)
            for (int currentQubit=0; currentQubit<qubitsNumber;){//loop for each qubit
                Number ind = currentQubit;
                QuantumSchemeStepQubitAttributes qubitParams = algorythmMatrix[currentQubit][step];
                if (qubitParams.gateID.equals(mainGateID)){
                    while ( currentQubit<qubitsNumber && algorythmMatrix[currentQubit][step].gateID.equals(mainGateID)){
                        currentQubit++;
                        ind = currentQubit;
                    }
                    Complex [][] gateMatrx = gates.get(mainGateID).getMatrix();
                    ComplexMath.tensorMultiplication(result,result.length,result.length,
                            gateMatrx, gateMatrx.length, gateMatrx.length);
                }else if (qubitParams.gateID.equals(QuantumSchemeStepQubitAttributes.IdentityGateID)){
                    Complex [][] gateMatrx = QuantumGate.identityGateMatrix();
                    ComplexMath.tensorMultiplication(result,result.length,result.length,
                            gateMatrx, gateMatrx.length, gateMatrx.length);
                    currentQubit++;
                }else {
                    throw new Exception("Two non trivial gates at step!");
                }
            }
        }else{//Group one gate qubits
            //check Type of gravityCenter (maybe needs double)
            int gravityCenter=0;
            if (count>0){
                gravityCenter = mainGateIndexesSum/count;
            }
            int upperQubit, lowerQubit;
        }
    }

    boolean checkAdjustment (ArrayList<Number> listToCheck){
        for (int i=1; i<listToCheck.size(); i++){
            if (listToCheck.get(i).intValue()>listToCheck.get(i).intValue()+1){
                return false;
            }
        }
        return true;
    }
}