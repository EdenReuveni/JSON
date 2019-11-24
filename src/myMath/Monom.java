
package myMath;

import java.text.DecimalFormat;
import java.util.Comparator;


/**
 * This class represents a simple "Monom" of shape a*x^b, where a is a real number
 *  and b is an integer (summed a none negative), 
 * see: https://en.wikipedia.org/wiki/Monomial 
 * The class implements function and support simple operations as:
 *  construction, value at x, derivative, add and multiply. 
 * @author Boaz
 *
 */
public class Monom implements function{
	private DecimalFormat formatOfDisplay = new DecimalFormat("0.0");
	public static final Monom ZERO = new Monom(0,0);
	public static final Monom MINUS1 = new Monom(-1,0);
	public static final double EPSILON = 0.0000001;
	public static final Comparator<Monom> _Comp = new Monom_Comperator();
	public static Comparator<Monom> getComp() {return _Comp;}
	public Monom(double a, int b){
		this.set_coefficient(a);
		this.set_power(b);
	}
	public Monom(Monom ot) {
		this(ot.get_coefficient(), ot.get_power());
	}

	public double get_coefficient() {
		return this._coefficient;
	}
	public int get_power() {
		return this._power;
	}

	/** 
	 * This method returns the derivative Monom of this.
	 * @return the derivative of it
	 */
	public Monom derivative() {
		if(this.get_power()==0) {return getNewZeroMonom();}
		return new Monom(this.get_coefficient()*this.get_power(), this.get_power()-1);
	}
	/**
	 * This method returns the function of x in power of p
	 */
	public double f(double x) {
		double ans=0;
		double p = this.get_power();
		ans = this.get_coefficient()*Math.pow(x, p);
		return ans;
	} 
	/**
	 * This method return true if the coefficient of a Monom is zero
	 * and false otherwise
	 * @return boolean value to represent if the Monom is zero
	 */
	public boolean isZero() {
		return this.get_coefficient() == 0;
	}
	/**
	 * This method turns a string into a valid Monom 
	 * @param s is the given string
	 */
	public Monom(String s) {
		String strForCeoff="";
		String strForPower="";
		String newLowerCase=s.toLowerCase();
		for (int i = 0; i < newLowerCase.length(); i++) {
			if(newLowerCase==null || newLowerCase== "" ||  !(Valid_chars(newLowerCase.charAt(i)))) {
				//if a char is invalid, throw an exception
				throw new RuntimeException("ERR The char "+newLowerCase.charAt(i)+
						" is invalid. Please enter a valid Monom");
			}	
		}
		int helper=0;
		//helper will eventually hold the last char of the coefficient
		for (int i = 0; i < newLowerCase.length(); i++) {
			if (newLowerCase.charAt(i)=='^') {
				helper++;
				break;
			}
			if (newLowerCase.charAt(i)!='x'&&newLowerCase.charAt(i)!='^') {
				//finds all of the coefficient, the chars before 'x'
				strForCeoff+=newLowerCase.charAt(i);
				helper=i;
			}
			else if ((i+1<newLowerCase.length())&&(newLowerCase.charAt(i+1)=='^')) {
				//if the char is 'x' and the next one is '^'
				helper++;
				//set helper to be the number after the power sign
			}
			if ((i+1<newLowerCase.length())&&(newLowerCase.charAt(i)=='x')&&
					(newLowerCase.charAt(i+1)!='^')) {
				//if the char is 'x' and there another one after it, that is different from '^'
				//the Monom isn't in the valid form
				throw new RuntimeException("ERR the Monom valid shape is ax^b OR aX^b");					
			}
			if ((newLowerCase.charAt(i)=='-')&&((i+1<newLowerCase.length())&&
					(newLowerCase.charAt(i+1)=='x')))
				//if there is no coefficient before 'x', only "-x"
				//the coefficient is -1
				strForCeoff="-1";
			if (i+1>=newLowerCase.length()) {
				//if i is the last index of the string
				if (newLowerCase.charAt(i)!='x')
					//if the last index isn't 'x'
					//the power is 0
					strForPower="0";
				else {
					//if the last char is 'x'
					//the power is 1
					strForPower="1";
					set_power(1);
				}
			}
			if (strForCeoff=="") {
				//if there is no coefficient and there is "x^" char
				strForCeoff="1";
				set_coefficient(1.0);
			}
		}
		if (strForPower!="0") {
			for (int i = helper+1; i < newLowerCase.length()&&newLowerCase.charAt(i)!='x'&&
					newLowerCase.charAt(i)!='-'&&newLowerCase.charAt(i)!='+'; i++) {
				//goes through the chars after 'x' and '^', and add all the power components 
				//gets into the loop only if all the chars after '^' are numbers and not signs
				strForPower+=newLowerCase.charAt(i);
			}
		}
		for (int i = helper+1; i < newLowerCase.length()&&newLowerCase.charAt(i)!='x'; i++) {
			if (i+1<newLowerCase.length()) {
				if (newLowerCase.charAt(i)=='-') {
					//if the first char after '^' is '-', the power is negative
					strForPower='-'+strForPower+newLowerCase.charAt(i+1);
				}
				if (newLowerCase.charAt(i)=='+')
					//if the first char after '^' is '+', it's a valid power.
					//add the char after '+'
					strForPower+=newLowerCase.charAt(i+1);
			}
		}
		if (strForPower=="")
			//if no power found by this point, there is no '^' and the Monom isn't valid
			throw new RuntimeException("ERR the Monom valid shape is ax^b OR aX^b");					
		set_coefficient(Double.parseDouble(strForCeoff));
		//convert the string for the coefficient to it's numeric value 
		set_power(Integer.parseInt(strForPower));
		//convert the string for the power to it's numeric value 
	}
	/**
	 * This method add up two Monoms with the same power
	 * @param m is a given Monom
	 */
	public void add(Monom m) {
		if (this.get_power()==m.get_power())
			this.set_coefficient(this._coefficient=this._coefficient+m._coefficient);
	}

	/**
	 * This method multiplies two Monoms according math rules 
	 * @param d is the given Monom
	 */
	public void multipy(Monom d) {		
		this.set_coefficient(this._coefficient*d.get_coefficient());
		this.set_power(this._power+d.get_power());
	}
	/**
	 * This method determines if two Monoms are logically equals 
	 * @param m is a given Monom
	 * @return the boolean value of the question
	 */
	public boolean equals(Monom m) {
		if (m.isZero()&&this.isZero())
		{
			m=getNewZeroMonom();
			//if both are zero, set both powers to zero
			return true;
		}
	if ((Math.abs(this.get_coefficient()-m.get_coefficient())<=Monom.EPSILON) &&
			(this.get_power()==m.get_power())) 
			return true;
		return(this.get_coefficient()==m.get_coefficient()&&this.get_power()==m.get_power());
	}

	/**
	 * This method prints to user a Monom in its valid form
	 */
	public String toString() {
		boolean negative=false;
		if (this.isZero())
			return "0";
		if (_coefficient == '-') {
			negative=true;    		
		}
		if(_power == 0) {
			if (negative) {
				//if there is no 'x' and just '-' as the coefficient
				//set it to -1
				set_coefficient(-1);
				return formatOfDisplay.format(_coefficient); 
				//return the coefficient in the right format
			}
			else 
				//if there is no 'x' and the coefficient isn't negative
				return formatOfDisplay.format(_coefficient);
		}
		else if(_power == 1) {
			if (negative) {
				//if the power is 1 and the coefficient is '-'
				//set it to -1 and add 'x'
				set_coefficient(-1);
				return formatOfDisplay.format(_coefficient)+'x';
			}
			else 
				//if the power is 1 and the coefficient isn't '-'
				//add to the coefficient 'x'
				return formatOfDisplay.format(_coefficient) + "x";
		}
		else
			//if the power is any number different from 0,1,-1 
			//add all to the format of a Monom
			return formatOfDisplay.format(_coefficient) +"x^" + _power;
	}


	//****************** Private Methods and Data *****************

	/**
	 * This method determines if the chars of a given string are valid for a Monom 
	 * @param c is a char in a given string
	 * @return the boolean value of the question
	 */
	private boolean Valid_chars(char c) {
		if((c!='x' && c!='^'&& c!='+' && c!='.' && c!='-')) {
			//if the char is different from any of the above
			if(c<'0' || c>'9')
				//and also is different from the numbers 0-9
				return false;
		}
		return true;
	}
	private void set_coefficient(double a){
		this._coefficient = a;
	}
	private void set_power(int p) {
		if(p<0) {
			throw new RuntimeException("ERR the power of Monom should not be negative, got: "+p);
		}
		this._power = p;
	}
	private static Monom getNewZeroMonom() {return new Monom(ZERO);}
	private double _coefficient; 
	private int _power;
}
