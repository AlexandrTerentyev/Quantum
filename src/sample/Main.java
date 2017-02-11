package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import kpfu.terentyev.quantum.api.KazanModel.*;

public class Main extends Application {

    public class TestClass{
        String value;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
        testKazanModelEmulator();
    }

//    void CNOT (Qubit controlQubit, Qubit targetQubit){
//        LOAD(targetQubit.logicalZeroAddress, transistor.0);
//        LOAD (targetQubit.logicalUnitAddress, transistor.1);
//        LOAD(controlQubit.logicalZeroAddress, transistor.controlPoint);
//
//        QET();
//
//        SAVE(transistor.0, targetQubit.logicalZeroAddress);
//        SAVE(transistor.1, targetQubit.logicalUnitAddress);
//        SAVE(transistor.controlPoint, controlQubit);
//
//        LOAD(controlQubit.logicalZeroAddress, transistor.0);
//        LOAD (controlQubit.logicalUnitAddress, transistor.1);
//
//        PHASE(Pi/2);
//
//        SAVE(transistor.1, controlQubit.logicalUnitAddress);
//        SAVE(transistor.0, controlQubit.logicalZeroAddress);
//    }

    public static void testKazanModelEmulator(){
//        QVM initialization
        Emulator QVM = new Emulator(200, 50, 50, 3);


//        Qubits initialization


        double logicalQubit1Freq = 60, logicalQubit1TimeDelay = 1;
        QuantumMemoryAddress q1Address = new QuantumMemoryAddress(logicalQubit1Freq, logicalQubit1TimeDelay,
                MemoryHalf.HALF_0);
        QuantumMemoryAddress q2Address = new QuantumMemoryAddress(logicalQubit1Freq, logicalQubit1TimeDelay,
                MemoryHalf.HALF_1);
        QVM.initLogicalQubit(q1Address, q2Address);

//        Tranzistor addresses
        ProcessingAddress tranzistor0_0 = new ProcessingAddress(0, ProcessingUnitCellAddress.Cell0);
        ProcessingAddress tranzistor0_1 = new ProcessingAddress(0, ProcessingUnitCellAddress.Cell1);
        ProcessingAddress tranzistor0_c = new ProcessingAddress(0, ProcessingUnitCellAddress.ControlPoint);



//        Transitions
        QVM.load(q1Address, tranzistor0_0);
        QVM.load(q2Address, tranzistor0_1);
        QVM.QET(0, Math.PI / 3.0);
        QVM.save(tranzistor0_0, q1Address);
        QVM.save(tranzistor0_1, q2Address);


        System.out.print("q1: " + QVM.measure(q1Address) + "\n");
        System.out.print("q2: " + QVM.measure(q2Address) + "\n");


        System.out.print("End testing");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
