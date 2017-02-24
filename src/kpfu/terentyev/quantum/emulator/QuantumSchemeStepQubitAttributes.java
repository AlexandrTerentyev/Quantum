package kpfu.terentyev.quantum.emulator;

/**
 * Created by alexandrterentyev on 19.05.15.
 */
public class QuantumSchemeStepQubitAttributes {
    public String gateID;

    /*
    * This equals to qubit is "upper than other"
    * */
    boolean control;
    public static String IdentityGateID = "IdentityGateID";

    public QuantumSchemeStepQubitAttributes(String gateID, boolean control) {
        this.gateID = gateID;
        this.control = control;
    }
    public QuantumSchemeStepQubitAttributes (){
        this.gateID = IdentityGateID;
        this.control =false;
    }
}
