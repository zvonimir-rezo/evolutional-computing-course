package hr.fer.zemris.gp.nodes;

public class LogNode extends UnaryNode {

	public double getResult(double[] input) {
		double childValue = children.get(0).getResult(input);
		if (childValue  <= 0)
			return 1;
		return Math.log10(childValue);
	}
	
	public Node deepCopy() {
		LogNode newNode = new LogNode();
		for (int i = 0; i < children.size(); i++) {
			newNode.addChild(children.get(i).deepCopy());
		}
		newNode.parent = this.parent;
		return newNode;
	}
	
	public String toString() {
		return "log(" + children.get(0).toString() + ")";
	}
}
