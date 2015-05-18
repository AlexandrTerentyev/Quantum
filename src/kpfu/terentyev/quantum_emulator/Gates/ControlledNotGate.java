package kpfu.terentyev.quantum_emulator.Gates;

import kpfu.terentyev.quantum_emulator.Complex;
import kpfu.terentyev.quantum_emulator.QuantumGate;

/**
 * Created by alexandrterentyev on 07.04.15.
 */
public class ControlledNotGate extends QuantumGate {
    public ControlledNotGate (){
        this.qubitsNumber=2;
        this.size=4;
    }
    @Override
    public Complex[][] getMatrix() {
        Complex result [][] = {
                {Complex.unit(), Complex.zero(), Complex.zero(), Complex.zero()},
                {Complex.zero(), Complex.unit(), Complex.zero(), Complex.zero()},
                {Complex.zero(), Complex.zero(), Complex.zero(), Complex.unit()},
                {Complex.zero(), Complex.zero(), Complex.unit(), Complex.zero()}
        };
        return result;
    }
}
