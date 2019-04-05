package sym_derivation.sym_derivation;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import sym_derivation.symderivation.SymFunction;
import sym_derivation.symderivation.SymVar;
import sym_derivation.symderivation.arith.SymInv;
import sym_derivation.symderivation.arith.SymPow;
import sym_derivation.symderivation.arith.SymProd;
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

public class EvalTest {
	private SymFunction testf;
	private HashMap<String, Double> param;
	private static double VALUE_PARAM = 5;
	private static double VALUE_PARAM2 = -2.34;
	private static double THRESHOLD = 0.001;
	
	@Before
	public void before() {
		param = new HashMap<String, Double>();
		param.put("x", VALUE_PARAM);
		param.put("y", VALUE_PARAM2);
	}
	
	@Test
	public void testConstant() {
		testf = new SymZero();
		assertEquals(0, testf.eval(param), THRESHOLD);
		
		testf = new SymOne();
		assertEquals(1, testf.eval(param), THRESHOLD);
		
		testf = new SymConstant(-2);
		assertEquals(-2, testf.eval(param), THRESHOLD);
		
		testf = new SymConstant(3);
		assertEquals(3, testf.eval(param), THRESHOLD);
	}
	
	@Test
	public void testVar() {
		testf = new SymVar("x");
		assertEquals(VALUE_PARAM, testf.eval(param), THRESHOLD);
		testf = new SymVar("z");
		assertNull(testf.eval(param));
	}
	
	@Test
	public void testTrig() {
		SymFunction arg = new SymVar("x");
		testf = new SymSin(arg);
		assertEquals(Math.sin(VALUE_PARAM), testf.eval(param), THRESHOLD);
		testf = new SymCos(arg);
		assertEquals(Math.cos(VALUE_PARAM), testf.eval(param), THRESHOLD);
		testf = new SymAtan(arg);
		assertEquals(Math.atan(VALUE_PARAM), testf.eval(param), THRESHOLD);
		testf = new SymCos(new SymSin(arg));
		assertEquals(Math.cos(Math.sin(VALUE_PARAM)), testf.eval(param), THRESHOLD);
		
		arg = new SymVar("z");
		testf = new SymSin(arg);
		assertNull(testf.eval(param));
		testf = new SymCos(arg);
		assertNull(testf.eval(param));
		testf = new SymCos(new SymSin(arg));
		assertNull(testf.eval(param));
	}

	@Test
	public void testArith() {
		SymFunction arg1 = new SymVar("x");
		SymFunction arg2 = new SymVar("y");
		testf = new SymSum(arg1, arg2);
		assertEquals(VALUE_PARAM + VALUE_PARAM2, testf.eval(param), THRESHOLD);
		testf = new SymProd(arg1, arg2);
		assertEquals(VALUE_PARAM * VALUE_PARAM2, testf.eval(param), THRESHOLD);
		testf = new SymUnaryMinus(arg1);
		assertEquals(-VALUE_PARAM, testf.eval(param), THRESHOLD);
		
		testf = new SymPow(arg2, 3);
		assertEquals(VALUE_PARAM2*VALUE_PARAM2*VALUE_PARAM2, testf.eval(param), THRESHOLD);
		testf = new SymPow(arg2, 0);
		assertEquals(1.0, testf.eval(param), THRESHOLD);
		testf = new SymPow(arg2, 1);
		assertEquals(VALUE_PARAM2, testf.eval(param), THRESHOLD);
		testf = new SymPow(arg2, -3);
		assertEquals(1/(VALUE_PARAM2*VALUE_PARAM2*VALUE_PARAM2), testf.eval(param), THRESHOLD);
		
		testf = new SymInv(arg2);
		assertEquals(1/VALUE_PARAM2, testf.eval(param), THRESHOLD);
		
		arg1 = new SymSin(new SymVar("x"));
		arg2 = new SymCos(new SymVar("x"));
		testf = new SymSum(arg1, arg2);
		assertEquals(Math.sin(VALUE_PARAM) + Math.cos(VALUE_PARAM), testf.eval(param), THRESHOLD);
		testf = new SymProd(arg1, arg2);
		assertEquals(Math.sin(VALUE_PARAM) * Math.cos(VALUE_PARAM), testf.eval(param), THRESHOLD);
	}
	
	@Test
	public void testTras() {
		SymFunction arg = new SymVar("x");
		testf = new SymExp(new SymSin(arg));
		assertEquals(Math.exp(Math.sin(VALUE_PARAM)), testf.eval(param), THRESHOLD);
		testf = new SymLog(arg);
		assertEquals(Math.log(VALUE_PARAM), testf.eval(param), THRESHOLD);
	}
}
