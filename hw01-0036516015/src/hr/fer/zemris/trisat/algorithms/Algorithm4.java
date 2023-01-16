package hr.fer.zemris.trisat.algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import hr.fer.zemris.trisat.BitVector;
import hr.fer.zemris.trisat.BitVectorNGenerator;
import hr.fer.zemris.trisat.MutableBitVector;
import hr.fer.zemris.trisat.SATFormula;

public class Algorithm4 implements IOptAlgorithm {
	
	private static final int MAX_FLIPS = 100000;
	private static final int MAX_TRIES = 20;
	
	private SATFormula formula;
	
	public Algorithm4(SATFormula formula) {
		this.formula = formula;
	}

	@Override
	public Optional<BitVector> solve(Optional<BitVector> initial) {
		
		for (int restart = 0; restart < MAX_TRIES; restart++) {
			BitVector assignment = new BitVector(new Random(), formula.getNumberOfVariables());
			if (formula.isSatisfied(assignment))
				return Optional.of(assignment);
			
			for (int i = 0; i < MAX_FLIPS; i++) {
				List<MutableBitVector> bestNeighbors = maxFitNeighbors(new BitVectorNGenerator(assignment).createNeighborhood());
				assignment = bestNeighbors.get(new Random().nextInt(bestNeighbors.size()));
				if (formula.isSatisfied(assignment))
					return Optional.of(assignment);
			}
		}
		System.out.println("Dodjela nije pronadena");
		return Optional.ofNullable(null);
		
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
