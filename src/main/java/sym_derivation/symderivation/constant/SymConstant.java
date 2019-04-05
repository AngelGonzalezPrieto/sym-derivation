package sym_derivation.symderivation.constant;

import java.util.HashMap;

import sym_derivation.symderivation.SymFunction;

public class SymConstant extends SymFunction{
	
	double value;
	
	public SymConstant(double value) {
		this.value=value;
	}

	public Double eval(HashMap<String, Double> param) {
		return value;
	}

	public SymFunction diff(String var) {
		return new SymZero();
	}
}
