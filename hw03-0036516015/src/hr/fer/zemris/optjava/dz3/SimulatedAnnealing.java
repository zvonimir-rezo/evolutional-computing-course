package hr.fer.zemris.optjava.dz3;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import Jama.Matrix;

public class SimulatedAnnealing {

	private double T, alpha;
	private Matrix S;
	int innerIter, outerIter;
	private IFunction func;
	private boolean minimize;
	
	public SimulatedAnnealing(Matrix S, int outerIter, int innerIter, double T, double alpha, IFunction func, boolean minimize) {
		this.S = S;
		this.outerIter = outerIter;
		this.innerIter = innerIter;
		this.T = T;
		this.alpha = alpha;
		this.func = func;
		this.minimize = minimize;
	}
	
	public void run() {
		Matrix solution = S;
		double temp = T;
		Matrix bestSolution = S;
		double bestError = Double.MAX_VALUE;
		for (int i = 0; i < outerIter; i++) {
			if (bestError < 1) break;
			for (int j = 0; j < innerIter; j++) {
				Matrix neighbor = generateNeighbor(solution);
				double solutionError = func.valueAt(solution);
				double neighborError = func.valueAt(neighbor);
				if (solutionError < bestError) {
					bestError = solutionError;
					bestSolution = solution;
				}
				double diff = neighborError - solutionError;
//				System.out.println("ERRORS:");
//				System.out.println(neighborError);
//				System.out.println(solutionError);
//				System.out.println("Diff: " + diff);
//				System.out.println("Temp: " + temp);
//				System.out.println(Math.exp(-diff/temp));
//				System.out.println();
				if (!minimize) diff = -diff;
				if ((diff < 0) || (Math.exp(-diff/temp) > Math.random())) solution = neighbor;
				
			}
			System.out.println(func.valueAt(solution) + " " + temp);
			temp *= alpha;
		}
		bestSolution.print(3, 3);
		System.out.println(bestError);
	}
	
	private Matrix generateNeighbor(Matrix solution) {
		Matrix neighbor = solution.copy();
		Random r = new Random();
		Set<Integer> indexSet = new HashSet<Integer>();
		int br = r.nextInt(6) + 1;
		while (indexSet.size() < br) indexSet.add(r.nextInt(6));
		double e;
		for (int i: indexSet) {
			if (i == 3) e = 0.02 * r.nextDouble() - 0.01;
			else e = 0.1 * r.nextDouble() - 0.05;
			neighbor.set(i, 0, neighbor.get(i, 0) + e);
		}
		return neighbor;
	}
	
}
