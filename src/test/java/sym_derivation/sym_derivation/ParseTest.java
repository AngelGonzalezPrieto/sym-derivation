package sym_derivation.sym_derivation;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Ignore;
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
		assertEquals(testf.getDepth(),2);
		
		testf = SymFunction.parse("pow y sin  x");
		assertEquals(Math.pow(VALUE_PARAM2, Math.sin(VALUE_PARAM)), testf.eval(param), THRESHOLD);
		assertEquals(testf.getDepth(),3);
		
		testf = SymFunction.parse("inv y");
		assertEquals(1/VALUE_PARAM2, testf.eval(param), THRESHOLD);
		
		testf = SymFunction.parse("+ sin x cos x");
		assertEquals(Math.sin(VALUE_PARAM) + Math.cos(VALUE_PARAM), testf.eval(param), THRESHOLD);
		assertEquals(testf.getDepth(),3);

		testf = SymFunction.parse("* sin x cos x");
		assertEquals(Math.sin(VALUE_PARAM) * Math.cos(VALUE_PARAM), testf.eval(param), THRESHOLD);
		assertEquals(testf.getDepth(),3);
	}

	@Test
	public void testComposition() {
		testf = SymFunction.parse("inv sin + x pown cos * x + y One 3");
		assertEquals(Math.pow(Math.sin(Math.pow(Math.cos(VALUE_PARAM*(VALUE_PARAM2+1)), 3)+ VALUE_PARAM)
				, -1), testf.eval(param), THRESHOLD);
		assertEquals(testf.getDepth(),8);

		testf = SymFunction.parse("inv sin + x pow cos * x + y One const 3");
		assertEquals(Math.pow(Math.sin(Math.pow(Math.cos(VALUE_PARAM*(VALUE_PARAM2+1)), 3)+ VALUE_PARAM)
				, -1), testf.eval(param), THRESHOLD);

	}

	@Test
	public void testTras() {
		testf = SymFunction.parse("exp sin x");
		assertEquals(Math.exp(Math.sin(VALUE_PARAM)), testf.eval(param), THRESHOLD);
		assertEquals(testf.getDepth(),3);
		testf = SymFunction.parse("log x");
		assertEquals(Math.log(VALUE_PARAM), testf.eval(param), THRESHOLD);
		assertEquals(testf.getDepth(),2);
	}
	
	@Test
	@Ignore
	public void testInput() {
		SymFunction testf1, testf2;
		String str1 = "-(exp(atan(pu5)),-(-(*(pu1,qi5),exp(pu3)),-(atan(-(atan(qi3),-(-(*(pu1,-(-(*(pu1,qi5),-(pu1,pu1)),-(atan(-(atan(qi3),-(-(qi5,sin(-(*(pu1,-(-(*(pu1,qi5),exp(pu3)),-(atan(-(atan(qi3),-(-(*(pu1,-(-(*(pu1,qi5),exp(pu3)),-(atan(-(atan(qi3),-(-(*(pu1,qi5),sin(-(pu1,pu1))),atan(qi3)))),qi5))),sin(atan(pu5))),pu1))),qi5))),exp(qi2)))),pu1))),qi5))),sin(atan(pu5))),pu1))),qi5)))";
		String str2 = "exp(atan(exp(+(exp(+(atan(qi1),pu1)),+(+(--(atan(exp(+(*(pu5,atan(exp(+(pu2,atan(exp(+(+(pu1,exp(atan(-(+(exp(atan(pu2)),atan(+(+(qi1,atan(pu1)),atan(exp(pu1))))),+(+(+(exp(atan(qi4)),qi0),atan(exp(+(exp(qi1),atan(qi1))))),+(+(pu1,exp(atan(pu2))),qi1)))))),qi1))))))),+(+(--(atan(+(+(qi1,atan(exp(+(*(pu5,qi1),pu2)))),qi1))),atan(*(pu2,qi1))),+(exp(qi3),atan(qi4))))))),atan(qi1)),atan(pu0))))))";
		str1 = str1.replace(',', ' ');
		str1 = str1.replace('(', ' ');
		str1 = str1.replace(')', ' ');
		
		str2 = str2.replace(',', ' ');
		str2 = str2.replace('(', ' ');
		str2 = str2.replace(')', ' ');
		
		testf1 = SymFunction.parse(str1);
		System.out.println(testf1.getDepth());
		System.out.println(testf1.toJavaCode());
		System.out.println(testf1.toInfix());
		System.out.println("-----");
		
		testf2 = SymFunction.parse(str2);
		System.out.println(testf2.getDepth());
		System.out.println(testf2.toJavaCode());
		System.out.println(testf2.toInfix());

		
		double VALUE = 0.8;
		double pu0 = VALUE, pu1 = VALUE, pu2 = VALUE, pu3 = VALUE, pu5 = VALUE;
		double qi0 = VALUE, qi1 = VALUE, qi2 = VALUE, qi3 = VALUE, qi4 = VALUE, qi5 = VALUE;
		HashMap<String, Double> par = new HashMap<String, Double>();
		
		for(int i=0; i<=5; i++) {
			par.put("pu"+i, VALUE);
			par.put("qi"+i, VALUE);
		}	
		
		Double res1 = (Math.exp(Math.atan(pu5))) - ((((pu1) * (qi5)) - (Math.exp(pu3))) - ((Math.atan((Math.atan(qi3)) - ((((pu1) * ((((pu1) * (qi5)) - ((pu1) - (pu1))) - ((Math.atan((Math.atan(qi3)) - (((qi5) - (Math.sin(((pu1) * ((((pu1) * (qi5)) - (Math.exp(pu3))) - ((Math.atan((Math.atan(qi3)) - ((((pu1) * ((((pu1) * (qi5)) - (Math.exp(pu3))) - ((Math.atan((Math.atan(qi3)) - ((((pu1) * (qi5)) - (Math.sin((pu1) - (pu1)))) - (Math.atan(qi3))))) - (qi5)))) - (Math.sin(Math.atan(pu5)))) - (pu1)))) - (qi5)))) - (Math.exp(qi2))))) - (pu1)))) - (qi5)))) - (Math.sin(Math.atan(pu5)))) - (pu1)))) - (qi5)));
		assertEquals(res1, testf1.eval(par), THRESHOLD);
		
		Double res2 = Math.exp(Math.atan(Math.exp((Math.exp((Math.atan(qi1)) + (pu1))) + (((-(Math.atan(Math.exp(((pu5) * (Math.atan(Math.exp((pu2) + (Math.atan(Math.exp(((pu1) + (Math.exp(Math.atan(((Math.exp(Math.atan(pu2))) + (Math.atan(((qi1) + (Math.atan(pu1))) + (Math.atan(Math.exp(pu1)))))) - ((((Math.exp(Math.atan(qi4))) + (qi0)) + (Math.atan(Math.exp((Math.exp(qi1)) + (Math.atan(qi1)))))) + (((pu1) + (Math.exp(Math.atan(pu2)))) + (qi1))))))) + (qi1)))))))) + (((-(Math.atan(((qi1) + (Math.atan(Math.exp(((pu5) * (qi1)) + (pu2))))) + (qi1)))) + (Math.atan((pu2) * (qi1)))) + ((Math.exp(qi3)) + (Math.atan(qi4)))))))) + (Math.atan(qi1))) + (Math.atan(pu0))))));
		assertEquals(res2, testf2.eval(par), THRESHOLD);
	}
}
