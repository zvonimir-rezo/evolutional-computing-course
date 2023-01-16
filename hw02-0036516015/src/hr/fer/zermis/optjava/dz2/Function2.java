package hr.fer.zermis.optjava.dz2;

import Jama.Matrix;

public class Function2 implements IFunction{

	@Override
	public int numberOfVariables() {
		return 2;
	}

	@Override
	public double valueAt(Matrix m) {
		double[] row = m.getRowPackedCopy();
		return Math.pow((row[0] - 1), 2) + 10 * Math.pow((row[1] - 2), 2);
	}

	@Override
	public Matrix gradient(Matrix m) {
		double[] row = m.getRowPackedCopy();
		double[] gradient = new double[] {2 * (row[0] - 1), 20 * (row[1] - 2)};
		return new Matrix(gradient, 2);
	}

}
