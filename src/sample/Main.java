package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import kpfu.terentyev.quantum_emulator.*;
import kpfu.terentyev.quantum_emulator.Gates.ControlledNotGate;
import kpfu.terentyev.quantum_emulator.Gates.HadamardGate;

import java.util.HashMap;
import java.util.Map;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
//        System.out.print(QuantumGate.identityGate() + "\n");
//        System.out.print(QuantumGate.hadamardGate()+ "\n");
//        System.out.print(QuantumGate.pauliXGate()+"\n");
//        System.out.print(QuantumGate.pauliYGate()+"\n");
//        System.out.print(QuantumGate.pauliZGate()+"\n");
//        System.out.print(QuantumGate.swapGate()+"\n");
//        System.out.print(QuantumGate.controlledNOTGate()+"\n");
//        System.out.print(QuantumGate.toffoliGate()+"\n");
//        System.out.print(QuantumGate.fredkinGate()+"\n");
//        QuantumRegister register = new QuantumRegister(3);
//        System.out.print(register+"\n\n");
//
//        QuantumSchemeStepQubitAttributes[][] algMatr  =  {{new QuantumSchemeStepQubitAttributes("TestGate",false)},
//                {new QuantumSchemeStepQubitAttributes()},
//                {new QuantumSchemeStepQubitAttributes("TestGate",false)}};
//        String[] mainGateIDs = {"TestGate"};
//        Map<String, QuantumGate> gates = new HashMap<String, QuantumGate>();
//        gates.put("TestGate", new ControlledNotGate());
//        QuantumAlgorythm algorythm = new QuantumAlgorythm(algMatr,mainGateIDs,gates);
//        register.performAlgorythm(algorythm);
//        System.out.print(register);

        int n=4;
        int size = (int)Math.pow(2,n);
        Complex[] vector = new Complex[size];
        for (int i =0; i<size ; i++){
            if (i%2==1)
            vector[i]= new Complex(1.0/Math.sqrt(size/2.0), 0);
            else {
                vector[i] = Complex.zero();
            }
        }
        QuantumRegister reg = new QuantumRegister(n, vector);
        System.out.print(reg);
        System.out.print("\n\n Qubit value: "+reg.measureQubit(1));
        System.out.print("\n\n"+reg);

    }


    public static void main(String[] args) {
        launch(args);
    }
}
