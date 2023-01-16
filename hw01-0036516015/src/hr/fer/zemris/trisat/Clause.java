package hr.fer.zemris.trisat;


public class Clause {
	int[] indexes;
	
	public Clause(int[] indexes) {
		this.indexes = indexes;
	}
	
	public int getSize() {
		return indexes.length;
	}
	
	public int getLiteral(int index) {
		return indexes[index];
	}
	
	public boolean isSatisfied(BitVector assignment) {
		for (int index: indexes) {
			if ((index < 0 && !assignment.get(-index-1)) || (index > 0 && assignment.get(index-1)))
				return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		String s = "";
		for (int var: indexes) {
			s += Integer.toString(var);
			s += " ";
		}
		return s;
	}
}
