package es.upm.etsisi.symderivation.constant;

import java.util.HashMap;

import es.upm.etsisi.symderivation.SymFunction;

/**
 * Constant symbolic functions. Any real value
 * of the constant is allowed.
 * 
 * Token notation: const.
 * 
 * @author Angel Gonzalez-Prieto
 *
 */
public class SymConstant extends SymFunction {
	
	double value;
	
	public SymConstant(double value) {
		this.value=value;
	}

	public Double eval(HashMap<String, Double> param) {
		return value;
	}

	public SymFunction diff(String var) {
		return new SymZero();
	}

	@Override
	public String toInfix() {
		return Double.toString(value);
	}
	
	@Override
	public String toJavaCode() {
		return Double.toString(value);
	}

	@Override
	public int getDepth() {
		return 1;
	}
}
