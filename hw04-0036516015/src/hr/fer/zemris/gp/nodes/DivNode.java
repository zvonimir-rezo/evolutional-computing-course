package hr.fer.zemris.gp.nodes;

public class DivNode extends BinaryNode {

	public double getResult(double[] input) {
		double r = children.get(1).getResult(input);
		if (r == 0)
			return 1;
		return children.get(0).getResult(input) / r;
	}
	
	public Node deepCopy() {
		DivNode newNode = new DivNode();
		for (int i = 0; i < children.size(); i++) {
			newNode.addChild(children.get(i).deepCopy());
		}
		newNode.parent = this.parent;
		return newNode;
	}
	
	public String toString() {
		return children.get(0).toString() + " / " + children.get(1).toString();
	}
}
