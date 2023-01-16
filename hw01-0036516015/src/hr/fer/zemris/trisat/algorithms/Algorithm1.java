package hr.fer.zemris.trisat.algorithms;

import java.util.Optional;

import hr.fer.zemris.trisat.BitVector;
import hr.fer.zemris.trisat.SATFormula;

public class Algorithm1 implements IOptAlgorithm {
	
	private SATFormula formula;
	
	public Algorithm1(SATFormula formula) {
		this.formula = formula;
	}

	@Override
	public Optional<BitVector> solve(Optional<BitVector> initial) {
		
		BitVector result = null;
		int nVars = formula.getNumberOfVariables();
		
		for (int n = 0; n < Math.pow(2, nVars); n++) {
			BitVector current = new BitVector(n, nVars);
			if (formula.isSatisfied(current)) {
				if (result == null) result = current;
				System.out.println(current);
			}
		}
		
		return result == null ? Optional.empty() : Optional.of(result);
		
	}

}
