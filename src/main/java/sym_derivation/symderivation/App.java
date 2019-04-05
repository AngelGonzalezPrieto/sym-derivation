package sym_derivation.symderivation;

import java.util.HashMap;

public class App {

	public static void main(String[] args) {
		HashMap<String, Double> param = new HashMap<String, Double>();
		param.put("x", 3.0);
		param.put("y", 2.0);
		
		SymFunction f = SymFunction.parse("  sin   cos + x y");
		System.out.println(f.eval(param));
	}

}
