package es.upm.etsisi.symderivation.trascendent;

import java.util.HashMap;

import es.upm.etsisi.symderivation.SymFunction;
import es.upm.etsisi.symderivation.arith.SymProd;
import es.upm.etsisi.symderivation.constant.SymZero;

/**
 * Exponential function with base e.
 * 
 * Token notation: exp.
 * 
 * @author Angel Gonzalez-Prieto
 *
 */
public class SymExp extends SymFunction {
	SymFunction arg;
	
	public SymExp(SymFunction arg) {
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
			return Math.exp(argValue);
		}
	}

	public SymFunction diff(String var) {
		SymFunction dif = arg.diff(var);
		if(dif instanceof SymZero) {
			return new SymZero();
		}
		
		return new SymProd(new SymExp(arg), dif);
	}

	@Override
	public String toInfix() {
		return "exp(" + arg.toInfix() + ")";
	}

	@Override
	public String toJavaCode() {
		return "Math.exp(" + arg.toJavaCode() + ")";
	}

	@Override
	public int getDepth() {
		return arg.getDepth()+1;
	}
}
