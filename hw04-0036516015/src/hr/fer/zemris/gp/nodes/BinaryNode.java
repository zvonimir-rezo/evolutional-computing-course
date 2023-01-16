package hr.fer.zemris.gp.nodes;

public abstract class BinaryNode extends Node {
	
	public int desiredChildren() {
		return 2;
	}
	
	public Node getLeft() {
		return children.get(0);
	}
	
	public Node getRight() {
		return children.get(1);
	}
	
	public void setLeft(Node n) {
		children.set(0, n);
	}
	
	public void setRight(Node n) {
		children.set(1, n);
	}
	
}
