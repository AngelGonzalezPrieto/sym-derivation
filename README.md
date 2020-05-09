# sym-derivation

Library for symbolic computation in Java.

It allows symbolic exact derivation. It also provides utilities for parsing from prefix form as well as translation into infix form and equivalent Java code.

## Installation

In order to add sym-derivation to your project, you can do it via Maven or directly with a `jar` package.

#### Using Maven

Copy the following lines to your `pom.xml` file.

```xml
<dependency>
    <groupId>es.upm.etsisi</groupId>
    <artifactId>sym-derivation</artifactId>
    <version>1.0.0</version>
</dependency>
```

#### As a `jar` package

If you prefer to use the library without a dependency management tool, you must add the `jar` packaged version of sym-derivation to your project's classpath. For example, if you are using IntelliJ IDEA, copy the file to your project's directory, make right click on the `jar` file and select `Add as Library`.

You can find the `jar` packaged version of sym-derivation into the release section of GitHub.

You can also package your own `jar` file . To do that, clone the repository using 

	git clone https://github.com/AngelGonzalezPrieto/sym-derivation.git

and package it with `mvn package`.

## Getting started

### Creating functions

The hands-on way of using sym-derivation is to directly create the functions using the implemented classes. Suppose that we want to create the symbolic function 
<center>
<img src="https://render.githubusercontent.com/render/math?math=f(x) = \sin(x).">
</center>

For that purpose, we need to create two elements: the variable `x` and the cosine function `cos`. All the classes in sym-derivation inherit from the abstract function `SymFunction` so we can do

    SymFunction var = new SymVar("x");
    SymFunction f = new SymSin(var); 

At this point, we can perform the usual math operations with `f`. For instance, we can evaluate it in <img src="https://render.githubusercontent.com/render/math?math=x=3"> with a `HashMap` mapping each variable to its value.

    HashMap<String, Double> param = new HashMap<String, Double>();
    param.put("x", 3.0);
    
    Double result = f.eval(param);
    System.out.println(result);
    >> 0.1411200080598672

We can recover the function as a string in infix notation using the `toInfix` method of `SymFunction`

	String infix = f.toInfix();
	System.out.println(infix);
	>> sin(x)
	
Furthermore, the library allows to automatically generate the Java code that would implement this function. It is provided by the function `toJavaCode`.

	String javaCode = f.toJavaCode();
	System.out.println(javaCode);
	>> Math.sin(x)

We can also derive <img src="https://render.githubusercontent.com/render/math?math=f"> to get its derivative <img src="https://render.githubusercontent.com/render/math?math=f'(x) = \cos(x)">

    SymFunction fPrime = f.diff() 

The derivative is again a fully functional function. In particular, we can evaluate it

    result = fPrime.eval(param)
    System.out.println(result);
    >> -0.9899924966004454    

### Composition of functions

The previous example can be made more involved by considering composition of functions. Suppose that we want to create the symbolic function
<center>
<img src="https://render.githubusercontent.com/render/math?math=f(x) = e^{-\cos(x)}.">
</center>
The functions in sym-derivation are represented with a tree structure that models the function as composition of elementary functions. In our example, we have that <img src="https://render.githubusercontent.com/render/math?math=f"> is the composition of four unary functions (from inner to outer):
* The variable `x`.
* The cosine function, `cos`.
* The unary minus function, `-`.
* The exponential function, `exp`.
    
Hence, we have to replicate this composition tree by chaining object declaration. The first step is to create the variable `x`. 

    SymFunction fun1 = new SymVar("x");

Using this variable, we create the function `cos(x)` as composition of `fun1` with `cos`.

    SymFunction fun2 = new SymCos(fun1);
	
Afterwards, we compose `cos(x)` with the unary minus function to create `-cos(x)`

    SymFunction fun3 = new SymUnaryMinus(fun2);
	
Finally, we compose `-cos(x)` with the exponential function to create <img src="https://render.githubusercontent.com/render/math?math=f">

    SymFunction f = new SymExp(fun3);

Now `f` contains the desired function. As in the previous example we can perform any operation. We can evaluate it

    HashMap<String, Double> param = new HashMap<String, Double>();
    param.put("x", 2.1);
    
    Double result = f.eval(param);
    System.out.println(result);
    >> 1.6567305376319947

We also can get the derivative function

    SymFunction fPrime = f.diff("x");
    
or we can render the Java code implementing the function
	
	String javaCode = f.toJavaCode();	
	System.out.println(javaCode);
	>> Math.exp(-(Math.cos(x)))
    
### Higher arity

The library also supports functions with higher arity and functions of several variables. Suppose that we want to create the function
<center>
<img src="https://render.githubusercontent.com/render/math?math=f(x,y) = e^{y - \cos(x)}.">
</center>

We can do it by writing it as composition of elementary functions as follows.

		SymFunction varx = new SymVar("x");
		SymFunction cosx = new SymCos(varx);
		SymFunction vary = new SymVar("y");
		SymFunction yMinusCosx = new SymSubs(vary, cosx);
		SymFunction f = new SymExp(yMinusCosx);

Observe that the `SymSubs` function is binary so its constructor expects two symbolic functions for the two arguments. 
Now `f` is a symbolic function of two variables, `x` and `y`. Hence, in order to evaluate it, we need to assign values to each of the variables.

		HashMap<String, Double> param = new HashMap<String, Double>();
		param.put("x", 2.1);
		param.put("y", -0.8);
		
		Double result = f.eval(param);
		System.out.println(result);
		>> 0.7444170162955518
		
We can also compute the partial derivates of `f` with respect to any of its variables. For getting the partial derivative with respect to `x`

		SymFunction fPrimex = f.diff("x");
		
		result = fPrimex.eval(param);
		System.out.println(result);
		>> 0.6425877411591275

Analogously, the partial derivative with respect to `y` can be computed as

		SymFunction fPrimey = f.diff("y");
		
		result = fPrimey.eval(param);
		System.out.println(result);
		>> 0.7444170162955518
		
Constant functions are also allowed. In this way, if we want to create the function 
<center>
<img src="https://render.githubusercontent.com/render/math?math=f(x) = 3x-1,">
</center>
we can use the `SymConstant` class as follows.

		SymFunction varx = new SymVar("x");
		SymFunction const3 = new SymConstant(3.0);
		SymFunction mult3x = new SymProd(const3, varx);
		SymFunction const1 = new SymConstant(1.0);
		SymFunction f = new SymSubs(mult3x, const1);

The constants `0` and `1` can also be created with the classes `SymZero` and `SymOne` respectively. In this way, the previous code is equivalent to the following.

		SymFunction varx = new SymVar("x");
		SymFunction const3 = new SymConstant(3.0);
		SymFunction mult3x = new SymProd(const3, varx);
		SymFunction const1 = new SymOne();
		SymFunction f = new SymSubs(mult3x, const1);

### Parsing functions

The easiest way of creating symbolic functions in sym-derivation is by parsing it from a string literal. Sym-derivation works with prefix notation. Supose that we want to create the function
<center>
<img src="https://render.githubusercontent.com/render/math?math=f(x, y) = \cos(x) - \exp(y),">
</center>

In prefix notation, it is given as
<center>
<img src="https://render.githubusercontent.com/render/math?math=f(x, y) = - \cos x \exp y">
</center>

We can parse an string with a function literal using the static method `parse` of the class SymFunction. Hence, apart from the previous methods for creating the function using the implemented classes, it can also be created with the following code.

    String literal = "- cos x exp y";
    SymFunction f = SymFunction.parse(literal);

More complicated functions can be created using this philosophy. For instance, suppose that we want to create the function
<center>
<img src="https://render.githubusercontent.com/render/math?math=f(x, y) = \cos(x) - 3\log(y^2).">
</center>

The constants in sym-function are declared with the token `const` so the previous example can be parsed as 

	String literal = "- cos x * const 3 log pow y const 2";
	SymFunction f = SymFunction.parse(literal);

Any token that does not correspond to a token of a symbolic function or is not preceded with `const` is interpreted as a name of variable. In this way, the following code

	String literal = "- cos helloworld * const 3 x";
	SymFunction f = SymFunction.parse(literal);

corresponds to the function

<center>
<img src="https://render.githubusercontent.com/render/math?math=f(x, \textrm{helloworld}) = \cos(\textrm{helloworld}) - 3x">
</center>


## Implemented functions

Currently, sym-derivation implements the following functions, whose arity and string literal (token) is also indicated.

| Function        | Class name           |  Arity  | Tokenn
| ----------- |:-------------:| -----:| -----:|
| Variable      | `SymVar` | - | `<name_var>`
| Constant      | `SymConstant` | - | `const <value_const>`
| Constant Zero      | `SymZero` | - | `Zero`
| Constant  One  | `SymOne` | - | `One`
| Sine     | `SymSin` | 1 | `sin <arg>`
| Cosine     | `SymCos` | 1 | `cos <arg>`
| Arc tangent     | `SymAtan` | 1 | `atan <arg>`
| Exponential     | `SymExp` | 1 | `exp <arg>`
| Logarithm (natural)     | `SymLog` | 1 | `log <arg>`
| Sum     | `SymSum` | 2 | `+ <arg1> <arg2>`
| Substraction     | `SymSubs` | 2 | `- <arg1> <arg2>`
| Unary minus  | `SymUnaryMinus` | 1 | `-- <arg>`
| Product     | `SymProd` | 2 | `* <arg1> <arg2>`
| Substraction     | `SymSubs` | 2 | `- <arg1> <arg2>`
| Multiplicative inverse     | `SymInv` | 1 | `inv <arg>`
| Rise to power  | `SymPow` | 2 | `pow <base> <exponent>`
| Rise to numeric power      | `SymPowNumeric` | 2 | `pown <base> <value_exponent>`
