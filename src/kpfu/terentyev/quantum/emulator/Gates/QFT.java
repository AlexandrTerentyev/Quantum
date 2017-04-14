package kpfu.terentyev.quantum.emulator.Gates;

import jcuda.cuComplex;
import kpfu.terentyev.quantum.emulator.QuantumGate;

/**
 * Created by alexandrterentyev on 07.06.15.
 */
public class QFT extends QuantumGate {
    QFT (int qubitsNumber){
        this.qubitsNumber=qubitsNumber;
        size = ((int) Math.pow(2, qubitsNumber));
    }
//    TODO: QFT computing
    @Override
    public cuComplex[][] getMatrix() throws Exception {
        cuComplex [][] result = new cuComplex[size][size];
        for (int i=0; i<size; i++){
            for (int j = 0 ; j<size; j++){
//                result[i][j]=
                ;
            }
        }
        return result;
    }
}
