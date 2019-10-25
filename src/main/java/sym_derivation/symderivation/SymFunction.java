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
	/**
	 * Evaluates the symbolic function on a point and
	 * returns its value.
	 * 
	 * @param param Dictionary that associates each of
	 * the parameters of the symbolic function to
	 * their value, represented by a Double.
	 * @return Value of the function computed. If there
	 * is an expected parameter of the function that is
	 * missing in the dictionary, the function returns null.
	 */
	public abstract Double eval(HashMap<String, Double> param);
	
	/**
	 * Computes the derivative of the symbolic function with
	 * respect to the variable var.
	 * 
	 * @param param Variable on which the derivative
	 * will be computed.
	 * @return Symbolic function representing the
	 * computed derivative. If var is not a parameter
	 * of the function, consistently it returns Zero.
	 */
	public abstract SymFunction diff(String var);
	
	/**
	 * Returns a string representing the infix notation
	 * of the function.
	 * 
	 * @return String with the resulting string in
	 * infix notation.
	 */
	public abstract String toInfix();
	
	/**
	 * Returns a string implementing the Java code
	 * to be executed in order to compute the same
	 * function as the initial symbolic function.
	 * 
	 * @return Java code of the function.
	 */
	public abstract String toJavaCode();
	
	/**
	 * Return the maximum depth of the function
	 * i.e. the maximum number of compositions
	 * of elementary functions.
	 * 
	 * @return Integer with the depth of the function.
	 */
	public abstract int getDepth();
	
	/**
	 * Accepts a string f and returns the corresponding
	 * symbolic function. The string f is assumed to be
	 * written in prefix notation.
	 * 
	 * @param f String of the symbolic function
	 * @return Symbolic function parsed.
	 */
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
	
	/**
	 * Auxiliar function for the parsing process.
	 * It accepts a queue of strings and computes
	 * recursively the corresponding symbolic function.
	 * 
	 * @param q Queue of strings.
	 * @return Symbolic function parsed.
	 */
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
