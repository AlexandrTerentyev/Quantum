package kpfu.terentyev.quantum.api.KazanModel;

/**
 * Created by aleksandrterentev on 17.04.16.
 */
public class TestClass {
    public static void testKazanModelEmulator(){
        Emulator QVM = new Emulator(200, 50, 50, 3);
        QuantumMemoryAddress q1Address = new QuantumMemoryAddress(60, 1);
        QuantumMemoryAddress q2Address = new QuantumMemoryAddress(60, 2);

        QuantumMemoryAddress q3Address = new QuantumMemoryAddress(60, 3);
        QuantumMemoryAddress q4Address = new QuantumMemoryAddress(60, 4);

        QVM.initQubitForAddress(q1Address);
        QVM.initQubitForAddress(q2Address);
        QVM.initQubitForAddress(q3Address);
        QVM.initQubitForAddress(q4Address);

        ProcessingAddress tranzistor0_0 = new ProcessingAddress(0, ProcessingUnitCellAddress.Cell0);
        ProcessingAddress tranzistor0_1 = new ProcessingAddress(0, ProcessingUnitCellAddress.Cell1);
        ProcessingAddress tranzistor0_c = new ProcessingAddress(0, ProcessingUnitCellAddress.ControlPoint);

//        связываем первую пару кубитов в один логический
        QVM.load(q1Address, tranzistor0_0);
        QVM.load(q2Address, tranzistor0_1);

        QVM.QET(0, 3.14);

        QVM.save(tranzistor0_0, q1Address);
        QVM.save(tranzistor0_1, q2Address);

//        связываем вторую пару кубитов в один логический
        QVM.load(q3Address, tranzistor0_0);
        QVM.load(q4Address, tranzistor0_1);

        QVM.QET(0, 0.0);

        QVM.save(tranzistor0_0, q3Address);
        QVM.save(tranzistor0_1, q4Address);


        System.out.print("End testing");
    }


    public static void main(String[] args) {
        testKazanModelEmulator();
    }
}
