package kpfu.terentyev.quantum_emulator.Gates;

import kpfu.terentyev.quantum_emulator.Complex;
import kpfu.terentyev.quantum_emulator.QuantumGate;

/**
 * Created by alexandrterentyev on 07.04.15.
 */
public class ToffoliGate extends QuantumGate {
    @Override
    public Complex[][] getMatrix() {
        Complex result [][] = {
                {Complex.unit(), Complex.zero(), Complex.zero(), Complex.zero(),
                        Complex.zero(), Complex.zero(), Complex.zero(), Complex.zero()},
                {Complex.zero(), Complex.unit(), Complex.zero(), Complex.zero(),
                        Complex.zero(), Complex.zero(), Complex.zero(), Complex.zero()},
                {Complex.zero(), Complex.zero(), Complex.unit(), Complex.zero(),
                        Complex.zero(), Complex.zero(), Complex.zero(), Complex.zero()},
                {Complex.zero(), Complex.zero(), Complex.zero(), Complex.unit(),
                        Complex.zero(), Complex.zero(), Complex.zero(), Complex.zero()},
                {Complex.zero(), Complex.zero(), Complex.zero(), Complex.zero(),
                        Complex.unit(), Complex.zero(), Complex.zero(), Complex.zero()},
                {Complex.zero(), Complex.zero(), Complex.zero(), Complex.zero(),
                        Complex.zero(), Complex.unit(), Complex.zero(), Complex.zero()},
                {Complex.zero(), Complex.zero(), Complex.zero(), Complex.zero(),
                        Complex.zero(), Complex.zero(), Complex.zero(), Complex.unit()},
                {Complex.zero(), Complex.zero(), Complex.zero(), Complex.zero(),
                        Complex.zero(), Complex.zero(), Complex.unit(), Complex.zero()}
        };
        return result;
    }
}
