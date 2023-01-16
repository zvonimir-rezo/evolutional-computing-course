package algorithm;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import functions.Function4;
import interfaces.ICrossover;
import interfaces.IFunction;
import interfaces.IMutation;
import interfaces.ISelection;

public class Main {

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
		
		
		Strategy strategy = new Strategy(args[1]);
		
		IFunction func = new Function4(x, y);
		int populationSize = 1000;
		ISelection selection = strategy.getSelection();
		IMutation mutation = strategy.getMutation();
		ICrossover crossover = strategy.getCrossover();
		int maxIter = 10000;
		double mutationCoef = 0.2;
		double errorBreak = 0.001;
		int constNumber = 6;
		
		DifferentialEvolution diffEvolution = new DifferentialEvolution(populationSize, func, selection, mutation, crossover, maxIter, mutationCoef, errorBreak, constNumber);
		diffEvolution.run();

	}


}
