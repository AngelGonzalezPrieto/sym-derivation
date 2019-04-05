package sym_derivation.sym_derivation;

import java.util.ArrayList;
import java.util.Arrays;

public class SymVars {
	ArrayList<String> variableNames;

	public SymVars() {
		this.variableNames = new ArrayList<String>();
	}
	
	public SymVars(String[] variableNames) {
		this.variableNames = new ArrayList<String>(Arrays.asList(variableNames));
	}
	
	public boolean add(String varName) {
		if(variableNames.contains(varName)) {
			return false;
		}
		
		variableNames.add(varName);
		return true;
	}
	
	public boolean remove(String varName) {
		return variableNames.remove(varName);
	}
}
