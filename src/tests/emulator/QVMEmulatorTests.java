package tests.emulator;

import jcuda.cuDoubleComplex;
import kpfu.terentyev.quantum.KazanModel.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by aleksandrterentev on 04.10.2017.
 */
public class QVMEmulatorTests {
    Emulator QVM;

    @Before
    void init() {
        double MAX_MEMORY_FREQUENCY = 200, MIN_MEMORY_FREQUENCY = 50, MEMORY_TIME_CYCLE = 50;
        QVM = new Emulator(MAX_MEMORY_FREQUENCY, MIN_MEMORY_FREQUENCY, MEMORY_TIME_CYCLE, 1);
    }

    @After
    void tearDown(){
        QVM = null;
    }

    @Test
    void testQET(){
        double logicalQubit1Freq = 60, logicalQubit1TimeDelay = 1;

        QuantumMemoryAddress q1Address = new QuantumMemoryAddress(logicalQubit1Freq, logicalQubit1TimeDelay,
                MemoryHalf.HALF_0);
        QuantumMemoryAddress q2Address = new QuantumMemoryAddress(logicalQubit1Freq, logicalQubit1TimeDelay,
                MemoryHalf.HALF_1);
        QVM.initLogicalQubit(q1Address, cuDoubleComplex.cuCmplx(1, 0), cuDoubleComplex.cuCmplx(0,0),
                q2Address, cuDoubleComplex.cuCmplx(0,0), cuDoubleComplex.cuCmplx(1,0));

//        Transistor addresses
        int currentTransisotorIndex = 0;
        ProcessingAddress transistor0_0 = new ProcessingAddress(currentTransisotorIndex, ProcessingUnitCellAddress.Cell0);
        ProcessingAddress transistor0_1 = new ProcessingAddress(currentTransisotorIndex, ProcessingUnitCellAddress.Cell1);
//        ProcessingAddress transistor0_c = new ProcessingAddress(currentTransisotorIndex, ProcessingUnitCellAddress.ControlPoint);



//        Transitions
        QVM.load(q1Address, transistor0_0);
        QVM.load(q2Address, transistor0_1);
        QVM.QET(currentTransisotorIndex, Math.PI / 4.0);
        QVM.QET(currentTransisotorIndex, - Math.PI / 4.0);
        QVM.save(transistor0_0, q1Address);
        QVM.save(transistor0_1, q2Address);

        int q1 = QVM.measure(q1Address);
        int q2 = QVM.measure(q2Address);

        assertTrue(q1 ==0);
        assertTrue(q2 == 1);
    }


}
