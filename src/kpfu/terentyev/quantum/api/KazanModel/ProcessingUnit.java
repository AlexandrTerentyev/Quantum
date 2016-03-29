package kpfu.terentyev.quantum.api.KazanModel;

/**
 * Created by aleksandrterentev on 29.03.16.
 */
public class ProcessingUnit {

    ProcessingUnitCell cell0;
    ProcessingUnitCell controlPoint;
    ProcessingUnitCell cell1;

    QuantumProccessorHelper processorHelper;


    boolean areCellsReadyToPerformTransformation (boolean checkControlledPoint){
        boolean result = cell0.qubit != null && cell1.qubit != null;
        if (checkControlledPoint){
            result = result && controlPoint.qubit != null;
        }
        return result;
    }

    void checkCells(boolean checkControlledPoint) throws Exception {
        if (!areCellsReadyToPerformTransformation(checkControlledPoint)){
            throw new Exception("Cells are not ready! Qubits not loaded");
        }
    }

    public void QET(double phase) throws Exception {
        checkCells(false);
        processorHelper.physicalQET(cell0.qubit, cell1.qubit);
    }

    public void cQET (double phase) throws Exception {
        checkCells(true);
        processorHelper.physicalCQET(cell0.qubit, controlPoint.qubit, cell1.qubit);
    }

    public void PHASE (double phase) throws Exception {
        checkCells(false);
        processorHelper.physicalPHASE(cell0.qubit, cell1.qubit);
    }
}
