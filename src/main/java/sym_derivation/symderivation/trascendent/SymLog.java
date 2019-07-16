package sym_derivation.symderivation.trascendent;

import java.util.HashMap;

import sym_derivation.symderivation.SymFunction;
import sym_derivation.symderivation.constant.SymZero;
import sym_derivation.symderivation.arith.SymInv;
import sym_derivation.symderivation.arith.SymProd;

public class SymLog extends SymFunction{	
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
