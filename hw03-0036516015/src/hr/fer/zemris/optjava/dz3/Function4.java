package hr.fer.zemris.optjava.dz3;

import Jama.Matrix;

public class Function4 implements IFunction{

	private Matrix A;
	private Matrix b;
	
	public Function4(Matrix A, Matrix b) {
		this.A = A;
		this.b = b;
	}

	@Override
	public int numberOfVariables() {
		return A.getColumnDimension();
	}

	@Override
	public double valueAt(Matrix m) {
		double suma = 0;
		double a = m.get(0, 0);
		double bb = m.get(1, 0);
		double c = m.get(2, 0);
		double d = m.get(3, 0);
		double e = m.get(4, 0);
		double f = m.get(5, 0);
		for (int i = 0; i < b.getRowDimension(); i++) {	
			double x1 = A.get(i, 0);
			double x2 = A.get(i, 1);
			double x3 = A.get(i, 2);
			double x4 = A.get(i, 3);
			double x5 = A.get(i, 4);
			double y = b.get(i, 0);
			double rez = y - ((a * x1) + (bb * Math.pow(x1, 3) * x2) 
					+ (c * Math.exp(d*x3) * (1 + Math.cos(e*x4))) 
					+ (f * x4 * Math.pow(x5, 2)));
			suma += Math.pow(rez, 2);
		}
		return Math.sqrt(suma);
	}

	@Override
	public Matrix gradient(Matrix m) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
