package hr.fer.zemris.gp.nodes;

public class MulNode extends BinaryNode {

	public double getResult(double[] input) {
		return children.get(0).getResult(input) * children.get(1).getResult(input);
	}
	
	public Node deepCopy() {
		MulNode newNode = new MulNode();
		for (int i = 0; i < children.size(); i++) {
			newNode.addChild(children.get(i).deepCopy());
		}
		newNode.parent = this.parent;
		return newNode;
	}
	
	public String toString() {
		return children.get(0).toString() + " * " + children.get(1).toString();
	}
	
}
