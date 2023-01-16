package hr.fer.zermis.optjava.dz2;

import Jama.Matrix;

public class Function1 implements IFunction {

	@Override
	public int numberOfVariables() {
		return 2;
	}

	@Override
	public double valueAt(Matrix m) {
		double[] row = m.getRowPackedCopy();
		return Math.pow(row[0], 2) + Math.pow((row[0] - 1), 2);
	}

	@Override
	public Matrix gradient(Matrix m) {
		double[] row = m.getRowPackedCopy();
		double[] gradient = new double[] {2 * row[0], 2 * (row[1] - 1)};
		return new Matrix(gradient, 2);
	}

}
