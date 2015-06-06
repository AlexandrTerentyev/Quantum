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


public class QuantumAlgorythm extends QuantumGate {

    private int qubitsNumber;
    private int stepsNumber;
//    private AlgorythmStep [] steps;
    QuantumSchemeStepQubitAttributes [][]algorythmMatrix;
    String [] mainGateIDs;
    Map <String, QuantumGate> gates;

    public QuantumAlgorythm(QuantumSchemeStepQubitAttributes[][] algorythmMatrix, String [] mainGateIDs,
                            Map<String, QuantumGate> gates) {
        this.algorythmMatrix = algorythmMatrix;
        this.gates = gates;
        this.mainGateIDs = mainGateIDs;
        qubitsNumber = algorythmMatrix.length;
        stepsNumber= algorythmMatrix[0].length;
    }

    Complex[][] generateStepMatrix(int step) throws Exception {
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
//TODO: Need realize an odd qubit count. How near mov qubits to gravity center
                //find upper and lower qubits. Upper index is less than lower index
                upperQubit=-1; lowerQubit=-1; //empty
                int upperPlace = gravityCenter-level;
                int lowerPlace = gravityCenter+1+level;
                int upperIndex = upperPlace;
                int lowerIndex = lowerPlace;


                int distance;
                for (; upperIndex>=0; upperIndex--){
                    QuantumSchemeStepQubitAttributes upperQubitParams = algorythmMatrix[upperIndex][step];
                    if (upperQubit==-1 && upperQubitParams.gateID.equals(mainGateID)){
                        upperQubit=upperIndex;
                        break;
                    }
                }

                for (; lowerIndex<qubitsNumber; lowerIndex++){
                    QuantumSchemeStepQubitAttributes lowerQubitParams = algorythmMatrix[lowerIndex][step];
                    if (lowerQubit == -1 && lowerQubitParams.gateID.equals(mainGateID)){
                        lowerQubit = lowerIndex;
                        break;
                    }
                }
                distance = Math.max(upperPlace-upperQubit, lowerQubit-lowerPlace);

                //move qubits to gravity center + level
                for (; distance>level; distance--){
                    //form swap matrix
                    Complex currentDistanceSwap[][] = {{Complex.unit()}};
                    for (int i=0; i<qubitsNumber; ){
                        if ((i==upperQubit && upperQubit==upperPlace-distance) ||
                                (i==lowerQubit-1 && lowerQubit==lowerPlace+distance)){
                            //need to swap upper gate
                            currentDistanceSwap = ComplexMath.tensorMultiplication(currentDistanceSwap,
                                    currentDistanceSwap.length, currentDistanceSwap.length,
                                    swapGateMatrix,
                                    swapGateMatrix.length, swapGateMatrix.length);
                            i+=2;
                        }else {
                            currentDistanceSwap = ComplexMath.tensorMultiplication(currentDistanceSwap, currentDistanceSwap.length,
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
                    centralMatrix = ComplexMath.tensorMultiplication(centralMatrix, centralMatrix.length, centralMatrix.length,
                            gateMatrix, gateMatrix.length, gateMatrix.length);
                }else {
                    centralMatrix = ComplexMath.tensorMultiplication(centralMatrix, centralMatrix.length, centralMatrix.length,
                            identityMatrx, identityMatrx.length, identityMatrx.length);
                }
            }
            //form common matrix, using matrix associative property, mult all matrices
            result=swapMatrices.get(0).clone();
            for (int i=1 ; i<swapMatrices.size(); i++){
                result=ComplexMath.squareMatricesMultiplication(result, swapMatrices.get(i), result.length);
            }
            result=ComplexMath.squareMatricesMultiplication(result, centralMatrix, result.length);
            for (int i=swapMatrices.size()-1 ; i>=0; i--){
                result=ComplexMath.squareMatricesMultiplication(result, swapMatrices.get(i), result.length);
            }
        }
        return result;
    }

    boolean checkAdjustment (ArrayList<Number> listToCheck){
        for (int i=1; i<listToCheck.size(); i++){
            if (listToCheck.get(i).intValue()>listToCheck.get(i-1).intValue()+1){
                return false;
            }
        }
        return true;
    }

    @Override
    public Complex[][] getMatrix() throws Exception {
        Complex [][]result = generateStepMatrix(stepsNumber - 1);
        for (int i=stepsNumber-2; i>=0; i--){
            result=ComplexMath.squareMatricesMultiplication(result, generateStepMatrix(i), result.length);
        }
        return result;
    }
}