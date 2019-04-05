package sym_derivation.symderivation;

import java.util.HashMap;

import sym_derivation.symderivation.constant.SymOne;
import sym_derivation.symderivation.constant.SymZero;

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

}
