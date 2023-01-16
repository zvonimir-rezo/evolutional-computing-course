package algorithm;

import java.util.Arrays;

import interfaces.ICrossover;
import interfaces.IFunction;
import interfaces.IMutation;
import interfaces.ISelection;


public class DifferentialEvolution {

	private int populationSize, maxIter;
	private IFunction function;
	private ISelection selection;
	private IMutation mutation;
	private ICrossover crossover;
	private double errorBreak, mutationCoef;
	private double[][] population;
	private int solutionLength;
	
	public DifferentialEvolution(int populationSize, IFunction function, ISelection selection, IMutation mutation, ICrossover crossover,
			int maxIter, double mutationCoef, double errorBreak, int solutionLength) {
		this.populationSize = populationSize;
		this.function = function;
		this.selection = selection;
		this.mutation = mutation;
		this.crossover = crossover;
		this.maxIter = maxIter;
		this.mutationCoef = mutationCoef;
		this.errorBreak = errorBreak;
		this.solutionLength = solutionLength;
		this.population = generatePopulation();
	}
	
	private double[][] generatePopulation() {
		double[][] pop = new double[populationSize][solutionLength];
		
		for (int i = 0; i < populationSize; i++) {
			double[] member = new double[solutionLength];
			for (int j = 0; j < member.length; j++) {
				member[j] = -5 + Math.random() * 10;
			}
			pop[i] = member;
		}
		return pop;
	}
	
	public void run() {
		int iter = 0, bestIndex = -1;
		double currentError = Double.MAX_VALUE;
		double[] currentSolution;
		double[] fValues = new double[populationSize];
		while (iter < maxIter && currentError > errorBreak) {
			double lastIterError = currentError;
			for (int i = 0; i < populationSize; i++) {
				double value = function.valueAt(population[i]);
				fValues[i] = value;
				if (value < currentError) {
					bestIndex = i;
					currentError = value;
				}
			}
			
			if (fValues[bestIndex] < lastIterError) {
				currentError = fValues[bestIndex];
				currentSolution = population[bestIndex];
				System.out.println("Rjesenje: " + Arrays.toString(currentSolution));
				System.out.println("Error: " + currentError);
			}
			
			double[][] nextPopulation = new double[populationSize][solutionLength];
			
			for (int i = 0; i < populationSize; i++) {
				double[] goalVector = population[i];
				double[][] selected = selection.select(population, bestIndex, i);
				
				double[] mutantVector = mutation.mutateOne(selected[0], selected[1], selected[2], mutationCoef);
				
				double[] probeVector = crossover.cross(goalVector, mutantVector);
				if (function.valueAt(goalVector) < function.valueAt(probeVector)) {
					nextPopulation[i] = goalVector;
				} else {
					nextPopulation[i] = probeVector;
				}
				
			}
			population = nextPopulation;
			iter++;
		}
	}
	
	
	
}
