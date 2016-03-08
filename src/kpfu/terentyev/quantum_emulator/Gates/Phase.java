package kpfu.terentyev.quantum_emulator.Gates;

import kpfu.terentyev.quantum_emulator.Complex;
import kpfu.terentyev.quantum_emulator.QuantumGate;

/**
 * Created by aleksandrterentev on 08.03.16.
 */
public class Phase extends QuantumGate {

    private double thetaInRadians;

    public Phase (double thetaInRadians){
        this.thetaInRadians = thetaInRadians;
    }

    @Override
    public Complex[][] getMatrix() throws Exception {
        Complex [][] result = {
                {Complex.unit(), Complex.zero(), Complex.zero(), Complex.zero()},
                {},
                {},
                {Complex.zero(),Complex.zero(),Complex.zero(), }
        };
    }
}
