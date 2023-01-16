package hr.fer.zemris.trisat;

import java.util.Arrays;
import java.util.Random;

public class BitVector {
	
	protected boolean[] bits;
	
	public BitVector(Random rand, int numberOfBits) {
		this(rand.nextInt((int) Math.pow(2, numberOfBits)), numberOfBits);
	}
	
	public BitVector(boolean ... bits) {
		this.bits = bits;
	}
	
	public BitVector(int n, int numberOfBits) {
		this.bits = new boolean[numberOfBits];
		
		int i = 0;
		while (i < numberOfBits) {
			this.bits[numberOfBits-i-1] = n % 2 == 1;
			n /= 2;
			i++;
		}
	}
	
	public boolean get(int index) {
		return bits[index];
	}
	
	public int getSize() {
		return bits.length;
	}
	
	@Override
	public String toString() {
		String s = "";
		for (boolean bit: bits) {
			s += bit ? "1" : "0";
		}
		return s;
		
	}
	
	public MutableBitVector copy() {
		return new MutableBitVector(Arrays.copyOf(bits, bits.length));
	}

}
