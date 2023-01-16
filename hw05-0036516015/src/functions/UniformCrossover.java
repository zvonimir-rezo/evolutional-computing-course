package functions;

import java.util.Random;

import interfaces.ICrossover;

public class UniformCrossover implements ICrossover {

	@Override
	public double[] cross(double[] parent1, double[] parent2) {
		Random random = new Random();
		double[] ret = new double[parent1.length];
		for (int i = 0; i < parent1.length; i++) {
			if (random.nextBoolean()) {
				ret[i] = parent1[i];
			} else {
				ret[i] = parent2[i];
			}
		}
		return parent1;
	}

}
