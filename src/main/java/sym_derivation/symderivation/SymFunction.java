package sym_derivation.symderivation;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import sym_derivation.symderivation.arith.SymInv;
import sym_derivation.symderivation.arith.SymPow;
import sym_derivation.symderivation.arith.SymPowNumeric;
import sym_derivation.symderivation.arith.SymProd;
import sym_derivation.symderivation.arith.SymSubs;
import sym_derivation.symderivation.arith.SymSum;
import sym_derivation.symderivation.arith.SymUnaryMinus;
import sym_derivation.symderivation.constant.SymConstant;
import sym_derivation.symderivation.constant.SymOne;
import sym_derivation.symderivation.constant.SymZero;
import sym_derivation.symderivation.trascendent.SymExp;
import sym_derivation.symderivation.trascendent.SymLog;
import sym_derivation.symderivation.trig.SymAtan;
import sym_derivation.symderivation.trig.SymCos;
import sym_derivation.symderivation.trig.SymSin;

public abstract class SymFunction {
	public abstract Double eval(HashMap<String, Double> param);
	public abstract SymFunction diff(String var);
	
	public static SymFunction parse(String f) {
		Queue<String> q = new LinkedList<String>(Arrays.asList(f.split(" +")));
		
		while(!q.isEmpty() && q.peek().equals("")) {
			q.remove();
		}
		
		if(q.isEmpty()) {
			return null;
		}else {
			return parseTokens(q);
		}
	}
	
	private static SymFunction parseTokens(Queue<String> q) {
		SymFunction arg1, arg2;		
		if(q.isEmpty()) {
			return null;
		}
		
		String token = q.remove();
		if(token.equals("sin")) {
			//System.out.println("Created sin");
			arg1 = parseTokens(q);
			
			return new SymSin(arg1);
		}
		else if(token.equals("cos")) {
			//System.out.println("Created cos");
			arg1 = parseTokens(q);
			
			return new SymCos(arg1);
		}
		else if(token.equals("atan")) {
			//System.out.println("Created atan");
			arg1 = parseTokens(q);
			
			return new SymAtan(arg1);
		}
		else if(token.equals("exp")) {
			//System.out.println("Created exp");
			arg1 = parseTokens(q);
			
			return new SymExp(arg1);
		}
		else if(token.equals("log")) {
			//System.out.println("Created log");
			arg1 = parseTokens(q);
			
			return new SymLog(arg1);
		}
		else if(token.equals("+")) {
			//System.out.println("Created +");
			arg1 = parseTokens(q);
			arg2 = parseTokens(q);
			
			return new SymSum(arg1, arg2);
		}
		else if(token.equals("*")) {
			//System.out.println("Created *");
			arg1 = parseTokens(q);
			arg2 = parseTokens(q);
			
			return new SymProd(arg1, arg2);
		}
		else if(token.equals("-")) {
			//System.out.println("Created -");
			arg1 = parseTokens(q);
			arg2 = parseTokens(q);
			
			return new SymSubs(arg1, arg2);
		}
		else if(token.equals("--")) {
			//System.out.println("Created unary -");
			arg1 = parseTokens(q);
			
			return new SymUnaryMinus(arg1);
		}
		else if(token.equals("inv")) {
			//System.out.println("Created Inv");
			arg1 = parseTokens(q);
			
			return new SymInv(arg1);
		}
		else if(token.equals("pow")) {
			//System.out.println("Created Pow");
			arg1 = parseTokens(q);
			arg2 = parseTokens(q);
			
			return new SymPow(arg1, arg2);
		}
		else if(token.equals("pown")) {
			//System.out.println("Created Pown");
			arg1 = parseTokens(q);
			String value = q.remove();
			
			return new SymPowNumeric(arg1, Integer.parseInt(value));
		}
		else if(token.equals("const")) {
			//System.out.println("Created Constant");

			String value = q.remove();
			
			return new SymConstant(Double.parseDouble(value));
		}
		else if(token.equals("Zero")) {
			//System.out.println("Created Zero");
			
			return new SymZero();
		}
		else if(token.equals("One")) {
			//System.out.println("Created One");
			
			return new SymOne();
		}
		else{
			//System.out.println("Create var: "+token);
			return new SymVar(token);
		}		
	}
}
