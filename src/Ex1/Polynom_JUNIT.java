package Ex1;

import Ex1.Monom;
import Ex1.Polynom;
import Ex1.Polynom_able;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

class Polynom_JUNIT {

	private Polynom_able polynomEx ; 
	private Polynom_able polynomAc ; 
	private String str;

	@BeforeEach
	void setUp() throws Exception 
	{
		polynomEx = new Polynom();	
		polynomAc = new Polynom();
		str="jojo";
	}
	/**
	 * test Polynom()  default constructor ;
	 */
	@Test
	void testPolynomConstructor() {
		Iterator<Monom>it=polynomEx.iteretor();
		assertFalse("Checks that the polynom's arrayList is null:",it.hasNext());
		//if(!polynomAc.isZero()) fail("Polynom() constructor not working");
	}
	@Test
	void testPolynomString() {
		String [] polynoms = {"-5+ 1.7x+3.2x^2-3-1.5x^2","1+x+0.5X^2+0.5x^2+0","1.3x^3-44x+3x^2+4x^1","-1-1"};
		String [] excepted_polynoms= {"1.7000000000000002x^2+1.7x-8.0","1.0x^2+1.0x+1.0","1.3x^3+3.0x^2-40.0x","-2.0"};
		for (int i = 0; i < polynoms.length; i++) {
			try
			{
				polynomAc = new Polynom(polynoms[i]);
				assertEquals(polynomAc.toString(),excepted_polynoms[i]);
			}		
			catch (Exception e) 
			{
				fail("Didn't build a polynom from a valid string with constructor (String)");
			}
		}
		String[] inValidInput = {"5*x^-2 "," 9*x^1.5","1*Xx","8*X^0", "p","-3*x^i"}; // invalid input
		for (int i = 0; i < inValidInput.length; i++) {
			try 
			{
				polynomAc = new Polynom(inValidInput[i]);
				fail("String Constructor did not throw exception when got invalid input");
			}
			catch (Exception e) 
			{

			}
		}
	}
	/**
	 * testing F function.
	 */
	@Test
	void testF() {
		String[] monoms1 = {"2", "-x","-3.2x^2","4","-1.5x^2"};
		String[] monoms2 = {"5", "1.7x","3.2x^2","-3","-1.5x^2"};
		String[] monoms3= {"-0x^4","0x"};
		for(int i=0;i<monoms1.length;i++) 
			polynomAc.add(new Monom(monoms1[i]));
		assertTrue(polynomAc.f(2)== -14.8);
		polynomAc=new Polynom();
		for(int i=0;i<monoms2.length;i++) 
			polynomAc.add(new Monom(monoms2[i]));
		assertTrue(polynomAc.f(-2)==5.4); 
		polynomAc=new Polynom();
		for(int i=0;i<monoms3.length;i++) 
			polynomAc.add(new Monom(monoms3[i]));
		assertTrue(polynomAc.f(4)== 0); 
	}
	/**
	 * testing add(Polynom_able) 
	 */
	@Test
	void testAddPolynom_able() {
		polynomAc = new Polynom("x^3 + 2x^1 - 6");
		Polynom test =new Polynom("3x^1 + 4");
		polynomEx=new Polynom("x^3 + 5x^1-2");
		polynomAc.add(test);
		assertEquals(polynomAc,polynomEx);
	}
	/**
	 * testing add(Monom) 
	 */
	@Test
	void testAddMonom() {
		polynomAc = new Polynom("x^3 + 2x^1 - 6");
		Monom sub =new Monom("3x^2");
		polynomEx=new Polynom("x^3 + 3x^2+2x^1-6");	
		polynomAc.add(sub);
		if(!polynomAc.equals(polynomEx))
			fail("Add(Monom) not working well");

		polynomAc = new Polynom("-x^4 + 3x^1 - 4");
		sub=new Monom("3x^4");
		polynomEx=new Polynom("2x^4 +3x^1 - 4");
		polynomAc.add(sub);
		if(!polynomAc.equals(polynomEx))
			fail("Add(Monom) not working well");
	}
	/**
	 * testing Subtract(Polynom) 
	 */
	@Test
	void testSubstract() {
		polynomAc = new Polynom("x^3 + 2x^1 - 6");
		Polynom sub =new Polynom("3x^1 + 4");
		polynomEx=new Polynom("x^3 -x^1-10");
		polynomAc.substract(sub);
		assertEquals(polynomAc,polynomEx);

		polynomAc = new Polynom("-x^4 + 3x^1");
		sub=new Polynom("3x^4 - 2x^1");;
		polynomEx=new Polynom("-4x^4+5x^1");
		polynomAc.substract(sub);
		assertEquals(polynomAc,polynomEx);
	}
	/**
	 * testing Multiply(Polynom) 
	 */
	@Test
	void testMultiply() {	
		polynomAc = new Polynom("4x+0x-3x^5-1x+2");
		Polynom mul =new Polynom("6X^2 + 2x+1");
		polynomEx=new Polynom("-18x^7 -6x^6- 3x^5 +18x^3 + 18x^2+7x +2");	
		polynomAc.multiply(mul);
		if(!polynomAc.equals(polynomEx))
			fail(" Multiply(Polynom) is not working well");
		mul.multiply(mul);
		polynomEx=new Polynom("36x^4+24x^3+16x^2+4x+1");	
		if(!mul.equals(polynomEx))
			fail(" Multiply(Polynom) is not working well");
	}
	/**
	 * testing Equals(Polynom_able) 
	 */
	@Test
	void testEquals() {
		polynomAc=new Polynom("x^3 + 3x^2+2x^1-6");	
		polynomEx=new Polynom("x^3 + 3x^2+2x^1-6");	
		Monom monomForTest=new Monom(2,3);
		Polynom polynomForTest=new Polynom("2x^3");
		Polynom_JUNIT objectForTest=new Polynom_JUNIT();
		System.out.println(objectForTest.str);
	//	assertEquals("Checks if a Monom is equal to a Polynom: ", polynomForTest,monomForTest);
	//	assertFalse("Checks if an invalid Object is equal to Polynom: ",polynomForTest.equals(objectForTest));
		if(!polynomAc.equals(polynomEx))
			fail("Equals is not working well");
		polynomEx=new Polynom("x^3 + 3x^2+2x^1+6");	
		if(polynomAc.equals(polynomEx))
			fail("Equals is not working well");
	}
	/**
	 * testing Is_Zero() 
	 */
	@Test
	void testIsZero() {
		polynomAc.add(new Monom(0,2));
		assertTrue(polynomAc.isZero());
		polynomAc.add(new Monom(1,0));
		assertFalse(polynomAc.isZero());
		polynomAc.add(new Monom(1,1));
		assertFalse(polynomAc.isZero());
	}
	/**
	 * testing Root function.
	 * 
	 */
	@Test
	void testRoot() {
		String[] monoms = {"-1","1","x","0.5x^2", "0.5x^2"};
		for(int i=0;i<monoms.length;i++)
			polynomAc.add(new Monom(monoms[i]));
		double root = polynomAc.root(0,1,Monom.EPSILON);
		double RootNegative = polynomAc.root(-3, 0, 0.001);
		RootNegative = Math.abs(RootNegative);
		if(root!=0)
			fail(" Root function is not working well");
		polynomAc=new Polynom ("-x^5+3.1");
		root= polynomAc.root(-2, 2, Monom.EPSILON);
		if(root!=1.2539272457361221)// checked with a graph
			fail(" Root function is not working well");
	}
	/**
	 * testing copy() 
	 */
	@Test
	void testCopy() {
		polynomEx=new Polynom("x^3 + 3x^2+2x^1-6");	
		polynomAc = polynomEx.copy();
		assertEquals(polynomAc,polynomEx);
	}
	/**
	 * testing Derivative function
	 */
	@Test
	void testDerivative() {
		polynomAc = new Polynom("x^3 -3x^2+2x^1-6");	
		polynomAc=polynomAc.derivative();
		polynomEx=new Polynom("3x^2 -6x+2");
		if(!polynomAc.equals(polynomEx))
			fail("Derivative function is not working well");
	}
	/**
	 * testing Area function
	 */
	@Test
	void testArea() {
		polynomAc = new Polynom("-2+3X+x^6+4x");
		if(polynomAc.area(-2,0, Monom.EPSILON)!=8.735259807879196)//checked with a calculator
			fail("Area function is not working well");

	}
	/**
	 * testing Multiply(Monom) 
	 */
	@Test
	void testMultiplyMonom() {	
		polynomAc = new Polynom("4x+0x-3x^5-1x+2");
		Monom mul =new Monom("6X^2");
		polynomEx=new Polynom("-18x^7+18x^3+12x^2");	
		polynomAc.multiply(mul);
		if(!polynomAc.equals(polynomEx))
			fail(" Multiply(Polynom) is not working well");
	}
	/**
	 * testing toString 
	 */
	@Test
	void testToString() {
		String[] monoms= {"3x^3","4","4x^0"};
		for(int i=0;i<monoms.length;i++)
			polynomAc.add(new Monom(monoms[i]));
		polynomEx= new Polynom("3x^3+8");
		Polynom test=new Polynom(polynomAc.toString());
		assertEquals(test,polynomEx);
	}

}

