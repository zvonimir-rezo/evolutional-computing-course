package hr.fer.zemris.trisat.algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import hr.fer.zemris.trisat.BitVector;
import hr.fer.zemris.trisat.BitVectorNGenerator;
import hr.fer.zemris.trisat.MutableBitVector;
import hr.fer.zemris.trisat.SATFormula;
import hr.fer.zemris.trisat.SATFormulaStats;

public class Algorithm3 implements IOptAlgorithm {
	
	private static final int MAX_ITER = 100000;
	private SATFormula formula;
	private SATFormulaStats stats;
	
	public Algorithm3(SATFormula formula) {
		this.formula = formula;
		this.stats = new SATFormulaStats(formula);
	}

	@Override
	public Optional<BitVector> solve(Optional<BitVector> initial) {
		
		BitVector assignment = new BitVector(new Random(), formula.getNumberOfVariables());
		BitVector solution = null;
		int iter_count = 0;
		while (iter_count < MAX_ITER) {
			stats.setAssignment(assignment, true);
			if (stats.getNumberOfSatisfied() == formula.getNumberOfClauses()) {
				solution = assignment;
				break;
			}
				
			List<MutableBitVector> bestNeighbors = maxFitNeighbors(new BitVectorNGenerator(assignment).createNeighborhood());
			assignment = bestNeighbors.get(new Random().nextInt(bestNeighbors.size()));
			iter_count++;
		}
		return Optional.ofNullable(solution);
		
	}
	
	
	private double fit(BitVector assignment) {
		stats.setAssignment(assignment, false);
		return stats.getNumberOfSatisfied() + stats.getPercentageBonus();
	}
	
	private List<MutableBitVector> maxFitNeighbors(MutableBitVector[] neighbors) {
		List<MutableBitVector> bestNeighbors = new ArrayList<>();
		
		double bestFit = 0;
		for (MutableBitVector n: neighbors) {
			double nFit = fit(n);
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
