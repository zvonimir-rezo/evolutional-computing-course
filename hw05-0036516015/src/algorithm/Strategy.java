package algorithm;

import functions.BestSelection;
import functions.ExpCrossover;
import functions.RandomSelection;
import functions.SubtractMutation;
import functions.UniformCrossover;
import interfaces.ICrossover;
import interfaces.IMutation;
import interfaces.ISelection;

public class Strategy {

	private ISelection selection;
	private IMutation mutation;
	private ICrossover crossover;
	
	public Strategy(String input) {
		String[] splitInput = input.split("/");
		if (splitInput[1].equals("rand")) {
			selection = new RandomSelection();
		} else if (splitInput[1].equals("best")) {
			selection = new BestSelection();
		}
		
		if (splitInput[2].equals("1")) {
			mutation = new SubtractMutation();
		}
		
		if (splitInput[3].equals("bin")) {
			crossover = new UniformCrossover();
		} else if (splitInput[3].equals("exp")) {
			crossover = new ExpCrossover(0.8);
		}
	}

	public ISelection getSelection() {
		return selection;
	}

	public IMutation getMutation() {
		return mutation;
	}

	public ICrossover getCrossover() {
		return crossover;
	}
	
	
	
}
