package interfaces;
public interface ISelection {

	public double[][] select(double[][] population, double[] fValues);

	public double[][] select(double[][] population, int bestIndex, int i);
	
}
