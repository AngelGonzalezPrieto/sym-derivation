package sym_derivation.symderivation.arith;

import java.util.HashMap;

import sym_derivation.symderivation.SymFunction;
import sym_derivation.symderivation.constant.SymOne;
import sym_derivation.symderivation.constant.SymZero;
import sym_derivation.symderivation.trascendent.SymLog;

public class SymPow extends SymFunction{
	private SymFunction base;
	private SymFunction pow;
		
	public SymPow(SymFunction arg, SymFunction pow) {
		this.base=arg;
		this.pow = pow;
	}

	public Double eval(HashMap<String, Double> param) {
		Double argValue;
		Double powValue;
		
		if(pow instanceof SymZero) {
			return 1.0;
		}else if(pow instanceof SymOne) {
			return base.eval(param);
		}
		
		argValue = base.eval(param);
		powValue = pow.eval(param);
		
		if(powValue == 0) {
			return 1.0;
		}else if(argValue == null || powValue == null ||
				(powValue <0 && Math.abs(argValue)<0.0)) {
			return null;
		}else {
			return Math.pow(argValue, powValue);
		}
	}

	public SymFunction diff(String var) {
		if(pow instanceof SymZero) {
			return new SymZero();
		}else if(pow instanceof SymOne) {
			return base.diff(var);
		}
		
		SymFunction difbase = base.diff(var);
		SymFunction difexp = pow.diff(var);
		
		if(difexp instanceof SymZero) {
			return new SymProd(new SymProd(pow, difbase), new SymPow(base, new SymSubs(pow, new SymOne())));
		}else if(difbase instanceof SymZero) {
			return new SymProd(new SymProd(difexp, new SymLog(base)), new SymPow(base, pow));
		}
		
		return new SymSum(new SymProd(new SymProd(difexp, new SymLog(base)), new SymPow(base, pow)),
				new SymProd(new SymProd(pow, difbase), new SymPow(base, new SymSubs(pow, new SymOne()))));
	}

}
