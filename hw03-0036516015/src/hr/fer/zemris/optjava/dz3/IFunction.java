package hr.fer.zemris.optjava.dz3;

import Jama.Matrix;

public interface IFunction {

	int numberOfVariables();
	
	double valueAt(Matrix m);
	
	Matrix gradient(Matrix m);
	
}
