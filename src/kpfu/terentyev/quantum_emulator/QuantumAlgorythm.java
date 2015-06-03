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
        Complex [][] gateMatrix = gates.get(mainGateID).getMatrix();
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
                    ComplexMath.tensorMultiplication(result,result.length,result.length,
                            gateMatrix, gateMatrix.length, gateMatrix.length);
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
            int levelNumber = mainGateQubits.size()/2;
            ArrayList <Complex[][]> swapMatrices = new ArrayList<Complex[][]>();
            Complex[][] centralMatrix = {{Complex.unit()}}; //matrix perfomed main gate when all qubits are near
            Complex[][] swapGateMatrix = QuantumGate.swapGateMatrix();
            Complex [][] identityMatrx = QuantumGate.identityGateMatrix();

            for (int level=0; level< levelNumber; level++){

                //find upper and lower qubits. Upper index is less than lower index
                upperQubit=-1; lowerQubit=-1; //empty
                int distance=level;
                for (; distance<=qubitsNumber/2; distance++){
                    QuantumSchemeStepQubitAttributes upperQubitParams = algorythmMatrix[gravityCenter-distance][step];
                    QuantumSchemeStepQubitAttributes lowerQubitParams = algorythmMatrix[gravityCenter+distance][step];
                    if (upperQubit==-1 && upperQubitParams.gateID.equals(mainGateID)){
                        upperQubit=gravityCenter-distance;
                    }
                    if (lowerQubit == -1 && lowerQubitParams.gateID.equals(mainGateID)){
                        lowerQubit = gravityCenter + distance;
                    }

                    if (lowerQubit>-1 && upperQubit>-1){
                        break;
                    }
                }

                //move qubits to gravity center + level
                for (; distance>level; distance--){
                    //form swap matrix
                    Complex currentDistanceSwap[][] = {{Complex.unit()}};
                    for (int i=0; i<qubitsNumber; ){
                        if ((i==upperQubit && upperQubit==gravityCenter-distance) ||
                                (i==lowerQubit-1 && lowerQubit==gravityCenter+distance)){
                            //need to swap upper gate
                            ComplexMath.tensorMultiplication(currentDistanceSwap, currentDistanceSwap.length,
                                    currentDistanceSwap.length, swapGateMatrix,
                                    swapGateMatrix.length, swapGateMatrix.length);
                            i+=2;
                        }else {
                            ComplexMath.tensorMultiplication(currentDistanceSwap, currentDistanceSwap.length,
                                    currentDistanceSwap.length, identityMatrx,
                                    identityMatrx.length, identityMatrx.length);
                            i++;
                        }
                    }
                    swapMatrices.add(currentDistanceSwap);
                }

            }

            //form central matrix after swaps
            for (int i=0; i<qubitsNumber; i++){
                if (i==gravityCenter-levelNumber/2){
                    ComplexMath.tensorMultiplication(centralMatrix, centralMatrix.length, centralMatrix.length,
                            gateMatrix, gateMatrix.length, gateMatrix.length);
                }else {
                    ComplexMath.tensorMultiplication(centralMatrix, centralMatrix.length, centralMatrix.length,
                            identityMatrx, identityMatrx.length, identityMatrx.length);
                }
            }
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