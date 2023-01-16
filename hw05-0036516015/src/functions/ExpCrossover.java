package functions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import interfaces.ICrossover;

public class ExpCrossover implements ICrossover {
	
	private double p;
	
	public ExpCrossover(double p) {
		this.p = p;
	}

	@Override
	public double[] cross(double[] parent1, double[] parent2) {
		Random random = new Random();
		List<Integer> indexes = new ArrayList<>();
		int start = random.nextInt(parent1.length);
		indexes.add(start);
		int i = start+1;
		while (random.nextDouble() < p) {
			indexes.add(i % parent1.length);
			i++;
		}
		
		double[] ret = new double[parent1.length];
		for (int j = 0; j < ret.length; j++) {
			if (indexes.contains(j)) {
				ret[j] = parent2[j];
			} else {
				ret[j] = parent1[j];
			}
		}
		return ret;
	}

}
