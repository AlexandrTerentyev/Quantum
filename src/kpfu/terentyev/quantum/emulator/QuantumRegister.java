package kpfu.terentyev.quantum.emulator;

import java.util.Random;

/**
 * Created by alexandrterentyev on 25.02.15.
 */
public class QuantumRegister {
    private int qubitsNumber;
    private int size;
    private Complex [] vector;

    public QuantumRegister (int qubitsNumber) {
        this.setQubitsNumber(qubitsNumber);
    }
    public QuantumRegister (int qubitsNumber, Complex [] vector) throws Exception {
        this.qubitsNumber = qubitsNumber;
        size = ((int) Math.pow(2, qubitsNumber));
        this.vector =vector;
        if (size != vector.length){
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

    public Complex[] getVector(){
        return vector;
    }

    public int getQubitsNumber() {
        return qubitsNumber;
    }

    public void setQubitsNumber(int qubitsNumber) {
        this.qubitsNumber = qubitsNumber;
        this.size = ((int) Math.pow(2, qubitsNumber));
        this.vector = new Complex[size];
        this.vector[0]=Complex.unit();
        for (int i=1; i<this.vector.length; i++){
            vector[i]=Complex.zero();
        }
    }

    public void multiplyOnMatrix(Complex[][]matrix) throws Exception {
        if (matrix.length!=size){
            throw new Exception();
        }
        this.vector=ComplexMath.multiplication(matrix, size, vector);
    }

    @Override
    public String toString() {
        String result ="";
        for (int i=0; i<size; i++){
            result= result+ vector[i] +" |"+i+">\n";
        }
        return result;
    }

    public void performAlgorythm (QuantumAlgorithm algorythm) throws Exception {
        vector = ComplexMath.multiplication(algorythm.getMatrix(), size, vector);
    }

    public void performAlgorythm (OneStepAlgorythm algorythm) throws Exception {
        vector = ComplexMath.multiplication(algorythm.getMatrix(), size, vector);
    }



/// Измерение
    public int measureQubit (int qubit) throws Exception {
        if (qubit >= qubitsNumber){
            throw  new Exception();
        }
        Complex [][] P0 = ComplexMath.zeroMatrix(size, size);
        int pow2n = (int) Math.pow(2, qubit);
        for (int i = 0; i+pow2n<size; i+=2*pow2n){
            for (int j=i; j<i+pow2n; j++){
                P0[j][j] = Complex.unit();
            }
        }
        Complex[][] vectorBra = new Complex[1][size];
        Complex[][] vectorKet = new Complex[size][1];
        for (int i=0 ; i<size; i++){
            vectorKet [i][0] = vector[i];
        }
        vectorBra [0] = vector.clone();
        Complex [][]p0 = ComplexMath.matricesMultiplication(vectorBra, 1, size,
                P0, size, size);
        p0 = ComplexMath.matricesMultiplication(p0, 1, size, vectorKet, size, 1);
        Complex p =  p0[0][0];
        int result = 0;

        //measure and normalize
        Complex [][] Pm;
        if (new Random().nextDouble() >p.getReal()){
            result = 1;
            Pm = ComplexMath.zeroMatrix(size, size);
            for (int i = pow2n; i<size; i+=2*pow2n){
                for (int j=i; j<i+pow2n; j++){
                    Pm[j][j] = Complex.unit();
                }
            }
            Complex [][]p1 = new Complex[1][size];
            p1[0]=vector.clone();
            p1 = ComplexMath.matricesMultiplication(vectorBra, 1, size,
                    Pm, size, size);
            p1 = ComplexMath.matricesMultiplication(p1, 1, size, vectorKet, size, 1);
            p=p1[0][0];

        }else {
            Pm = P0;
        }

        //norm
        vector= ComplexMath.multiplication(Pm, size,vector);
        Complex norma = Complex.zero();
        for (int i=0; i<size; i++){
            norma = Complex.add(norma, Complex.mult(vector[i], vector[i]));
        }
        for (int i =0; i<size; i++){
            vector[i] = Complex.devide(vector[i], norma);
        }

        return  result;
    }
}
