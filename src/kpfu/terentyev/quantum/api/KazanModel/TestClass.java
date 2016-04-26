package kpfu.terentyev.quantum.api.KazanModel;

/**
 * Created by aleksandrterentev on 17.04.16.
 */
public class TestClass {
    public static void testKazanModelEmulator(){
//        QVM initialization
        Emulator QVM = new Emulator(200, 50, 50, 3);


//        Qubits initialization
        double logicalQubit1Freq = 60, logicalQubit1TimeDelay = 1;
        QuantumMemoryAddress q1Address = new QuantumMemoryAddress(logicalQubit1Freq, logicalQubit1TimeDelay,
                MemoryHalf.HALF_0);
        QuantumMemoryAddress q2Address = new QuantumMemoryAddress(logicalQubit1Freq, logicalQubit1TimeDelay,
                MemoryHalf.HALF_1);

        double logicalQubit2Freq = 60, logicalQubit2TimeDelay = 2;
        QuantumMemoryAddress q3Address = new QuantumMemoryAddress(logicalQubit2Freq, logicalQubit2TimeDelay,
                MemoryHalf.HALF_0);
        QuantumMemoryAddress q4Address = new QuantumMemoryAddress(logicalQubit2Freq, logicalQubit2TimeDelay,
                MemoryHalf.HALF_1);

        QVM.initLogicalQubit(q1Address, q2Address);
        QVM.initLogicalQubit(q3Address, q4Address);

//        Tranzistor addresses
        ProcessingAddress tranzistor0_0 = new ProcessingAddress(0, ProcessingUnitCellAddress.Cell0);
        ProcessingAddress tranzistor0_1 = new ProcessingAddress(0, ProcessingUnitCellAddress.Cell1);
        ProcessingAddress tranzistor0_c = new ProcessingAddress(0, ProcessingUnitCellAddress.ControlPoint);


//        Transitions
//        QVM.load(q1Address, tranzistor0_0);
//        QVM.load(q2Address, tranzistor0_1);
//        QVM.PHASE(0, 3.14);
//        QVM.save(tranzistor0_0, q1Address);
//        QVM.save(tranzistor0_1, q2Address);


        System.out.print("q1: " + QVM.measure(q1Address) + "\n");
        System.out.print("q2: " + QVM.measure(q2Address) + "\n");

        System.out.print("End testing");
    }


    public static void main(String[] args) {
        testKazanModelEmulator();
    }
}
