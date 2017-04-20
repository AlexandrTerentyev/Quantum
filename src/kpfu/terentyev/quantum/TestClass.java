package kpfu.terentyev.quantum.api.KazanModel;

import jcuda.cuDoubleComplex;
import kpfu.terentyev.quantum.api.QuantumManager;
import kpfu.terentyev.quantum.emulator.Complex;

/**
 * Created by aleksandrterentev on 17.04.16.
 */
public class TestClass {
    public static void testKazanModelEmulator(){


//        QVM initialization

        double MAX_MEMORY_FREQUENCY = 200, MIN_MEMORY_FREQUENCY = 50, MEMORY_TIME_CYCLE = 50;
        int PROCCESSING_UNITS_COUNT = 3;

        Emulator QVM = new Emulator(MAX_MEMORY_FREQUENCY, MIN_MEMORY_FREQUENCY, MEMORY_TIME_CYCLE, PROCCESSING_UNITS_COUNT);


//        Qubits initialization


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
        ProcessingAddress transistor0_c = new ProcessingAddress(currentTransisotorIndex, ProcessingUnitCellAddress.ControlPoint);



//        Transitions
        QVM.load(q1Address, transistor0_0);
        QVM.load(q2Address, transistor0_1);
        QVM.QET(currentTransisotorIndex, Math.PI / 4.0);
        QVM.QET(currentTransisotorIndex, Math.PI / 4.0);
        QVM.save(transistor0_0, q1Address);
        QVM.save(transistor0_1, q2Address);




        System.out.print("q1: " + QVM.measure(q1Address) + "\n");
        System.out.print("q2: " + QVM.measure(q2Address) + "\n");


        System.out.print("End testing");
    }

    static void testQuantumManager(){


        try {

            System.out.print("\n\n-----INITIALIZATION-----\n\n");

            QuantumManager manager = new QuantumManager();
            QuantumManager.Qubit qubit1 = manager.initNewQubit(Complex.zero(), Complex.unit());
            QuantumManager.Qubit qubit2 = manager.initNewQubit(Complex.unit(), Complex.zero());
            QuantumManager.Qubit qubit3 = manager.initNewQubit(Complex.zero(), Complex.unit());
            QuantumManager.Qubit qubit4 = manager.initNewQubit(Complex.zero(), Complex.unit());
            QuantumManager.Qubit qubit5 = manager.initNewQubit(Complex.zero(), Complex.unit());
            QuantumManager.Qubit qubit6 = manager.initNewQubit(Complex.zero(), Complex.unit());
            QuantumManager.Qubit qubit7 = manager.initNewQubit(Complex.zero(), Complex.unit());
            QuantumManager.Qubit qubit8 = manager.initNewQubit(Complex.zero(), Complex.unit());

            System.out.print("Q1: |1>\n");
            System.out.print("Q2: |0>\n");
            System.out.print("Q3: |1>\n");
            System.out.print("Q4: |1>\n");
            System.out.print("Q5: |1>\n");
            System.out.print("Q6: |1>\n");
            System.out.print("Q7: |1>\n");
            System.out.print("Q8: |1>\n");


            System.out.print("\n\n-----OPERATIONS-----\n\n");

            cuDoubleComplex u = Complex.unit();
            cuDoubleComplex z = Complex.zero();

            cuDoubleComplex[][] U = {
                    {u, z, z, z},
                    {z, u, z, z},
                    {z, z, z, u},
                    {z, z, u, z},
            };

            manager.performTransitionForQubits(qubit1, U, qubit2);
            System.out.print("CNOT(Q1, Q2)\n");

            manager.performTransitionForQubits(qubit3, U, qubit2);
            System.out.print("CNOT(Q3, Q2)\n");

            manager.performTransitionForQubits(qubit4, U, qubit1);
            System.out.print("CNOT(Q4, Q1)\n");
//
            manager.performTransitionForQubits(qubit4, U, qubit5);
            System.out.print("CNOT(Q4, Q5)\n");

//            manager.performTransitionForQubits(qubit6, U, qubit2);
//            System.out.print("CNOT(Q6, Q2)\n");
//
//            manager.performTransitionForQubits(qubit7, U, qubit2);
//            System.out.print("CNOT(Q7, Q2)\n");
//
//            manager.performTransitionForQubits(qubit8, U, qubit3);
//            System.out.print("CNOT(Q8, Q3)\n");

            System.out.print("\n\n-----MEASURMENT-----\n\n");
           
            System.out.print("Q1:" + manager.measure(qubit1) + "\n");
            System.out.print("Q2:" + manager.measure(qubit2) + "\n");
            System.out.print("Q3:" + manager.measure(qubit3) + "\n");
            System.out.print("Q4:" + manager.measure(qubit4) + "\n");
            System.out.print("Q5:" + manager.measure(qubit5) + "\n");
//            System.out.print("Q6:" + manager.measure(qubit6) + "\n");
//            System.out.print("Q7:" + manager.measure(qubit7) + "\n");
//            System.out.print("Q8:" + manager.measure(qubit8) + "\n");

        } catch (Exception e) {
            System.out.print("Exception:" + e.getLocalizedMessage() + "\n");
        }


    }



    public static void main(String[] args) {
        testQuantumManager();
//        testKazanModelEmulator();

//        cuDoubleComplex aa = Complex.complex(1, 2);
//        cuDoubleComplex bb = Complex.complex(3, 4);
//
//        cuDoubleComplex [] a = {aa};
//        cuDoubleComplex [] b = {bb};
//
//        cuDoubleComplex  res = kpfu.terentyev.quantum.emulator.ComplexMath.vectorProduct(a, b);
//        cuDoubleComplex res2 = cuDoubleComplex.cuCmul(aa, bb);
//        System.out.print(res);
//        System.out.print(res2);
    }
}
