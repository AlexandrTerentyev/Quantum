package kpfu.terentyev.quantum.emulator.core.Gates;

import jcuda.cuDoubleComplex;
import kpfu.terentyev.quantum.emulator.core.Complex;
import kpfu.terentyev.quantum.emulator.core.QuantumGate;

/**
 * Created by alexandrterentyev on 07.04.15.
 */
public class PauliZGate extends QuantumGate {
    public PauliZGate (){
        this.qubitsNumber=1;
        this.size=2;
    }
    @Override
    public cuDoubleComplex[][] getMatrix() {
        cuDoubleComplex result [][] = {
                {cuDoubleComplex.cuCmplx(0,1), Complex.zero()},
                {Complex.zero(),cuDoubleComplex.cuCmplx(0, -1)}
        };
        return result;
    }
}
