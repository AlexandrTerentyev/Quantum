package tests.memory;

import kpfu.terentyev.quantum.KazanModel.QuantumMemory;
import kpfu.terentyev.quantum.KazanModel.QuantumMemoryInfo;
import kpfu.terentyev.quantum.KazanModel.QuantumProccessorHelper;
import org.junit.Before;

/**
 * Created by aleksandrterentev on 04.10.17.
 */
public class QuantumMemoryTests {

    private QuantumMemory memory;

    @Before
    void init() {
        double MAX_MEMORY_FREQUENCY = 200, MIN_MEMORY_FREQUENCY = 50, MEMORY_TIME_CYCLE = 50;
        memory = new QuantumMemory(new QuantumMemoryInfo(MAX_MEMORY_FREQUENCY, MIN_MEMORY_FREQUENCY, MEMORY_TIME_CYCLE), new QuantumProccessorHelper());
    }
}
