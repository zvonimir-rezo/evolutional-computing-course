package hr.fer.zemris.gp.nodes;

import java.util.Random;

public class TerminalNode extends Node {

	private String name;
	private double value;
	
	public TerminalNode(String name, double constMin, double constMax) {
		this.name = name;
		if (name.equals("const")) {
			this.value = new Random().nextDouble() * (constMax-constMin) + constMin;
		}
		this.size = 1;
	}
	
	public TerminalNode(String name, double value) {
		this.name = name;
		this.value = value;
		this.size = 1;
	}

	public String getName() {
		return name;
	}

	public void setValue(double value) {
		this.value = value;
	}
	
	public double getValue() {
		return value;
	}
	
	public int desiredChildren() {
		return 0;
	}
	
	public int getSize() {
		return 1;
	}
	
	public Node deepCopy() {
		Node node = new TerminalNode(name, value);
		node.parent = this.parent;
		return node;
	}
	
	public String toString() {
		if (name.equals("const"))
			return String.valueOf(value);
		else
			return name;
	}
}
