package sym_derivation.symderivation.arith;

import java.util.HashMap;

import sym_derivation.symderivation.SymFunction;
import sym_derivation.symderivation.constant.SymConstant;
import sym_derivation.symderivation.constant.SymZero;

public class SymPow extends SymFunction{
	private SymFunction arg;
	private int pow;
	
	private static double THRESHOLD = 10e-15;
	
	public SymPow(SymFunction arg, int pow) {
		this.arg=arg;
		this.pow = pow;
	}

	public Double eval(HashMap<String, Double> param) {
		Double argValue = arg.eval(param);
		
		if(pow == 0) {
			return 1.0;
		}else if(argValue == null ||
				(pow<0 && Math.abs(argValue)<THRESHOLD)) {
			return null;
		}else {
			return Math.pow(argValue, pow);
		}
	}

	public SymFunction diff(String var) {
		if(pow == 0) {
			return new SymZero();
		}else if(pow == 1) {
			return arg.diff(var);
		}else if(pow == 2) {
			return new SymProd(new SymConstant(2),
					new SymProd(arg, arg.diff(var)));
		}
		
		return new SymProd(new SymConstant(pow),
				new SymProd(new SymPow(arg, pow-1), arg.diff(var)));
	}

}
