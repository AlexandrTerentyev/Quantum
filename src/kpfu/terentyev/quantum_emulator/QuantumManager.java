package kpfu.terentyev.quantum_emulator;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by aleksandrterentev on 24.01.16.
 */

public class QuantumManager {

    public class Qubit {
        // Можно было сделать его частным случаем регистра, но пока удобнее хранить идентификаторы регистров и их номер в регистре
        private String registerAddress;
        private int addressInRegister;
        private Qubit (String registerAddress, int addressInRegister){
            this.registerAddress = registerAddress;
            this.addressInRegister = addressInRegister;
        }
    }

    private class RegisterInfo{
        QuantumRegister register;
        ArrayList <Qubit> qubits;

        public RegisterInfo(ArrayList<Qubit> qubits, QuantumRegister register) {
            this.qubits = qubits;
            this.register = register;
        }
    }

    // This class must contain quantum registeres
    HashMap <String, RegisterInfo> registers;

//    Qubit creation
    public Qubit initNewQubit(){
        QuantumRegister newRegister = new QuantumRegister(1);
        String registerID = Double.toString(new Date().getTime());
        Qubit newQubit = new Qubit(registerID, 0);
        ArrayList<Qubit> qubits = new ArrayList<Qubit>();
        qubits.add(newQubit);
        registers.put(registerID, new RegisterInfo(qubits, newRegister));
        return newQubit;
    }


    //Service functions
    protected RegisterInfo checkAndMergeRegistersIfNeedForQubits (Qubit... qubits) throws Exception {
        ArrayList<String> usedRegisterAddresses = new ArrayList<String>();
        for (Qubit qubit: qubits) {
            if (!usedRegisterAddresses.contains(qubit.registerAddress)){
                usedRegisterAddresses.add(qubit.registerAddress);
            }
        }

        if (usedRegisterAddresses.size()==1){
            return registers.get(usedRegisterAddresses.get(0));
        }

        //       Create new register merged registers
        Complex [] newRegisterConfiguration = {Complex.unit()};
        ArrayList <Qubit> newRegisterQubits = new ArrayList<Qubit>();
        String newRegisterAddress = Double.toString(new Date().getTime());

        for (String registerAddress: usedRegisterAddresses) {
            RegisterInfo currentRegisterInfo = registers.get(registerAddress);
            newRegisterConfiguration = ComplexMath.tensorMultiplication(newRegisterConfiguration, currentRegisterInfo.register.getVector());
            for (Qubit qubit: currentRegisterInfo.qubits){
                newRegisterQubits.add(qubit);
                qubit.registerAddress = newRegisterAddress;
                qubit.addressInRegister = newRegisterQubits.size()-1;
            }
            registers.remove(registerAddress);
        }

        QuantumRegister newRegister = new QuantumRegister(newRegisterQubits.size(), newRegisterConfiguration);

        RegisterInfo newRegisterInfo =   new RegisterInfo(newRegisterQubits, newRegister);
        registers.put(newRegisterAddress, newRegisterInfo);

        return newRegisterInfo;
    }


    // Operations

}
