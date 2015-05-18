package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import kpfu.terentyev.quantum_emulator.*;

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
        QuantumRegister register = new QuantumRegister(1);
        System.out.print(register+"\n\n");
        register.multiplyOnMatrix(QuantumGate.pauliXGateMatrix());
        System.out.print(register);

    }


    public static void main(String[] args) {
        launch(args);
    }
}
