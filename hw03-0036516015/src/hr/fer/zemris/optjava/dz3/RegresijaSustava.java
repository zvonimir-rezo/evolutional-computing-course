package hr.fer.zemris.optjava.dz3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

import Jama.Matrix;

public class RegresijaSustava {

	public static void main(String[] args) throws IOException {
		Path path = Paths.get(args[0]);
		
		List<String> rows = Files.readAllLines(path);
		int commentCount = (int) rows.stream().filter(x -> x.startsWith("#")).count();
		
		double[] y = new double[rows.size() - commentCount];
		double[][] x = new double[rows.size() - commentCount][5];
		
		for (int i = commentCount; i < rows.size(); i++) {
			String row = rows.get(i);
			String[] rowSplit = row.substring(1, row.length() - 1).split(", ");
			for (int j = 0; j < rowSplit.length - 1; j++) {
				x[i - commentCount][j] = Double.valueOf(rowSplit[j]);
			}
			y[i - commentCount] = Double.valueOf(rowSplit[rowSplit.length - 1]);
		}
		
		Matrix xMatrix = new Matrix(x);
		Matrix yMatrix = new Matrix(y, rows.size() - commentCount);
		
		IFunction func = new Function4(xMatrix, yMatrix);
		Matrix startingSolution = randomSolution(6);
		int outerIter = 500;
		int innerIter = 10000;
		double temp = 200;
		double alpha = 0.99;
		boolean minimize = true;
		
		SimulatedAnnealing simulatedAnnealing = new SimulatedAnnealing(startingSolution, outerIter, innerIter, temp, alpha, func, minimize);
		simulatedAnnealing.run();

	}
	
	private static Matrix randomSolution(int n) {
		Random r = new Random();
		double[] solution = new double[n];
		for (int i = 0; i < n; i++) {
			solution[i] = r.nextDouble() * 10 - 5;
		}
		return new Matrix(solution, n);
	}

}
