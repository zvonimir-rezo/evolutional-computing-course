package functions;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import interfaces.ISelection;

public class BestSelection implements ISelection{

	@Override
	public double[][] select(double[][] population, double[] fValues) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double[][] select(double[][] population, int bestIndex, int i) {
		double[][] ret = new double[3][population[0].length];
		ret[0] = population[bestIndex];
		int cnt = 1;
		Random random = new Random();
		Set<Integer> takenIndexes = new HashSet<>();
		takenIndexes.add(bestIndex);
		takenIndexes.add(i);
		while (cnt < 3) {
			int index = random.nextInt(population.length);
			if (!takenIndexes.contains(index)) {
				ret[cnt] = population[index];
				takenIndexes.add(cnt);
				cnt++;
			}
		}
		return ret;
	}

}
