package hr.fer.zemris.gp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

import hr.fer.zemris.gp.nodes.CosNode;
import hr.fer.zemris.gp.nodes.DivNode;
import hr.fer.zemris.gp.nodes.ExpNode;
import hr.fer.zemris.gp.nodes.LogNode;
import hr.fer.zemris.gp.nodes.MinusNode;
import hr.fer.zemris.gp.nodes.MulNode;
import hr.fer.zemris.gp.nodes.Node;
import hr.fer.zemris.gp.nodes.PlusNode;
import hr.fer.zemris.gp.nodes.SinNode;
import hr.fer.zemris.gp.nodes.SqrtNode;
import hr.fer.zemris.gp.nodes.TerminalNode;

public class GeneticAlgorithm {
	
	private String[] functionSet;
	private String[] terminalSet;
	private int depth, populationSize, tournamentSize, costEvaluations;
	private double errorBreak, mutationProbability;
	private List<Node> population;
	private double constMin, constMax;
	private double[][] input;
	private double[] output;
	private boolean elitism;
	
	public GeneticAlgorithm(String[] functionSet, String[] terminalSet, int depth, int populationSize, int tournamentSize, int costEvaluations, double mutationProbability, double errorBreak, double[][] input,
			double[] output, double constMin, double constMax, boolean elitism, boolean useLinearScaling) {
		this.functionSet = functionSet;
		this.terminalSet = terminalSet;
		this.depth = depth;
		this.populationSize = populationSize;
		this.tournamentSize = tournamentSize;
		this.costEvaluations = costEvaluations;
		this.mutationProbability = mutationProbability;
		this.errorBreak = errorBreak;
		this.constMin = constMin;
		this.constMax = constMax;
		this.input = input;
		this.output = output;
		this.elitism = elitism;
		this.population = generatePopulation();
	}
	
	public void run() {
		int outerIter = 0, bestIndex = -1;
		double currentError = Double.MAX_VALUE;
		Node currentSolution;
		double[] fValues;
		Random random = new Random();
		while (outerIter < costEvaluations && currentError > errorBreak) {
			fValues = calculateValues();
			bestIndex = findBestIndex(fValues);
			List<Node> nextPopulation = new ArrayList<>();
			
			if (fValues[bestIndex] < currentError) {
				currentError = fValues[bestIndex];
				currentSolution = population.get(bestIndex);
				System.out.println("Rjesenje: " + currentSolution.toString());
				System.out.println("Error: " + currentError);
			}
			
			if (elitism) nextPopulation.add(population.get(bestIndex).deepCopy());
			
			while (nextPopulation.size() < populationSize) {
				double p = random.nextDouble();
				
				if (p < 0.01) { // reprodukcija
					nextPopulation.add(tourSelection(fValues, tournamentSize).get(0));
				} else if (p < mutationProbability+0.01) { // mutacija
					nextPopulation.add(mutation(tourSelection(fValues, tournamentSize).get(0)));
				} else { // krizanje
					List<Node> nSelected = tourSelection(fValues, tournamentSize);
					Node first = nSelected.get(0);
					Node second = nSelected.get(1);
					nextPopulation.addAll(crossover(first, second));
				}
				
			}
			outerIter += population.size();
			population = nextPopulation;
		}
	}
	
	private double[] calculateValues() {
		double[] ret = new double[population.size()];
		for (int i = 0; i < ret.length; i++) {
			double tmp = 0;
			for (int j = 0; j < input.length; j++) {
				tmp += Math.pow(population.get(i).getResult(input[j]) - output[j], 2);
			}
			ret[i] = tmp / input.length;
		}
		return ret;
	}
	
	private int findBestIndex(double[] fValues) {
		int eliteIndex = 0;
		double mini = Double.MAX_VALUE;
		for (int i = 0; i < fValues.length; i++) {
			if (fValues[i] < mini) {
				eliteIndex = i;
				mini = fValues[i];
			}
		}
		return eliteIndex;
	}
	
	private List<Node> generatePopulation() {
		List<Node> population = new ArrayList<>();
		int half = populationSize / 2;
		int perDepth = (int) Math.ceil((double)half / (depth-1));
		for (int i = 2; i < depth+1; i++) {
			for (int j = 0; j < perDepth; j++) {
				Node root = getFunctionNode();
				generateFull(root, i-1);
				population.add(root);
			}
		}
		for (int i = 2; i < depth+1; i++) {
			for (int j = 0; j < perDepth; j++) {
				Node root = getFunctionNode();
				generateGrow(root, i-1);
				population.add(root);
			}
		}
		
		return population;
		
	}
	
	private void generateFull(Node n, int depth) {
		if (depth == 1) {
			for (int i = 0; i < n.desiredChildren(); i++) {
				Node child = getTerminalNode();
				n.addChild(child);
			}
		} else {
			for (int i = 0; i < n.desiredChildren(); i++) {
				Node child = getFunctionNode();
				generateFull(child, depth-1);
				n.addChild(child);
			}
		}
	}
	
	public void generateGrow(Node n, int depth) {
		if (depth == 1) {
			for (int i = 0; i < n.desiredChildren(); i++) {
				Node child = getTerminalNode();
				n.addChild(child);
			}
		} else {
			for (int i = 0; i < n.desiredChildren(); i++) {
				Node child = getRandomNewNode();
				generateGrow(child, depth-1);
				n.addChild(child);
			}
		}
	}
	
	private Node getFunctionNode() {
		Random random = new Random();
		switch (functionSet[random.nextInt(functionSet.length)]) {
		case "+":
			return new PlusNode();
		case "-":
			return new MinusNode();
		case "*":
			return new MulNode();
		case "/":
			return new DivNode();
		case "sin":
			return new SinNode();
		case "cos":
			return new CosNode();
		case "sqrt":
			return new SqrtNode();
		case "log":
			return new LogNode();
		default:
			return new PlusNode();
		}
	}
	
	private Node getTerminalNode() {
		Random random = new Random();
		return new TerminalNode(terminalSet[random.nextInt(terminalSet.length)], constMin, constMax);

	}
	
	private Node getRandomNewNode() {
		Random random = new Random();
		int nodeTypeIndex = random.nextInt(functionSet.length + terminalSet.length);
		String nodeType = (nodeTypeIndex < functionSet.length) ? functionSet[nodeTypeIndex] : terminalSet[nodeTypeIndex - functionSet.length];
		switch (nodeType) {
		case "+":
			return new PlusNode();
		case "-":
			return new MinusNode();
		case "*":
			return new MulNode();
		case "/":
			return new DivNode();
		case "sin":
			return new SinNode();
		case "cos":
			return new CosNode();
		case "sqrt":
			return new SqrtNode();
		case "log":
			return new LogNode();
		case "exp":
			return new ExpNode();
		default:
			return new TerminalNode(nodeType, constMin, constMax);
		}
	}
	
	private List<Node> crossover(Node firstNode, Node secondNode) {
		Node firstNodeCross = getRandomNode(firstNode);
		Node secondNodeCross = getRandomNode(secondNode);
		while (firstNodeCross.getParent() == null) firstNodeCross = getRandomNode(firstNode);
		while (secondNodeCross.getParent() == null) secondNodeCross = getRandomNode(firstNode);
		Node firstNodeCrossParent = firstNodeCross.getParent();
		Node secondNodeCrossParent = secondNodeCross.getParent();
		
		int indexFirst = 0, indexSecond = 0;
		for (int i = 0; i < firstNodeCrossParent.numberOfChildren(); i++) {
			if (firstNodeCrossParent.getChild(i) == firstNodeCross) {
				indexFirst = i;
			}
		}
		for (int i = 0; i < secondNodeCrossParent.numberOfChildren(); i++) {
			if (secondNodeCrossParent.getChild(i) == secondNodeCross) {
				indexSecond = i;
			}
		}
		
		Node firstReplace = firstNodeCross.deepCopy();
		Node secondReplace = secondNodeCross.deepCopy();
		firstNodeCrossParent.replaceChild(indexFirst, firstReplace);
		secondNodeCrossParent.replaceChild(indexSecond, secondReplace);
		List<Node> l = new ArrayList<>();
		l.add(firstNode);
		l.add(secondNode);
		return l;
		
	}
	
	private Node getRandomNode(Node node) {
		Node left = null, right = null;
		int a = node.getSize();
		int b = 0, c = 0;
		if (node.numberOfChildren() == 2) {
			left = node.getChild(0);
			right = node.getChild(1);
			b = left.getSize();
			c = right.getSize();
		} else if (node.numberOfChildren() == 1) {
			left = node.getChild(0);
			b = left.getSize();
		} else {
			return node;
		}
		
		if (b == 0 && c == 0) return node;
		
		Random r = new Random();
		int rInt = r.nextInt(a) + 1;
		if (rInt <= b) {
			return getRandomNode(left);
		}
		else if (rInt == b + 1) return node;
		else {
			if (right == null)
				return node;
			return getRandomNode(right);
		}
	}
	
	private Node mutation(Node node) {
		Node mutationNode = getRandomNode(node);
		Node mutationParent = mutationNode.getParent();
		if (mutationParent == null) {
			Node newRoot = getFunctionNode();
			generateFull(newRoot, depth);
			return newRoot;
		}

		int nodeDepth = 0;
		Node tmp = mutationNode;
		while (tmp != null) {
			tmp = tmp.getParent();
			nodeDepth++;
		}
		int indexChild = 0;
		for (int i = 0; i < mutationParent.numberOfChildren(); i++) {
			if (mutationParent.getChild(i) == mutationNode) {
				indexChild = i;
			}
		}
		if (depth-nodeDepth <= 0) {
			mutationParent.replaceChild(indexChild, getTerminalNode());
			return node;
		}
		Node newNode = getRandomNewNode();
		if (newNode.desiredChildren() == 0) {
			mutationParent.replaceChild(indexChild, newNode);
		} else {
			generateFull(newNode, depth-nodeDepth);
			mutationParent.replaceChild(indexChild, newNode);
		}
		return node;
		
	}

	public List<Node> tourSelection(double[] errors, int n) {
		Random random = new Random();

		Map<Double, Node> map = new TreeMap<Double, Node>();
		while (map.size() < n) {
			int r = random.nextInt(populationSize);
			map.put(errors[r], population.get(r));
		}
		
		return new ArrayList<Node>(map.values());
		
	}
	
}
