package hr.fer.zermis.optjava.dz2;

import java.util.ArrayList;
import java.util.List;

import Jama.Matrix;

public class NumOptAlgorithms {

	public static List<Matrix> gradientDescent(IFunction func, int maxIter, Matrix point) {
		List<Matrix> resultList = new ArrayList<>();
		Matrix xo = point.copy();
		double lambda = 0.005;
		int i = 0;
		while (i < maxIter) {
			resultList.add(xo);
			Matrix g = func.gradient(xo);
			lambda = getLambda(func, xo);
			Matrix xn = xo.minus(g.times(lambda));
//			double n0 = xo.norm1();
//			double n1 = xn.norm1();
			double errNorm = Math.abs(xo.minus(xn).norm1());
//			double errNorm = func.valueAt(xn);
			if (errNorm <= 1E-4) {
				resultList.add(xn);
				break;
			}
			xo = xn;
			i++;
		}
		return resultList;
	}
	
	private static double getLambda(IFunction func, Matrix current) {
		double lower = 0;
		double upper = getUpperLambda(func, current);
		Matrix d = func.gradient(current).times(-1);

		double lambda;
		while (true) {
			lambda = (lower + upper) / 2;
			Matrix currentAdded = current.plus(d.times(lambda));
			Matrix gradient = func.gradient(currentAdded);
			double derivation = gradient.transpose().times(d).get(0, 0);
			if (Math.abs(derivation) < 1E-6)
				break;
			if (derivation <= 0) {
				lower = lambda;
			} else {
				upper = lambda;
			}
		}
		
		return lambda;
	}
	
	
	private static double getUpperLambda(IFunction func, Matrix current) {
		double upper = 1;
		Matrix d = func.gradient(current).times(-1);
		
		while (true) {
			Matrix currentAdded = current.plus(d.times(upper));
			Matrix gradient = func.gradient(currentAdded);
			double derivation = gradient.transpose().times(d).get(0, 0);
			if (derivation > 0 || Math.abs(derivation) < 1E-6)
				break;
			upper *= 2;
		}
		return upper;
	}
	
}
