package sym_derivation.symderivation.arith;

import java.util.HashMap;

import sym_derivation.symderivation.SymFunction;

public class SymUnaryMinus extends SymFunction{
	SymFunction arg;
	
	public SymUnaryMinus(SymFunction arg) {
		this.arg=arg;
	}

	public Double eval(HashMap<String, Double> param) {
		Double argValue = arg.eval(param);
		
		if(argValue == null) {
			return null;
		}else {
			return -argValue;
		}
	}

	public SymFunction diff(String var) {
		return new SymUnaryMinus(arg.diff(var));
	}

}
