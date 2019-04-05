package sym_derivation.symderivation.trascendent;

import java.util.HashMap;

import sym_derivation.symderivation.SymFunction;
import sym_derivation.symderivation.arith.SymUnaryMinus;
import sym_derivation.symderivation.arith.SymProd;

public class SymExp extends SymFunction{	
	SymFunction arg;
	
	public SymExp(SymFunction arg) {
		this.arg = arg;
	}

	public Double eval(HashMap<String, Double>  param) {
		Double argValue = arg.eval(param);
		
		if(argValue == null) {
			return null;
		}else {
			return Math.exp(argValue);
		}
	}

	public SymFunction diff(String var) {
		return new SymProd(new SymExp(arg), arg.diff(var));
	}
}
