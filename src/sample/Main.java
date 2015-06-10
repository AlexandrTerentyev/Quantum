package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import kpfu.terentyev.quantum_emulator.*;
import kpfu.terentyev.quantum_emulator.Gates.ControlledNotGate;
import kpfu.terentyev.quantum_emulator.Gates.HadamardGate;
import kpfu.terentyev.quantum_emulator.Gates.PauliZGate;

import java.util.HashMap;
import java.util.Map;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
        QuantumRegister register = new QuantumRegister(3);
        register = new QuantumRegister(3, new Complex [] { new Complex(Math.sqrt(2.0/3.0),0),
                Complex.zero(),
                Complex.zero(),
                Complex.zero(),
                Complex.zero(),
                Complex.zero(),
         new Complex(Math.sqrt(1.0/3.0),0),
                Complex.zero(),});
        System.out.print(register+"\n\n");

        QuantumSchemeStepQubitAttributes[][] algMatr  =  {
                {new QuantumSchemeStepQubitAttributes(), new QuantumSchemeStepQubitAttributes("TestGate",false)},
                {new QuantumSchemeStepQubitAttributes(), new QuantumSchemeStepQubitAttributes("TestGate",false)},
                {new QuantumSchemeStepQubitAttributes("PauliZGate", false) ,new QuantumSchemeStepQubitAttributes()}
        };
        String[] mainGateIDs = {"PauliZGate" ,"TestGate"};
        Map<String, QuantumGate> gates = new HashMap<String, QuantumGate>();
        gates.put("PauliZGate", new PauliZGate());
        gates.put("TestGate", new ControlledNotGate());
        QuantumAlgorythm algorythm = new QuantumAlgorythm(algMatr,mainGateIDs,gates);
        register.performAlgorythm(algorythm);
        System.out.print(register);

//        int n=4;
//        int size = (int)Math.pow(2,n);
//        Complex[] vector = new Complex[size];
//        for (int i =0; i<size ; i++){
//            if (i%2==1)
//            vector[i]= new Complex(1.0/Math.sqrt(size/2.0), 0);
//            else {
//                vector[i] = Complex.zero();
//            }
//        }
//        QuantumRegister reg = new QuantumRegister(n, vector);
//        System.out.print(reg);
//        System.out.print("\n\n Qubit value: "+reg.measureQubit(1));
//        System.out.print("\n\n"+reg);

    }


    public static void main(String[] args) {
        launch(args);
    }
}
