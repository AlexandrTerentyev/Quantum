package kpfu.terentyev.quantum.emulator.Gates;

import jcuda.cuDoubleComplex;
import kpfu.terentyev.quantum.emulator.Complex;
import kpfu.terentyev.quantum.emulator.QuantumGate;

/**
 * Created by alexandrterentyev on 07.04.15.
 */
public class PauliYGate extends QuantumGate {
    public PauliYGate (){
        this.qubitsNumber=1;
        this.size=2;
    }
    @Override
    public cuDoubleComplex[][] getMatrix() {
        cuDoubleComplex result [][] = {
                {Complex.zero(),cuDoubleComplex.cuCmplx(0, -1)},
                {cuDoubleComplex.cuCmplx(0,1),Complex.zero()}
        };
        return result;
    }
}
