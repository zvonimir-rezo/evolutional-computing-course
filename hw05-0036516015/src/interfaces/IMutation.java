package interfaces;

public interface IMutation {

	public double[][] mutate(double[][] solutions);
	
	public double[] mutateOne(double[] baseVector, double[] first, double[] second, double coef);
	
}
