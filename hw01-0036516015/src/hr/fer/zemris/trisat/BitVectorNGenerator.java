package hr.fer.zemris.trisat;

import java.util.Iterator;

public class BitVectorNGenerator implements Iterable<MutableBitVector> {
	BitVector assignment;
	
	public BitVectorNGenerator(BitVector assignment) {
		this.assignment = assignment;
	}
	
	@Override
	public Iterator<MutableBitVector> iterator() {
		return new Iterator<MutableBitVector>() {
			int index = 0;
			@Override
			public boolean hasNext() {
				return index < assignment.getSize();
			}

			@Override
			public MutableBitVector next() {
				MutableBitVector vec = assignment.copy();
				vec.set(index, !assignment.get(index));
				index++;
				return vec;
			}
		};
	}
	
	
	public MutableBitVector[] createNeighborhood() {
		MutableBitVector[] neighbors = new MutableBitVector[assignment.getSize()];
		for (int i = 0; i < assignment.getSize(); i++) {
			neighbors[i] = assignment.copy();
			neighbors[i].set(i, !assignment.get(i));
		}
		return neighbors;
	}

}
