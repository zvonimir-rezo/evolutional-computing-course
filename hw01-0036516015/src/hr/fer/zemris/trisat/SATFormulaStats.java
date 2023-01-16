package hr.fer.zemris.trisat;

public class SATFormulaStats {
	
	public static final int numberOfBest = 2;
	public static final double percentageConstantUp = 0.01;
	public static final double percentageConstantDown = 0.1;
	public static final double percentageUnitAmount = 50;

	private SATFormula formula;
	private double[] post;
	private double percentageBonus;
	private int nSatisfiedClauses;
	
	public SATFormulaStats(SATFormula formula) {
		this.formula = formula;
		this.post = new double[formula.getNumberOfClauses()];
	}
	
	public void setAssignment(BitVector assignment, boolean updatePercentages) {
		int satisfied = 0;
		if (updatePercentages) {
			for (int i = 0; i < formula.getNumberOfClauses(); i++) {
				if (formula.getClause(i).isSatisfied(assignment)) {
					satisfied++;
					post[i] += (1 - post[i]) * percentageConstantUp;
				} else {
					post[i] += (0 - post[i]) * percentageConstantDown;
				}
			}
			this.nSatisfiedClauses = satisfied;
		} else {
			percentageBonus = 0;
			for (int i = 0; i < formula.getNumberOfClauses(); i++) {
				if (formula.getClause(i).isSatisfied(assignment)) {
					satisfied++;
					percentageBonus += percentageUnitAmount * (1 - post[i]);
				} else {
					percentageBonus -= percentageUnitAmount * (1 - post[i]);
				}
			}
			this.nSatisfiedClauses = satisfied;
		}
		
	}
	
	public int getNumberOfSatisfied() {
		return nSatisfiedClauses;
	}
	
	public boolean isSatisfied() {
		return formula.getNumberOfClauses() == nSatisfiedClauses;
	}
	

	public double getPercentageBonus() {
		return percentageBonus;
	}
	

	public double getPercentage(int index) {
		return post[index];
	}
	
}
