package hr.fer.zemris.gp.nodes;

import java.util.ArrayList;
import java.util.List;

public abstract class Node {
	
	List<Node> children;
	int size;
	Node parent;
	
	public Node() {
		this.children = new ArrayList<>();
		this.size = 1;
		this.parent = null;
	}
	
	public double getResult(double[] input) {
		return 0;
	}
	
	public void addChild(Node n) {
		children.add(n);
		n.setParent(this);
		size += n.getSize();
		
	}
	
	private void setParent(Node n) {
		this.parent = n;
	}
	
	public Node getParent() {
		return parent;
	}
	
	public int getSize() {
		return size;
	}
	
	public int desiredChildren() {
		return 0;
	}
	
	public int numberOfChildren() {
		return children.size();
	}
	
	public Node getChild(int index) {
		return children.get(index);
	}
	
	public void replaceChild(int index, Node n) {
		size -= children.get(index).getSize();
		children.set(index, n);
		n.setParent(this);
		size += n.getSize();
	}
	
	public Node deepCopy() {
		return null;
	}
	
	public String toString() {
		return "";
	}
	
}
