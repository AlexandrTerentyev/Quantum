package kpfu.terentyev.quantum_emulator.Gates;

import kpfu.terentyev.quantum_emulator.Complex;
import kpfu.terentyev.quantum_emulator.QuantumGate;

/**
 * Created by alexandrterentyev on 07.04.15.
 */
public class HadamardGate extends QuantumGate {
    public HadamardGate (){
        this.qubitsNumber=1;
        this.size=2;
    }
    @Override
    public Complex[][] getMatrix() {
        Complex result [][] = {
                {new Complex(1/Math.sqrt(2), 0),new Complex(1/Math.sqrt(2),0)},
                {new Complex(1/Math.sqrt(2), 0),new Complex(-1/Math.sqrt(2),0)}
        };
        return result;
    }
}
