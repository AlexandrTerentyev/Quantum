package kpfu.terentyev.quantum.emulator.core;

import jcuda.cuDoubleComplex;

import java.util.Random;

/**
 * Created by alexandrterentyev on 25.02.15.
 */
public class QuantumRegister {
    private int qubitsNumber;
    private int size;
    private cuDoubleComplex [][] densityMatrix;

    public QuantumRegister (int qubitsNumber) {
        this.setQubitsNumber(qubitsNumber);
    }
    public QuantumRegister (int qubitsNumber, cuDoubleComplex[][] densityMatrix) throws Exception {
        this.qubitsNumber = qubitsNumber;
        size = ((int) Math.pow(2, qubitsNumber));
        this.densityMatrix =densityMatrix;
        if (size != densityMatrix.length){
            throw new Exception();
        }
    }

    public QuantumRegister (int qubitsNumber, cuDoubleComplex [] configuration) throws Exception {
        this.qubitsNumber = qubitsNumber;
        size = ((int) Math.pow(2, qubitsNumber));
        this.densityMatrix = densityMatrixForClearStageConfigurationVector(configuration);
        if (size != densityMatrix.length){
            throw new Exception();
        }
    }


//    public QuantumRegister (Qubit... qubits){
//        qubitsNumber = qubits.length;
//        size = ((int) Math.pow(2, qubitsNumber));
//        vector = qubits[0].getVector();
//        for (int i=1; i< qubits.length; i++){
//            vector = ComplexMath.tensorMultiplication(vector, qubits[i].getVector());
//        }
//    }

    public cuDoubleComplex[][] getDensityMatrix(){
        return densityMatrix;
    }

    public int getQubitsNumber() {
        return qubitsNumber;
    }

    public void setQubitsNumber(int qubitsNumber) {
        this.qubitsNumber = qubitsNumber;
        this.size = ((int) Math.pow(2, qubitsNumber));
        cuDoubleComplex[] vector = new cuDoubleComplex[size];
        vector[0]=Complex.unit();
        for (int i=1; i<vector.length; i++){
            vector[i]=Complex.zero();
        }
    }

    private cuDoubleComplex[][] densityMatrixForClearStageConfigurationVector (cuDoubleComplex[] vector){
        int size = vector.length;
        cuDoubleComplex[][] result = new cuDoubleComplex[size][size];

        result = ComplexMath.ketBraTensorMultiplication(vector, vector);

        return result;
    }

    public void multiplyOnMatrix(cuDoubleComplex[][]matrix) throws Exception {
        if (matrix.length!=size){
            throw new Exception();
        }
        this.densityMatrix=ComplexMath.multiplication(matrix, size, size, densityMatrix, size, size);
    }

    @Override
    public String toString() {
        String result ="";
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                result= result+ densityMatrix[i][j] + " ";
            }
           result = result + "\n";
        }
        return result;
    }

    public void performAlgorythm (QuantumAlgorithm algorythm) throws Exception {
        performTransformationWithMatrix(algorythm.getMatrix());
    }

    public void performAlgorythm (OneStepOneQubitGateAlgorythm algorythm) throws Exception {
        performTransformationWithMatrix(algorythm.getMatrix());
    }

    private void performTransformationWithMatrix (cuDoubleComplex[][] U){
        densityMatrix = ComplexMath.squareMatricesMultiplication(U, densityMatrix, size);
        cuDoubleComplex[][] U_transpose = ComplexMath.hermitianTransposeForMatrix(U, size, size);
        densityMatrix = ComplexMath.squareMatricesMultiplication(densityMatrix, U_transpose, size);
    }


    public void performOneQubitTransitionForAllQubits(cuDoubleComplex[][]matrix){
        cuDoubleComplex [][] resMatrix = {{Complex.unit()}};

        for (int i = 0; i < qubitsNumber; i++){
            resMatrix = ComplexMath.tensorMultiplication(resMatrix, resMatrix.length, resMatrix.length,
                    matrix, matrix.length, matrix.length);
        }

        performTransformationWithMatrix(resMatrix);
    }

/// Измерение
    public int measureQubit (int qubit) throws Exception {
        if (qubit >= qubitsNumber){
            throw  new Exception();
        }
        cuDoubleComplex [][] P0 = ComplexMath.zeroMatrix(size, size);
        int pow2n_q = (int) Math.pow(2, qubitsNumber - qubit);
        int pow2n_q_1 = (int) Math.pow(2, qubitsNumber - qubit-1);
        // нужно пройти по всем состояниям, где текущий кубит 0
        for (int i = 0; i < size; i+=pow2n_q){
            for (int j=i; j<i+pow2n_q_1; j++){
                P0[j][j] = Complex.unit();
            }
        }

        int result = 0;

        cuDoubleComplex[][] P0Transpose = ComplexMath.hermitianTransposeForMatrix(P0, size, size);

        cuDoubleComplex[][] P0Transpose_P0 = ComplexMath.multiplication(P0Transpose, size, size, P0, size, size);
        cuDoubleComplex [][] P0Transpose_P0_ro = ComplexMath.multiplication(P0Transpose_P0,size, size, densityMatrix, size, size);

        double p0Norma =  cuDoubleComplex.cuCreal(ComplexMath.trace(P0Transpose_P0_ro, size));

        //measure and normalize
        cuDoubleComplex [][] Pm;
        if (new Random().nextDouble() > p0Norma){
            result = 1;
//            Configure P1 projector
            Pm = ComplexMath.zeroMatrix(size, size);
            for (int i = pow2n_q_1; i < size; i+=pow2n_q){
                for (int j=i; j<i+pow2n_q_1; j++){
                    Pm[j][j] = Complex.unit();
                }
            }
        }else {
            Pm = P0;
        }

        cuDoubleComplex[][] PmTranspose = ComplexMath.hermitianTransposeForMatrix(Pm, size, size);

        cuDoubleComplex [][] Pm_ro_PmTranspose = ComplexMath.squareMatricesMultiplication(
                ComplexMath.squareMatricesMultiplication(Pm,  densityMatrix, size),
                PmTranspose, size
        );

        cuDoubleComplex [][] PmTranspose_Pm_ro = ComplexMath.squareMatricesMultiplication(
                ComplexMath.squareMatricesMultiplication(PmTranspose, Pm, size),
                densityMatrix, size
                );

        densityMatrix = ComplexMath.multiplication(cuDoubleComplex.cuCmplx(1.0 / ComplexMath.trace(PmTranspose_Pm_ro, size).x,0),
                Pm_ro_PmTranspose, size
                );


        return  result;
    }
}
