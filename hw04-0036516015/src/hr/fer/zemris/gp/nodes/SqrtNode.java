package hr.fer.zemris.gp.nodes;

public class SqrtNode extends UnaryNode {

	public double getResult(double[] input) {
		double childValue = children.get(0).getResult(input);
		if (childValue < 0)
			return 1;
		return Math.sqrt(childValue);
	}
	
	public Node deepCopy() {
		SqrtNode newNode = new SqrtNode();
		for (int i = 0; i < children.size(); i++) {
			newNode.addChild(children.get(i).deepCopy());
		}
		newNode.parent = this.parent;
		return newNode;
	}
	
	public String toString() {
		return "sqrt(" + children.get(0).toString() + ")";
	}
}
