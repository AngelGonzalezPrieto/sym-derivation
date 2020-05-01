package es.upm.etsisi.symderivation;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import es.upm.etsisi.symderivation.arith.SymInv;
import es.upm.etsisi.symderivation.arith.SymPow;
import es.upm.etsisi.symderivation.arith.SymPowNumeric;
import es.upm.etsisi.symderivation.arith.SymProd;
import es.upm.etsisi.symderivation.arith.SymSum;
import es.upm.etsisi.symderivation.arith.SymUnaryMinus;
import es.upm.etsisi.symderivation.constant.SymConstant;
import es.upm.etsisi.symderivation.constant.SymOne;
import es.upm.etsisi.symderivation.constant.SymZero;
import es.upm.etsisi.symderivation.trascendent.SymExp;
import es.upm.etsisi.symderivation.trascendent.SymLog;
import es.upm.etsisi.symderivation.trig.SymAtan;
import es.upm.etsisi.symderivation.trig.SymCos;
import es.upm.etsisi.symderivation.trig.SymSin;

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
		
		testf = new SymPowNumeric(arg2, 3);
		assertEquals(VALUE_PARAM2*VALUE_PARAM2*VALUE_PARAM2, testf.eval(param), THRESHOLD);
		testf = new SymPowNumeric(arg2, 0);
		assertEquals(1.0, testf.eval(param), THRESHOLD);
		testf = new SymPowNumeric(arg2, 1);
		assertEquals(VALUE_PARAM2, testf.eval(param), THRESHOLD);
		testf = new SymPowNumeric(arg2, -3);
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
	
	@Test
	public void testNaN() {
		SymFunction arg = new SymVar("x");
		param.put("x", -0.5);
		testf = new SymLog(arg);
		assertTrue(testf.eval(param).isNaN());
		testf = new SymPow(arg, arg);
		assertTrue(testf.eval(param).isNaN());
		testf = new SymLog(new SymSin(arg));
		assertTrue(testf.eval(param).isNaN());
	}
}
