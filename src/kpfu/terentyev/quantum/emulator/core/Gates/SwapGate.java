package kpfu.terentyev.quantum.emulator.core.Gates;

import jcuda.cuDoubleComplex;
import kpfu.terentyev.quantum.emulator.core.Complex;
import kpfu.terentyev.quantum.emulator.core.QuantumGate;

/**
 * Created by alexandrterentyev on 07.04.15.
 */
public class SwapGate  extends QuantumGate {
    public SwapGate (){
        this.qubitsNumber=2;
        this.size=2;
    }
    @Override
    public cuDoubleComplex[][] getMatrix() {
        cuDoubleComplex result [][] = {
                {Complex.unit(), Complex.zero(), Complex.zero(), Complex.zero()},
                {Complex.zero(), Complex.zero(), Complex.unit(), Complex.zero()},
                {Complex.zero(), Complex.unit(), Complex.zero(), Complex.zero()},
                {Complex.zero(), Complex.zero(), Complex.zero(), Complex.unit()}
        };
        return result;
    }
}
