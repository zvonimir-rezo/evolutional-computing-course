package hr.fer.zemris.trisat.algorithms;

import java.util.Optional;

import hr.fer.zemris.trisat.BitVector;

public interface IOptAlgorithm {
	
	Optional<BitVector> solve(Optional<BitVector> initial);


}
