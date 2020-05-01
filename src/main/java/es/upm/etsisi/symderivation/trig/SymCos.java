package es.upm.etsisi.symderivation.trig;

import java.util.HashMap;

import es.upm.etsisi.symderivation.SymFunction;
import es.upm.etsisi.symderivation.arith.SymUnaryMinus;
import es.upm.etsisi.symderivation.constant.SymZero;
import es.upm.etsisi.symderivation.arith.SymProd;

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
