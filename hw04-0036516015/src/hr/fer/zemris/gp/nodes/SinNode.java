package hr.fer.zemris.gp.nodes;

public class SinNode extends UnaryNode {

	public double getResult(double[] input) {
		return Math.sin(children.get(0).getResult(input));
	}
	
	public Node deepCopy() {
		SinNode newNode = new SinNode();
		for (int i = 0; i < children.size(); i++) {
			newNode.addChild(children.get(i).deepCopy());
		}
		newNode.parent = this.parent;
		return newNode;
	}
	
	public String toString() {
		return "sin(" + children.get(0).toString() + ")";
	}
}
