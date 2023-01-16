package hr.fer.zermis.optjava.dz2;

import Jama.Matrix;

public class Function3 implements IFunction {

	private Matrix A;
	private Matrix b;
	
	public Function3(Matrix A, Matrix b) {
		this.A = A;
		this.b = b;
	}

	@Override
	public int numberOfVariables() {
		return A.getColumnDimension();
	}

	@Override
	public double valueAt(Matrix m) {
		return A.times(m).minus(b).norm2();
	}

	@Override
	public Matrix gradient(Matrix m) {
		return A.transpose().times(2).times(A.times(m).minus(b));
	}
	
}
