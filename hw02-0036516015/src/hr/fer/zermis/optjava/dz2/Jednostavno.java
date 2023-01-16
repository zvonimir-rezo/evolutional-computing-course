package hr.fer.zermis.optjava.dz2;

import java.util.List;
import java.util.Random;

import Jama.Matrix;

public class Jednostavno {

	public static void main(String[] args) {
		
		int func = Integer.valueOf(args[0]);
		int maxIter = Integer.valueOf(args[1]);
		
		
		Matrix startingPoint;
		if (args.length == 4) {
			double[] tmp = new double[] {Double.valueOf(args[2]), Double.valueOf(args[3])};
			startingPoint = new Matrix(tmp, 2);
		} else {
			Random random = new Random();
			startingPoint = new Matrix(new double[] {random.nextDouble(), random.nextDouble()}, 2);
		}
		
		List<Matrix> results = null;
		switch (func) {
		case 1:
			results = NumOptAlgorithms.gradientDescent(new Function1(), maxIter, startingPoint);
		case 2:
			results = NumOptAlgorithms.gradientDescent(new Function2(), maxIter, startingPoint);
		}
		for (int i = 0; i < results.size(); i++) {
			double[] row = results.get(i).getRowPackedCopy();
			System.out.println("Iteration: " + (i+1) + ", Gradient: [" + row[0] + ", " + row[1] + "]");
			
		}
		

	}

}
