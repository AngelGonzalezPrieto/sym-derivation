package es.upm.etsisi.symderivation.trig;

import java.util.HashMap;

import es.upm.etsisi.symderivation.arith.SymPowNumeric;
import es.upm.etsisi.symderivation.arith.SymProd;
import es.upm.etsisi.symderivation.SymFunction;
import es.upm.etsisi.symderivation.constant.SymOne;
import es.upm.etsisi.symderivation.constant.SymZero;
import es.upm.etsisi.symderivation.arith.SymInv;
import es.upm.etsisi.symderivation.arith.SymSum;

/**
 * Inverse of the tan function (arctan function).
 * 
 * Token notation: atan.
 * 
 * @author Angel Gonzalez-Prieto
 *
 */
public class SymAtan extends SymFunction{	
	SymFunction arg;
	
	public SymAtan(SymFunction arg) {
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
			return Math.atan(argValue);
		}
	}

	public SymFunction diff(String var) {
		SymFunction dif = arg.diff(var);
		if(dif instanceof SymZero) {
			return new SymZero();
		}
		
		return new SymProd(new SymInv(new SymSum(new SymOne(), new SymPowNumeric(arg,2))), dif);
	}

	@Override
	public String toInfix() {
		/*Warning: The string atan function is not
		 *correctly parsed by SageMath. For this
		 *reason, we substituted it by arctan.*/
		return "arctan(" + arg.toInfix() + ")";
	}
	
	@Override
	public String toJavaCode() {
		return "Math.atan(" + arg.toJavaCode() + ")";
	}

	@Override
	public int getDepth() {
		return arg.getDepth()+1;
	}
}
