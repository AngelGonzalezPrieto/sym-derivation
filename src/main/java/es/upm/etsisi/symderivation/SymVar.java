package es.upm.etsisi.symderivation;

import java.util.HashMap;

import es.upm.etsisi.symderivation.constant.SymOne;
import es.upm.etsisi.symderivation.constant.SymZero;

/**
 * Symbolic variable of a symbolic function.
 * 
 * @author Angel Gonzalez-Prieto
 *
 */
public class SymVar extends SymFunction{
	String nameVar;
	
	public SymVar(String nameVar) {
		this.nameVar = nameVar;
	}
		
	public Double eval(HashMap<String, Double> param) {
		return param.get(nameVar);
	}

	public SymFunction diff(String var) {
		if(var.equals(nameVar)) {
			return new SymOne();
		}else {
			return new SymZero();
		}
	}

	@Override
	public String toInfix() {
		return nameVar;
	}

	@Override
	public String toJavaCode() {
		return nameVar;
	}

	@Override
	public int getDepth() {
		return 1;
	}

	
}
