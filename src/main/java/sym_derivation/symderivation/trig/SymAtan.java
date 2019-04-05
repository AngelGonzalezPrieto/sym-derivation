package sym_derivation.symderivation.trig;

import java.util.HashMap;

import sym_derivation.symderivation.SymFunction;
import sym_derivation.symderivation.arith.SymUnaryMinus;
import sym_derivation.symderivation.constant.SymOne;
import sym_derivation.symderivation.arith.SymInv;
import sym_derivation.symderivation.arith.SymPow;
import sym_derivation.symderivation.arith.SymProd;
import sym_derivation.symderivation.arith.SymSum;

public class SymAtan extends SymFunction{	
	SymFunction arg;
	
	public SymAtan(SymFunction arg) {
		this.arg = arg;
	}

	public Double eval(HashMap<String, Double>  param) {
		Double argValue = arg.eval(param);
		
		if(argValue == null) {
			return null;
		}else {
			return Math.atan(argValue);
		}
	}

	public SymFunction diff(String var) {
		return new SymProd(new SymInv(new SymSum(new SymOne(), new SymPow(arg,2))), arg.diff(var));
	}
}
