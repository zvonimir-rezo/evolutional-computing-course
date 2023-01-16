package hr.fer.zemris.trisat.algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import hr.fer.zemris.trisat.BitVector;
import hr.fer.zemris.trisat.BitVectorNGenerator;
import hr.fer.zemris.trisat.Clause;
import hr.fer.zemris.trisat.MutableBitVector;
import hr.fer.zemris.trisat.SATFormula;

public class Algorithm5 implements IOptAlgorithm {
	
	private static final int MAX_FLIPS = 100000;
	private static final int MAX_TRIES = 20;
	private static final double P = 0.1;
	
	private SATFormula formula;
	
	public Algorithm5(SATFormula formula) {
		this.formula = formula;
	}

	@Override
	public Optional<BitVector> solve(Optional<BitVector> initial) {
		
		for (int restart = 0; restart < MAX_TRIES; restart++) {
			Random random = new Random();
			MutableBitVector assignment = new BitVector(random, formula.getNumberOfVariables()).copy();
			if (formula.isSatisfied(assignment))
				return Optional.of(assignment);
			
			for (int i = 0; i < MAX_FLIPS; i++) {
				List<Clause> clauses = new ArrayList<>();
				for (int j = 0; j < formula.getNumberOfClauses(); j++) {
					Clause clause = formula.getClause(j);
					if (!clause.isSatisfied(assignment))
						clauses.add(clause);
				}
				Clause randomUnsatisfied = clauses.get(random.nextInt(clauses.size()));
				
				if (random.nextDouble() < P) {
					int rndIndex = Math.abs(randomUnsatisfied.getLiteral(random.nextInt(randomUnsatisfied.getSize())));
					assignment.set(rndIndex-1, !assignment.get(rndIndex-1));
				} else {
					List<MutableBitVector> bestNeighbors = maxFitNeighbors(new BitVectorNGenerator(assignment).createNeighborhood());
					assignment = bestNeighbors.get(random.nextInt(bestNeighbors.size()));
				}
				if (formula.isSatisfied(assignment))
					return Optional.of(assignment);
			}
		}
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
