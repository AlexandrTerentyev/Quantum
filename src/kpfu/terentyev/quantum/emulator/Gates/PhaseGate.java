package kpfu.terentyev.quantum.emulator.Gates;

import jcuda.cuComplex;
import kpfu.terentyev.quantum.emulator.QuantumGate;
import kpfu.terentyev.quantum.emulator.Complex;

/**
 * Created by aleksandrterentev on 08.03.16.
 */
public class PhaseGate extends QuantumGate {

    private double thetaPhaseInRadians;

    public PhaseGate(double thetaPhaseInRadians){
        qubitsNumber = 1;
        size = 2;
        this.thetaPhaseInRadians = thetaPhaseInRadians;
    }

    @Override
    public cuComplex[][] getMatrix() throws Exception {
        cuComplex [][] result = {
                {cuComplex.cuCmplx((float)Math.cos(-thetaPhaseInRadians/2.0),(float) Math.sin(-thetaPhaseInRadians/2.0)), Complex.zero()},
                {Complex.zero(), cuComplex.cuCmplx((float)Math.cos(thetaPhaseInRadians/2.0), (float) Math.sin(thetaPhaseInRadians/2.0))},
        };
        return result;
    }
}
