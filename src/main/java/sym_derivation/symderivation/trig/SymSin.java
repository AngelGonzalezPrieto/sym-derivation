package sym_derivation.symderivation.trig;

import java.util.ArrayList;
import java.util.HashMap;

import sym_derivation.symderivation.SymFunction;
import sym_derivation.symderivation.arith.SymProd;
import sym_derivation.symderivation.arith.SymSum;
import sym_derivation.symderivation.constant.SymZero;

public class SymSin extends SymFunction{
	SymFunction arg;
	
	public SymSin(SymFunction arg) {
		this.arg = arg;
	}

	public Double eval(HashMap<String, Double>  param) {
		Double argValue = arg.eval(param);
		
		if(argValue == null) {
			return null;
		}else {
			return Math.sin(argValue);
		}
	}

	public SymFunction diff(String var) {
		SymFunction dif = arg.diff(var);
		if(dif instanceof SymZero) {
			return new SymZero();
		}
		return new SymProd(new SymCos(arg), dif);
	}
}
