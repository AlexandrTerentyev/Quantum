package kpfu.terentyev.quantum_emulator.Gates;

import kpfu.terentyev.quantum_emulator.Complex;
import kpfu.terentyev.quantum_emulator.QuantumGate;

/**
 * Created by alexandrterentyev on 07.04.15.
 */
public class PauliXGate extends QuantumGate {
    public PauliXGate (){
        this.qubitsNumber=1;
        this.size=2;
    }
    @Override
    public Complex[][] getMatrix() {
        Complex result [][] = {
                {Complex.zero(),Complex.unit()},
                {Complex.unit(),Complex.zero()}
        };
        return result;
    }
}
