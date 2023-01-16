package hr.fer.zemris.gp.nodes;

public class ExpNode extends UnaryNode {

	public double getResult(double[] input) {
		return Math.exp(children.get(0).getResult(input));
	}
	
	public Node deepCopy() {
		ExpNode newNode = new ExpNode();
		for (int i = 0; i < children.size(); i++) {
			newNode.addChild(children.get(i).deepCopy());
		}
		newNode.parent = this.parent;
		return newNode;
	}
	
	public String toString() {
		return "exp(" + children.get(0).toString() + ")";
	}
	
}
