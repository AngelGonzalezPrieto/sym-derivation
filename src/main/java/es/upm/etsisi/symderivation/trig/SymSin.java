package es.upm.etsisi.symderivation.trig;

import java.util.HashMap;

import es.upm.etsisi.symderivation.SymFunction;
import es.upm.etsisi.symderivation.arith.SymProd;
import es.upm.etsisi.symderivation.constant.SymZero;

/**
 * Sine function.
 * 
 * Token notation: sin.
 * 
 * @author Angel Gonzalez-Prieto
 *
 */
public class SymSin extends SymFunction{
	SymFunction arg;
	
	public SymSin(SymFunction arg) {
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
	
	@Override
	public String toInfix() {
		return "sin(" + arg.toInfix() + ")";
	}
	
	@Override
	public String toJavaCode() {
		return "Math.sin(" + arg.toJavaCode() + ")";
	}

	@Override
	public int getDepth() {
		return arg.getDepth()+1;
	}
}
