package hr.fer.zemris.gp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


public class Main {

	public static void main(String[] args) throws IOException {
		
		List<String> rows = Files.readAllLines(Paths.get(args[1]));
		
		double[] output = new double[rows.size()];
		String[] firstRow = rows.get(0).split("\\s+");
		double[][] input = new double[rows.size()][firstRow.length-1];
		
		for (int i = 0; i < rows.size(); i++) {
			String[] row = rows.get(i).split("\\s+");
			output[i] = Double.valueOf(row[row.length-1]);
			for (int j = 0; j < row.length - 1; j++) {
				input[i][j] = Double.valueOf(row[j]);
			}
		}
		String[] terminalSet = new String[input[0].length + 1];
		terminalSet[0] = "const";
		for (int i = 1; i < input[0].length + 1; i++) {
			terminalSet[i] = "x" + Integer.toString(i);
		}
		
		String[] functionSet = null;
		int populationSize = 500;
		int tournamentSize = 3;
		int costEvaluations = 1000000;
		int maxTreeDepth = 7;
		double mutationProbability = 0.3;
		double errorBreak = 0.000001;
		double constMin = -3, constMax = 3;
		boolean elitism = true, useLinearScaling = false;
		
		rows = Files.readAllLines(Paths.get(args[0]));
		
		for (int i = 0; i < rows.size(); i++) {
			String row = rows.get(i);
			String name = row.split("\\s+")[0];
			if (name.equals("FunctionNodes:")) {
				String r = row.replace("FunctionNodes: ", "");
				functionSet = r.split(", ");
			} else if (name.equals("ConstantRange:")) {
				String[] rangeSplit = row.replace("ConstantRange: ", "").split(", ");
				constMin = Double.parseDouble(rangeSplit[0]);
				constMax = Double.parseDouble(rangeSplit[1]);
			} else if (name.equals("PopulationSize:")) {
				populationSize = Integer.parseInt(row.replace("PopulationSize: ", ""));
			} else if (name.equals("TournamentSize:")) {
				tournamentSize = Integer.parseInt(row.replace("TournamentSize: ", ""));
			} else if (name.equals("CostEvaluations:")) {
				costEvaluations = Integer.parseInt(row.replace("CostEvaluations: ", ""));
			} else if (name.equals("MutationProbability:")) {
				mutationProbability = Double.parseDouble(row.replace("MutationProbability: ", ""));
			} else if (name.equals("MaxTreeDepth:")) {
				maxTreeDepth = Integer.parseInt(row.replace("MaxTreeDepth: ", ""));
			} else if (name.equals("UseLinearScaling:")) {
				useLinearScaling = Boolean.parseBoolean(row.replace("UseLinearScaling: ", ""));
			}
		}
		
		GeneticAlgorithm algorithm = new GeneticAlgorithm(functionSet, terminalSet, maxTreeDepth, populationSize, tournamentSize, costEvaluations, mutationProbability,
										errorBreak, input, output, constMin, constMax, elitism, useLinearScaling);
		
		algorithm.run();
		
	}
	
}
