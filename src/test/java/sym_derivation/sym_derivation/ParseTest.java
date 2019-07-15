package sym_derivation.sym_derivation;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import sym_derivation.symderivation.SymFunction;

public class ParseTest {
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
		testf = SymFunction.parse("Zero");
		assertEquals(0, testf.eval(param), THRESHOLD);
		
		testf = SymFunction.parse("One");
		assertEquals(1, testf.eval(param), THRESHOLD);
		
		testf = SymFunction.parse("const -2");
		assertEquals(-2, testf.eval(param), THRESHOLD);
		
		testf = SymFunction.parse("const 3");
		assertEquals(3, testf.eval(param), THRESHOLD);
	}
	
	@Test
	public void testVar() {
		testf = SymFunction.parse(" x");
		assertEquals(VALUE_PARAM, testf.eval(param), THRESHOLD);
		testf = SymFunction.parse(" z");
		assertNull(testf.eval(param));
	}
	
	@Test
	public void testTrig() {
		testf = SymFunction.parse("sin x");
		assertEquals(Math.sin(VALUE_PARAM), testf.eval(param), THRESHOLD);
		testf = SymFunction.parse("cos    x");
		assertEquals(Math.cos(VALUE_PARAM), testf.eval(param), THRESHOLD);
		testf = SymFunction.parse(" atan    x");
		assertEquals(Math.atan(VALUE_PARAM), testf.eval(param), THRESHOLD);
		testf = SymFunction.parse(" atan    cos x");
		assertEquals(Math.atan(Math.cos(VALUE_PARAM)), testf.eval(param), THRESHOLD);
		testf = SymFunction.parse("  cos   sin  x");
		assertEquals(Math.cos(Math.sin(VALUE_PARAM)), testf.eval(param), THRESHOLD);
		

		testf = SymFunction.parse("z");
		assertNull(testf.eval(param));
		testf = SymFunction.parse("cos z");
		assertNull(testf.eval(param));
		testf = SymFunction.parse("sin   z");
		assertNull(testf.eval(param));
	}

	@Test
	public void testArith() {
		testf = SymFunction.parse("+ x y");
		assertEquals(VALUE_PARAM + VALUE_PARAM2, testf.eval(param), THRESHOLD);
		testf = SymFunction.parse("- x y");
		assertEquals(VALUE_PARAM - VALUE_PARAM2, testf.eval(param), THRESHOLD);
		testf = SymFunction.parse("* x y");
		assertEquals(VALUE_PARAM * VALUE_PARAM2, testf.eval(param), THRESHOLD);
		testf = SymFunction.parse("-- x");
		assertEquals(-VALUE_PARAM, testf.eval(param), THRESHOLD);
		
		testf = SymFunction.parse("pown y 3");
		assertEquals(VALUE_PARAM2*VALUE_PARAM2*VALUE_PARAM2, testf.eval(param), THRESHOLD);
		testf = SymFunction.parse("pow y const 3");
		assertEquals(VALUE_PARAM2*VALUE_PARAM2*VALUE_PARAM2, testf.eval(param), THRESHOLD);
		
		
		testf = SymFunction.parse("pown y 0");
		assertEquals(1.0, testf.eval(param), THRESHOLD);
		testf = SymFunction.parse("pow y Zero");
		assertEquals(1.0, testf.eval(param), THRESHOLD);
		
		testf = SymFunction.parse("pown y 1");
		assertEquals(VALUE_PARAM2, testf.eval(param), THRESHOLD);
		testf = SymFunction.parse("pow y One");
		assertEquals(VALUE_PARAM2, testf.eval(param), THRESHOLD);
		
		testf = SymFunction.parse("pown y -3");
		assertEquals(1/(VALUE_PARAM2*VALUE_PARAM2*VALUE_PARAM2), testf.eval(param), THRESHOLD);
		testf = SymFunction.parse("pow y const -3");
		assertEquals(1/(VALUE_PARAM2*VALUE_PARAM2*VALUE_PARAM2), testf.eval(param), THRESHOLD);
		
		testf = SymFunction.parse("pow y sin  x");
		assertEquals(Math.pow(VALUE_PARAM2, Math.sin(VALUE_PARAM)), testf.eval(param), THRESHOLD);
		
		testf = SymFunction.parse("inv y");
		assertEquals(1/VALUE_PARAM2, testf.eval(param), THRESHOLD);
		
		testf = SymFunction.parse("+ sin x cos x");
		assertEquals(Math.sin(VALUE_PARAM) + Math.cos(VALUE_PARAM), testf.eval(param), THRESHOLD);
		testf = SymFunction.parse("* sin x cos x");
		assertEquals(Math.sin(VALUE_PARAM) * Math.cos(VALUE_PARAM), testf.eval(param), THRESHOLD);
	}

	@Test
	public void testComposition() {
		testf = SymFunction.parse("inv sin + x pown cos * x + y One 3");
		assertEquals(Math.pow(Math.sin(Math.pow(Math.cos(VALUE_PARAM*(VALUE_PARAM2+1)), 3)+ VALUE_PARAM)
				, -1), testf.eval(param), THRESHOLD);
		
		testf = SymFunction.parse("inv sin + x pow cos * x + y One const 3");
		assertEquals(Math.pow(Math.sin(Math.pow(Math.cos(VALUE_PARAM*(VALUE_PARAM2+1)), 3)+ VALUE_PARAM)
				, -1), testf.eval(param), THRESHOLD);

	}

	@Test
	public void testTras() {
		testf = SymFunction.parse("exp sin x");
		assertEquals(Math.exp(Math.sin(VALUE_PARAM)), testf.eval(param), THRESHOLD);
		testf = SymFunction.parse("log x");
		assertEquals(Math.log(VALUE_PARAM), testf.eval(param), THRESHOLD);
	}
	
	@Test
	public void testInput() {
		String str = "-(exp(atan(pu5)),-(-(*(pu1,qi5),exp(pu3)),-(atan(-(atan(qi3),-(-(*(pu1,-(-(*(pu1,qi5),-(pu1,pu1)),-(atan(-(atan(qi3),-(-(qi5,sin(-(*(pu1,-(-(*(pu1,qi5),exp(pu3)),-(atan(-(atan(qi3),-(-(*(pu1,-(-(*(pu1,qi5),exp(pu3)),-(atan(-(atan(qi3),-(-(*(pu1,qi5),sin(-(pu1,pu1))),atan(qi3)))),qi5))),sin(atan(pu5))),pu1))),qi5))),exp(qi2)))),pu1))),qi5))),sin(atan(pu5))),pu1))),qi5)))";
		/** str = str.replace(',', ' ');**/
		testf = SymFunction.parse(str);
		System.out.println(testf.toInfix());
	}
}
