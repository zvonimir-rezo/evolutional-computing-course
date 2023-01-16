package hr.fer.zemris.trisat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import hr.fer.zemris.trisat.algorithms.Algorithm1;
import hr.fer.zemris.trisat.algorithms.Algorithm2;
import hr.fer.zemris.trisat.algorithms.Algorithm3;
import hr.fer.zemris.trisat.algorithms.Algorithm4;
import hr.fer.zemris.trisat.algorithms.Algorithm5;
import hr.fer.zemris.trisat.algorithms.Algorithm6;
import hr.fer.zemris.trisat.algorithms.IOptAlgorithm;

public class TriSATSolver {
	
	public static void main(String[] args) {
		
		int algoNum = Integer.valueOf(args[0]);
		Path path = Paths.get(args[1]);
		
		SATFormula formula = getFormulaFromFile(path);
		IOptAlgorithm algorithm = null;
		
		switch (algoNum) {
		case 1:
			algorithm = new Algorithm1(formula);
			break;
		case 2:
			algorithm = new Algorithm2(formula);
			break;
		case 3:
			algorithm = new Algorithm3(formula);
			break;
		case 4:
			algorithm = new Algorithm4(formula);
			break;
		case 5:
			algorithm = new Algorithm5(formula);
			break;
		case 6:
			algorithm = new Algorithm6(formula);
			break;
		}
		
		Optional<BitVector> solution = algorithm.solve(Optional.empty());
		if(solution.isPresent()) {
			BitVector sol = solution.get();
			System.out.println("Imamo rjesenje: " + sol);
		} else {
			System.out.println("Rjesenje nije pronadeno.");
		}
	}
	
	public static SATFormula getFormulaFromFile(Path path) {
		
		List<String> allRows = null;
		try {
			allRows = Files.readAllLines(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int index = 0;
		while (allRows.get(index).startsWith("c")) index++;

		String[] definitionLine = allRows.get(index).split(" ");
		int nVariables = Integer.valueOf(definitionLine[2]);
		int nClauses = Integer.valueOf(definitionLine[4]);
		index++;
		int indexClauses = 0;
		Clause[] clauses = new Clause[nClauses];
		
		while (indexClauses < nClauses) {
			String[] lineSplit = allRows.get(index+indexClauses).trim().split(" ");
			int[] clauseIndexes = new int[lineSplit.length-1];
			for (int i = 0; i < clauseIndexes.length; i++) {
				clauseIndexes[i] = Integer.valueOf(lineSplit[i]);
			}
			
			clauses[indexClauses] = new Clause(clauseIndexes);
			indexClauses++;
		}
		
		return new SATFormula(nVariables, clauses);
		
	}

	
}
