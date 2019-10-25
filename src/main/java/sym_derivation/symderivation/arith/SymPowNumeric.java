package sym_derivation.symderivation.arith;

import java.util.HashMap;

import sym_derivation.symderivation.SymFunction;
import sym_derivation.symderivation.constant.SymConstant;
import sym_derivation.symderivation.constant.SymZero;

/**
 * Pow function with only numerical exponents
 * (no symbolic functions accepted).
 * 
 * Token notation: pown.
 * 
 * @author Angel Gonzalez-Prieto
 *
 */
public class SymPowNumeric extends SymFunction{
	private SymFunction arg;
	private int pow;
	
	private static double THRESHOLD = 10e-15;
	
	public SymPowNumeric(SymFunction arg, int pow) {
		this.arg=arg;
		this.pow = pow;
	}

	public Double eval(HashMap<String, Double> param) {
		Double argValue = arg.eval(param);
		
		if(argValue == null) {
			return null;
		}
		
		if(pow == 0) {
			return 1.0;
		}else if(argValue.isNaN() ||
				(pow<0 && Math.abs(argValue)<THRESHOLD)) {
			return Double.NaN;
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
		
		SymFunction dif = arg.diff(var);
		if(dif instanceof SymZero) {
			return new SymZero();
		}
		return new SymProd(new SymConstant(pow),
				new SymProd(new SymPowNumeric(arg, pow-1), dif));
	}

	@Override
	public String toInfix() {
		return "(" + arg.toInfix() + ")^(" + Integer.toString(pow) + ")";
	}

	@Override
	public String toJavaCode() {
		return "Math.pow(" + arg.toJavaCode() + "," + Integer.toString(pow) + ")";
	}

	@Override
	public int getDepth() {
		return arg.getDepth()+1;
	}
}
