package kpfu.terentyev.quantum.emulator.Gates;

import jcuda.cuComplex;
import kpfu.terentyev.quantum.emulator.Complex;
import kpfu.terentyev.quantum.emulator.QuantumGate;

/**
 * Created by alexandrterentyev on 07.04.15.
 */
public class PauliZGate extends QuantumGate {
    public PauliZGate (){
        this.qubitsNumber=1;
        this.size=2;
    }
    @Override
    public cuComplex[][] getMatrix() {
        cuComplex result [][] = {
                {cuComplex.cuCmplx(0,1), Complex.zero()},
                {Complex.zero(),cuComplex.cuCmplx(0, -1)}
        };
        return result;
    }
}
