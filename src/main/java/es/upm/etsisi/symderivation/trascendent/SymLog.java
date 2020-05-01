package es.upm.etsisi.symderivation.trascendent;

import java.util.HashMap;

import es.upm.etsisi.symderivation.SymFunction;
import es.upm.etsisi.symderivation.arith.SymProd;
import es.upm.etsisi.symderivation.constant.SymZero;
import es.upm.etsisi.symderivation.arith.SymInv;

/**
 * Natural logarithm function (with base e).
 * 
 * Token notation: log.
 * 
 * @author Angel Gonzalez-Prieto
 *
 */
public class SymLog extends SymFunction {
	SymFunction arg;
	
	private static double THRESHOLD = 10e-15;
	
	public SymLog(SymFunction arg) {
		this.arg = arg;
	}

	public Double eval(HashMap<String, Double>  param) {
		Double argValue = arg.eval(param);
		
		if(argValue == null) {
			return null;
		}
		
		if(argValue.isNaN() || Math.abs(argValue) < THRESHOLD) {
			return Double.NaN;
		}else {
			return Math.log(argValue);
		}
	}

	public SymFunction diff(String var) {
		SymFunction dif = arg.diff(var);
		if(dif instanceof SymZero) {
			return new SymZero();
		}
		
		return new SymProd(new SymInv(arg), dif);
	}

	@Override
	public String toInfix() {
		return "log(" + arg.toInfix() + ")";
	}
	
	@Override
	public String toJavaCode() {
		return "Math.log(" + arg.toJavaCode() + ")";
	}

	@Override
	public int getDepth() {
		return arg.getDepth()+1;
	}
}
