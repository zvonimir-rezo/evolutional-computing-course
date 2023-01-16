package hr.fer.zemris.trisat.algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import hr.fer.zemris.trisat.BitVector;
import hr.fer.zemris.trisat.BitVectorNGenerator;
import hr.fer.zemris.trisat.MutableBitVector;
import hr.fer.zemris.trisat.SATFormula;

public class Algorithm2 implements IOptAlgorithm {
	
	private static final int MAX_ITER = 100000;
	private SATFormula formula;
	
	public Algorithm2(SATFormula formula) {
		this.formula = formula;
	}

	@Override
	public Optional<BitVector> solve(Optional<BitVector> initial) {
		
		BitVector assignment = new BitVector(new Random(), formula.getNumberOfVariables());
		BitVector solution = null;
		for (int i = 0; i < MAX_ITER; i++) {
			if (fit(assignment) == formula.getNumberOfClauses()) {
				solution = assignment;
				break;
			}
				
			List<MutableBitVector> bestNeighbors = maxFitNeighbors(new BitVectorNGenerator(assignment).createNeighborhood());		
			if (fit(assignment) > fit(bestNeighbors.get(0))) {
				System.out.println("Neuspjeh");
				break;
			}
			assignment = bestNeighbors.get(new Random().nextInt(bestNeighbors.size()));
		}
		return Optional.ofNullable(solution);
		
	}
	
	private int fit(BitVector assignment) {
		int counter = 0;
		for (int i = 0; i < formula.getNumberOfClauses(); i++) {
			if (formula.getClause(i).isSatisfied(assignment))
				counter++;
		}
		return counter;
	}
	
	private List<MutableBitVector> maxFitNeighbors(MutableBitVector[] neighbors) {
		List<MutableBitVector> bestNeighbors = new ArrayList<>();
		
		int bestFit = 0;
		for (MutableBitVector n: neighbors) {
			int nFit = fit(n);
			if (nFit > bestFit) {
				bestNeighbors.clear();
				bestFit = nFit;
				bestNeighbors.add(n);
			} else if (nFit == bestFit) {
				bestNeighbors.add(n);
			}
		}
		return bestNeighbors;
	}

}
