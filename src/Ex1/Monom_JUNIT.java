package Ex1;

import static org.junit.Assert.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Ex1.Monom;

class Monom_JUNIT {

	private Monom monomEx ; 
	private Monom monomAc ;

	@BeforeEach
	void buildMonoms() {
		this.monomEx = new Monom(4,2);
		this.monomAc = new Monom(4,2);
	}

	/**
	 * test Monom(Double,Int) constructor ;
	 */
	@Test
	void testMonomConstructor() {
		assertTrue("Checks the coefficients",monomEx.get_coefficient()==monomAc.get_coefficient());
		assertTrue("Checks the powers",monomEx.get_power()==monomAc.get_power());
	}
	/**
	 * test Monom(Monom) constructor Manual checking
	 */
	@Test
	void testMonomConstructorFromMonom(){
		Monom testMonom = new Monom(monomEx);
		assertTrue("Checks the coefficients",monomAc.get_coefficient() == testMonom.get_coefficient());
		assertTrue("Checks the powers",this.monomAc.get_power() == testMonom.get_power());
	}


	/**
	 * test Monom(String) constructor 
	 *  in the test , there is 2 inputs , valid monoms and invalid monoms inputs.
	 * the test check 2  strings one is represent a valid inputs , and the other invalid inputs.
	 * then split with the char ' ' , and check if the string its working and make a monom .
	 * otherwise its fail 
	 */

	@Test
	void testMonomString(){
		String[] monoms = {"--2", "-X","3.2x^2","0x^0"};
		Monom monoms_excepted [] = {new Monom(2,0), new Monom(-1,1),new Monom(-3.2,2),Monom.ZERO};
		String[] monoms_worng = {"-X^-1","-3.3.3x^2","0x^0.1"};
		for (int i = 0; i < monoms.length; i++) {
			if(monoms_excepted[i].get_coefficient() != (new Monom(monoms[i]).get_coefficient()))
				fail("The String Constructor doesn't work well,the coefficient value of the excepted Monom isn't as well.");
			else if(monoms_excepted[i].get_power() != (new Monom(monoms[i]).get_power()))
				fail("The String Constructor doesn't work well,the power value of the excepted Monom isn't as well.");
		}
		for (int i = 0; i < monoms_worng.length; i++) {
			try { // Test a wrong input
				monomEx = new Monom(monoms_worng[i]);
				fail("String Constructor did not throw exception when got invalid input");
			}

			catch (Exception e)
			{

			}
		}
	}



	/**
	 * testing Derivative() function 
	 */
	@Test
	void testDerivative() {
		double excepted_coefficient = (this.monomEx.get_coefficient()*this.monomEx.get_power());
		int excepted_power = (this.monomEx.get_power()-1);
		monomAc=this.monomAc.derivative();
		assertTrue("Checks the coefficient: ",this.monomAc.get_coefficient()==excepted_coefficient);
		assertTrue("Checks the coefficient: ",this.monomAc.get_power()==excepted_power);
	}

	/**
	 * testing F() function
	 */
	@Test
	void testF() {
		double x =5 ;
		double excepted_FValue = (this.monomAc.get_coefficient() * Math.pow(x, this.monomAc.get_power()));
		assertTrue("Checks the values of f: ", excepted_FValue==this.monomAc.f(x));
		/*if(excepted_FValue != this.monomAc.f(x))
			fail("The F function is not working the f value is not as the excepted_FValue");*/
	}
	/**
	 * testing add(Monom) Manual checking
	 */
	@Test
	void testAdd() {
		this.monomAc.add(monomAc);
		assertTrue("Checks the coefficients: ", (2*monomEx.get_coefficient())==monomAc.get_coefficient());
		assertTrue("Checks the powers: ", monomAc.get_power()==monomEx.get_power());
		/*	if(monomAc.get_coefficient() != 6)
			fail("The add function is not working well, the coefficient value not as the excepted value  ");
		if(monomAc.get_power() != 3 )
			fail("The add function is not working well, the power value not as the excepted value  ");*/
	}
	/**
	 * testing Multiply() function 
	 */
	@Test
	void testMultiply() {
		double excepted_coefficient = this.monomAc.get_coefficient() * 3 ;
		int excepted_power = this.monomAc.get_power() + 5 ;
		this.monomAc.multipy(new Monom(3,5));
		assertTrue("Checks the coefficients: ", monomAc.get_coefficient()==excepted_coefficient);
		assertTrue("Checks the powers: ", monomAc.get_power()==excepted_power);
		/*if(monomAc.get_coefficient() != excepted_coefficient)
			fail("The Multiply function is not working well, the coefficient value not as the excepted value  ");
		if(monomAc.get_power() != excepted_power )
			fail("The Multiply function is not working well, the power value not as the excepted value  ");
		 */
	}
	/**
	 * testing equals(Monom) function 
	 */
	@Test
	void testEquals() {
		boolean ans = this.monomAc.equals(this.monomEx);
		assertTrue("Checks the equals function: ",ans);
	}
	/**
	 * testing isZero function
	 */
	@Test
	void testIsZero() {
		this.monomAc = new Monom(0,3);
		boolean ans = this.monomAc.isZero();
		assertTrue("Checks isZero function: ",ans);
	}

	@Test
	void testGet_power() {
		if(this.monomAc.get_power() != 2 )
			fail("The get_power function isn't working");
	}

	@Test
	void testGet_coefficient() {
		assertTrue("Checks the get_coefficient function: ", this.monomAc.get_coefficient()==4);
	}
	/**
	 * testing the toString function with Monom(String) constructor
	 */
	@Test
	void testToString() {
		this.monomAc = new Monom(this.monomEx.toString()); 
		assertTrue("Checks the coefficients: ", monomAc.get_coefficient()==this.monomEx.get_coefficient());
		assertTrue("Checks the powers: ", monomAc.get_power()==this.monomEx.get_power());
		
	}
}
