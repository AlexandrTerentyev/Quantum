package kpfu.terentyev.quantum.emulator.Gates;

import jcuda.cuDoubleComplex;
import kpfu.terentyev.quantum.emulator.QuantumGate;

/**
 * Created by alexandrterentyev on 07.04.15.
 */
public class HadamardGate extends QuantumGate {
    public HadamardGate (){
        this.qubitsNumber=1;
        this.size=2;
    }
    @Override
    public cuDoubleComplex[][] getMatrix() {
        cuDoubleComplex result [][] = {
                {cuDoubleComplex.cuCmplx((float) ((float) 1/Math.sqrt(2)), 0),cuDoubleComplex.cuCmplx((float)(1/Math.sqrt(2)),0)},
                {cuDoubleComplex.cuCmplx((float)(1/Math.sqrt(2)), 0),cuDoubleComplex.cuCmplx((float)(-1/Math.sqrt(2)),0)}
        };
        return result;
    }
}
