package kpfu.terentyev.quantum.api.KazanModel;

/**
 * Created by aleksandrterentev on 01.04.16.
 */
public class QuantumMemoryAddress {
    private double frequency;
    private double timeDelay;

    public double getFrequency() {
        return frequency;
    }

    public double getTimeDelay() {
        return timeDelay;
    }

    public QuantumMemoryAddress(double frequency, double timeDelay) {

        this.frequency = frequency;
        this.timeDelay = timeDelay;
    }
}
