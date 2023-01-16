package hr.fer.zemris.trisat;


public class SATFormula {
	int numberOfVariables;
	Clause[] clauses;
	
	public SATFormula(int numberOfVariables, Clause[] clauses) {
		this.numberOfVariables = numberOfVariables;
		this.clauses = clauses;
	}
	
	public int getNumberOfVariables() {
		return numberOfVariables;
	}
	
	public int getNumberOfClauses() {
		return clauses.length;
	}
	
	public Clause getClause(int index) {
		return clauses[index];
	}
	
	public boolean isSatisfied(BitVector assignment) {
		for (Clause c: clauses) {
			if (!c.isSatisfied(assignment)) 
				return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		String s = "";
		for (Clause c: clauses) {
			s += c.toString();
		}
		return s;
	}

	
	
}
