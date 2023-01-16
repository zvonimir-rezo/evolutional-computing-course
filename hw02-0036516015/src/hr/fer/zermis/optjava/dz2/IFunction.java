package hr.fer.zermis.optjava.dz2;

import Jama.Matrix;

public interface IFunction {

	int numberOfVariables();
	
	double valueAt(Matrix m);
	
	Matrix gradient(Matrix m);
	
}
