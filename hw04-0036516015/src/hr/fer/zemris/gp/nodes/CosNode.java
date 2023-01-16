package hr.fer.zemris.gp.nodes;

public class CosNode extends UnaryNode {

	public double getResult(double[] input) {
		return Math.cos(children.get(0).getResult(input));
	}
	
	public Node deepCopy() {
		CosNode newNode = new CosNode();
		for (int i = 0; i < children.size(); i++) {
			newNode.addChild(children.get(i).deepCopy());
		}
		newNode.parent = this.parent;
		return newNode;
	}
	
	public String toString() {
		return "cos(" + children.get(0).toString() + ")";
	}
}
