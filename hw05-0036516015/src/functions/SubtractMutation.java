package functions;

import java.util.Arrays;

import interfaces.IMutation;

public class SubtractMutation implements IMutation {

	@Override
	public double[][] mutate(double[][] solutions) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public double[] mutateOne(double[] baseVector, double[] first, double[] second, double coef) {
		baseVector = Arrays.copyOf(baseVector, baseVector.length);
		for (int i = 0; i < baseVector.length; i++) {
			baseVector[i] += ((first[i] - second[i]) * coef);
		}
		return baseVector;
	}

}
