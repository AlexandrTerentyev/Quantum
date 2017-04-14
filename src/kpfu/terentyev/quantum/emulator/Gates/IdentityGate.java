package kpfu.terentyev.quantum.emulator.Gates;

import jcuda.cuComplex;
import kpfu.terentyev.quantum.emulator.Complex;
import kpfu.terentyev.quantum.emulator.QuantumGate;

/**
 * Created by alexandrterentyev on 07.04.15.
 */
public class IdentityGate extends QuantumGate {
    public IdentityGate (){
        this.qubitsNumber=1;
        this.size=2;
    }
    @Override
    public cuComplex[][] getMatrix() {
        cuComplex result [][] = {
                {Complex.unit(),Complex.zero()},
                {Complex.zero(),Complex.unit()}
        };
        return result;
    }
}
