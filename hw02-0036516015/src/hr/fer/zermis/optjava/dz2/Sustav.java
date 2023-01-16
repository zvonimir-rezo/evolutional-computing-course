package hr.fer.zermis.optjava.dz2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import Jama.Matrix;

public class Sustav {

	public static void main(String[] args) throws IOException {
		
		int maxIter = Integer.valueOf(args[0]);
		Path path = Paths.get(args[1]);
		
		List<String> rows = Files.readAllLines(path);
		int commentCount = (int) rows.stream().filter(x -> x.startsWith("#")).count();
		
		double[] y = new double[rows.size() - commentCount];
		double[][] x = new double[rows.size() - commentCount][rows.size() - commentCount];
		
		for (int i = commentCount; i < rows.size(); i++) {
			String row = rows.get(i);
			String[] rowSplit = row.substring(1, row.length() - 1).split(", ");
			for (int j = 0; j < rowSplit.length - 1; j++) {
				x[i - commentCount][j] = Double.valueOf(rowSplit[j]);
			}
			y[i - commentCount] = Double.valueOf(rowSplit[rowSplit.length - 1]);
		}
		
		Matrix xMatrix = new Matrix(x);
		Matrix yMatrix = new Matrix(y, 10);
		Matrix xo = new Matrix(new double[] {-5.0, -5.0, 0, 0, 0, 0, 0, 0, 0, 0}, 10);
		
		IFunction func = new Function3(xMatrix, yMatrix);
		List<Matrix> results = NumOptAlgorithms.gradientDescent(func, maxIter, xo);
		System.out.println(results.size());
		double[] lastRow = results.get(results.size() - 1).getRowPackedCopy();
		System.out.println("Final solution: " + Arrays.toString(lastRow));
		System.out.println("Final error: " + func.valueAt(results.get(results.size() - 1)));
		
	}

}
