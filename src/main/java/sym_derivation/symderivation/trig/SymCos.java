package sym_derivation.symderivation.trig;

import java.util.HashMap;

import sym_derivation.symderivation.SymFunction;
import sym_derivation.symderivation.arith.SymUnaryMinus;
import sym_derivation.symderivation.constant.SymZero;
import sym_derivation.symderivation.arith.SymProd;

/**
 * Cosine function.
 * 
 * Token notation: cos.
 * 
 * @author Angel Gonzalez-Prieto
 *
 */
public class SymCos extends SymFunction{	
	SymFunction arg;
	
	public SymCos(SymFunction arg) {
		this.arg = arg;
	}

	public Double eval(HashMap<String, Double>  param) {
		Double argValue = arg.eval(param);
		
		if(argValue == null) {
			return null;
		}
		
		if(argValue.isNaN()) {
			return Double.NaN;
		}else {
			return Math.cos(argValue);
		}
	}

	public SymFunction diff(String var) {
		SymFunction dif = arg.diff(var);
		if(dif instanceof SymZero) {
			return new SymZero();
		}
		return new SymUnaryMinus(new SymProd(new SymSin(arg), dif));
	}
	
	@Override
	public String toInfix() {
		return "cos(" + arg.toInfix() + ")";
	}
	
	@Override
	public String toJavaCode() {
		return "Math.cos(" + arg.toJavaCode() + ")";
	}

	@Override
	public int getDepth() {
		return arg.getDepth()+1;
	}
}
