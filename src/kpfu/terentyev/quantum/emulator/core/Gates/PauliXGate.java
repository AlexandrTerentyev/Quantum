package kpfu.terentyev.quantum.emulator.core.Gates;

import jcuda.cuDoubleComplex;
import kpfu.terentyev.quantum.emulator.core.Complex;
import kpfu.terentyev.quantum.emulator.core.QuantumGate;

/**
 * Created by alexandrterentyev on 07.04.15.
 */
public class PauliXGate extends QuantumGate {
    public PauliXGate (){
        this.qubitsNumber=1;
        this.size=2;
    }
    @Override
    public cuDoubleComplex[][] getMatrix() {
        cuDoubleComplex result [][] = {
                {Complex.zero(),Complex.unit()},
                {Complex.unit(),Complex.zero()}
        };
        return result;
    }
}
