package sym_derivation.symderivation.trig;

import java.util.HashMap;

import sym_derivation.symderivation.SymFunction;
import sym_derivation.symderivation.arith.SymUnaryMinus;
import sym_derivation.symderivation.arith.SymProd;

public class SymCos extends SymFunction{	
	SymFunction arg;
	
	public SymCos(SymFunction arg) {
		this.arg = arg;
	}

	public Double eval(HashMap<String, Double>  param) {
		Double argValue = arg.eval(param);
		
		if(argValue == null) {
			return null;
		}else {
			return Math.cos(argValue);
		}
	}

	public SymFunction diff(String var) {
		return new SymUnaryMinus(new SymProd(new SymSin(arg), arg.diff(var)));
	}
}
