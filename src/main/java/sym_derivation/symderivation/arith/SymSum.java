package sym_derivation.symderivation.arith;

import java.util.HashMap;

import sym_derivation.symderivation.SymFunction;
import sym_derivation.symderivation.constant.SymZero;

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
		}
		
		if(arg1Value.isNaN() || arg2Value.isNaN()) {
			return Double.NaN;
		}else {
			return arg1Value+arg2Value;
		}
	}

	public SymFunction diff(String var) {
		SymFunction dif1 = arg1.diff(var);
		SymFunction dif2 = arg2.diff(var);
		
		if(dif1 instanceof SymZero && dif2 instanceof SymZero) {
			return new SymZero();
		}
		else if(dif1 instanceof SymZero) {
			return dif2;
		}
		else if(dif2 instanceof SymZero) {
			return dif1;
		}
		
		return new SymSum(dif1, dif2);
	}

	@Override
	public String toInfix() {
		return "(" + arg1.toInfix() + ") + (" + arg2.toInfix() + ")";
	}
}
