package es.upm.etsisi.symderivation;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.instanceOf;

import es.upm.etsisi.symderivation.arith.SymInv;
import es.upm.etsisi.symderivation.arith.SymPowNumeric;
import es.upm.etsisi.symderivation.arith.SymProd;
import es.upm.etsisi.symderivation.arith.SymSubs;
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

public class DiffTest {

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
		assertThat(testf.diff("x"), instanceOf(SymZero.class));
		
		testf = new SymOne();
		assertThat(testf.diff("x"), instanceOf(SymZero.class));
		
		testf = new SymConstant(-2);
		assertThat(testf.diff("x"), instanceOf(SymZero.class));
		
		testf = new SymConstant(3);
		assertThat(testf.diff("x"), instanceOf(SymZero.class));
	}
	
	@Test
	public void testVar() {
		testf = new SymVar("x");
		assertThat(testf.diff("x"), instanceOf(SymOne.class));
		
		testf = new SymVar("z");
		assertThat(testf.diff("x"), instanceOf(SymZero.class));
	}
	
	@Test
	public void testTrig() {
		SymFunction arg = new SymVar("x");
		testf = new SymSin(arg);
		assertEquals(Math.cos(VALUE_PARAM), testf.diff("x").eval(param), THRESHOLD);
		assertEquals(0.0, testf.diff("y").eval(param), THRESHOLD);

		testf = new SymCos(arg);
		assertEquals(-Math.sin(VALUE_PARAM), testf.diff("x").eval(param), THRESHOLD);
		assertEquals(0.0, testf.diff("y").eval(param), THRESHOLD);

		testf = new SymCos(new SymSin(arg));
		assertEquals(-Math.sin(Math.sin(VALUE_PARAM))*Math.cos(VALUE_PARAM), testf.diff("x").eval(param), THRESHOLD);
		assertEquals(0.0, testf.diff("y").eval(param), THRESHOLD);
		
		testf = new SymAtan(arg);
		assertEquals(1/(1+VALUE_PARAM*VALUE_PARAM), testf.diff("x").eval(param), THRESHOLD);
		assertEquals(0.0, testf.diff("y").eval(param), THRESHOLD);
		
		testf = new SymAtan(new SymSin(arg));
		assertEquals(1/(1+Math.sin(VALUE_PARAM)*Math.sin(VALUE_PARAM))*Math.cos(VALUE_PARAM), testf.diff("x").eval(param), THRESHOLD);
		assertEquals(0.0, testf.diff("y").eval(param), THRESHOLD);
	}

	@Test
	public void testArith() {
		SymFunction arg1 = new SymSin(new SymVar("x"));
		SymFunction arg2 = new SymCos(new SymVar("x"));
		testf = new SymSum(arg1, arg2);
		assertEquals(Math.cos(VALUE_PARAM) - Math.sin(VALUE_PARAM), testf.diff("x").eval(param), THRESHOLD);
		
		arg1 = new SymSin(new SymVar("x"));
		arg2 = new SymSin(new SymVar("y"));
		testf = new SymSum(arg1, arg2);
		assertEquals(Math.cos(VALUE_PARAM), testf.diff("x").eval(param), THRESHOLD);
		
		arg1 = new SymSin(new SymVar("x"));
		arg2 = new SymSin(new SymVar("y"));
		testf = new SymSubs(arg1, arg2);
		assertEquals(Math.cos(VALUE_PARAM), testf.diff("x").eval(param), THRESHOLD);
		assertEquals(-Math.cos(VALUE_PARAM2), testf.diff("y").eval(param), THRESHOLD);
		
		arg1 = new SymSin(new SymVar("x"));
		arg2 = new SymSin(new SymVar("x"));
		testf = new SymProd(arg1, arg2);
		assertEquals(2*Math.sin(VALUE_PARAM)*Math.cos(VALUE_PARAM), testf.diff("x").eval(param), THRESHOLD);
		testf = new SymUnaryMinus(arg1);
		assertEquals(-Math.cos(VALUE_PARAM), testf.diff("x").eval(param), THRESHOLD);
		
		testf = new SymSubs(arg1, arg2);
		assertEquals(Math.cos(VALUE_PARAM)-Math.cos(VALUE_PARAM), testf.diff("x").eval(param), THRESHOLD);

		
		arg1 = new SymSin(new SymVar("x"));
		arg2 = new SymVar("x");
		testf = new SymProd(arg1, arg2);
		assertEquals(Math.sin(VALUE_PARAM)+VALUE_PARAM*Math.cos(VALUE_PARAM), testf.diff("x").eval(param), THRESHOLD);

		arg1 = new SymSin(new SymVar("x"));
		arg2 = new SymVar("y");
		testf = new SymProd(arg1, arg2);
		assertEquals(VALUE_PARAM2*Math.cos(VALUE_PARAM), testf.diff("x").eval(param), THRESHOLD);
		assertEquals(Math.sin(VALUE_PARAM), testf.diff("y").eval(param), THRESHOLD);
		
		arg1 = new SymSin(new SymVar("x"));
		arg2 = new SymSin(new SymVar("y"));
		testf = new SymProd(arg1, arg2);
		assertEquals(Math.cos(VALUE_PARAM)*Math.sin(VALUE_PARAM2), testf.diff("x").eval(param), THRESHOLD);
		
		testf = new SymPowNumeric(arg1, 3);
		assertEquals(3*Math.sin(VALUE_PARAM)*Math.sin(VALUE_PARAM)*Math.cos(VALUE_PARAM), testf.diff("x").eval(param), THRESHOLD);
		
		testf = new SymPowNumeric(arg1, 0);
		assertThat(testf.diff("x"), instanceOf(SymZero.class));

		testf = new SymPowNumeric(arg1, -3);
		assertEquals(-3*Math.pow(Math.sin(VALUE_PARAM),-4)*Math.cos(VALUE_PARAM), testf.diff("x").eval(param), THRESHOLD);
		
		testf = new SymInv(arg1);
		assertEquals(-Math.pow(Math.sin(VALUE_PARAM),-2)*Math.cos(VALUE_PARAM), testf.diff("x").eval(param), THRESHOLD);
	}

	
	@Test
	public void testTras() {
		SymFunction arg = new SymVar("x");
		testf = new SymExp(new SymSin(arg));
		assertEquals(Math.exp(Math.sin(VALUE_PARAM))*Math.cos(VALUE_PARAM), testf.diff("x").eval(param), THRESHOLD);
		testf = new SymLog(arg);
		assertEquals(Math.pow(VALUE_PARAM,-1), testf.diff("x").eval(param), THRESHOLD);
	}
}
