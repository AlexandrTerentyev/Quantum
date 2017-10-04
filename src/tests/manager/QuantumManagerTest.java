package tests.manager;

import kpfu.terentyev.quantum.emulator.api.QuantumManager;
import kpfu.terentyev.quantum.emulator.core.Complex;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;


/**
 * Created by aleksandrterentev on 04.10.17.
 */

public class QuantumManagerTest {
    private TestableQuantumManager manager;

    @Before public void init() {manager = new TestableQuantumManager();}
    @After public void  tearDown() {manager = null;}

    @Test
    public void createQubit(){
        QuantumManager.Qubit q = null;
        try {
            q = manager.initNewQubit(Complex.complex(1.0, 0.0), Complex.complex(0.0, 0.0));
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertTrue(manager.allRegisters().size()==1);
        assertTrue(manager.infoForRegisterID(manager.registerIDForQubit(q)).getQubitsNumber() == 1);
    }


}
