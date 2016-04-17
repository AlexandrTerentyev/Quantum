package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import kpfu.terentyev.quantum.api.KazanModel.Emulator;
import kpfu.terentyev.quantum.api.KazanModel.ProcessingAddress;
import kpfu.terentyev.quantum.api.KazanModel.ProcessingUnitCellAddress;
import kpfu.terentyev.quantum.api.KazanModel.QuantumMemoryAddress;

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
    }


    public static void main(String[] args) {
        launch(args);
    }
}
