package sym_derivation.symderivation.arith;

import java.util.ArrayList;
import java.util.HashMap;

import sym_derivation.symderivation.SymFunction;
import sym_derivation.symderivation.trig.SymCos;

public class SymSum extends SymFunction {

	SymFunction arg1, arg2;
	
	public SymSum(SymFunction arg1, SymFunction arg2) {
		this.arg1 = arg1;
		this.arg2 = arg2;
	}
	
	public Double eval(HashMap<String, Double> param) {
		Double arg1Value = arg1.eval(param);
		Double arg2Value = arg2.eval(param);
		
		if(arg1Value == null || arg2Value == null) {
			return null;
		}else {
			return arg1Value+arg2Value;
		}
	}

	public SymFunction diff(String var) {
		return new SymSum(arg1.diff(var), arg2.diff(var));
	}
}
