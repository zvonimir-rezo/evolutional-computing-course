package functions;

import interfaces.IFunction;

public class Function4 implements IFunction{

	private double[][] A;
	private double[] b;
	
	public Function4(double[][] A, double[] b) {
		this.A = A;
		this.b = b;
	}

	@Override
	public int numberOfVariables() {
		return A[0].length;
	}

	@Override
	public double valueAt(double[] m) {
		double suma = 0;
		double a = m[0];
		double bb = m[1];
		double c = m[2];
		double d = m[3];
		double e = m[4];
		double f = m[5];
		for (int i = 0; i < b.length; i++) {	
			double x1 = A[i][0];
			double x2 = A[i][1];
			double x3 = A[i][2];
			double x4 = A[i][3];
			double x5 = A[i][4];
			double y = b[i];
			double rez = y - ((a * x1) + (bb * Math.pow(x1, 3) * x2) 
					+ (c * Math.exp(d*x3) * (1 + Math.cos(e*x4))) 
					+ (f * x4 * Math.pow(x5, 2)));
			suma += Math.pow(rez, 2);
		}
		return Math.sqrt(suma);
	}

	
}
