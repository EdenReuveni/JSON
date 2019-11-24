package myMath;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

import myMath.Monom;
/**
 * This class represents a Polynom with add, multiply functionality, it also should support the following:
 * 1. Riemann's Integral: https://en.wikipedia.org/wiki/Riemann_integral
 * 2. Finding a numerical value between two values (currently support root only f(x)=0).
 * 3. Derivative
 * 
 * @author Boaz
 *
 */
public class Polynom implements Polynom_able{

	/**
	 * Zero (empty Polynom)
	 */
	public Polynom() {
		this.Polynom=new ArrayList<Monom>();
	}
	/**
	 * init a Polynom from a String such as:
	 *  {"x", "3+1.4X^3-34x", "2x^2-4-1.2x-7.1", "3-3.4x+1+3.1x-1.2-3X^2-3.1"};
	 * @param s: is a string represents a Polynom
	 */
	public Polynom(String s) {
		String newLowerCase=s.toLowerCase();
		String strForMonom="";
		Polynom p=new Polynom();
		for (int i = 0; i < newLowerCase.length(); i++) {
			if(newLowerCase==null || newLowerCase== "" ||  !(Valid_chars(newLowerCase.charAt(i)))) {
				//if a char is invalid, throw an exception
				throw new RuntimeException("The char "+newLowerCase.charAt(i)+" is invalid. Please enter a valid Monom");
			}
			Monom monForNeg=new Monom (newLowerCase);
			if(monForNeg.equals(Monom.MINUS1)) {
				//if the given String equals to "-1"
				strForMonom="-1";
				p.add(monForNeg);
			}
			if(i==0 && newLowerCase.charAt(i)=='-')
				//if the first char of the string is '-' than it's valid, continue to next char
				continue;
			if(newLowerCase.charAt(i)!='-' && newLowerCase.charAt(i)!='+')
				//add every char you meet until you'll get to '-' or '+', which means you reached the next Monom
				strForMonom+=newLowerCase.charAt(i);
			if (newLowerCase.charAt(i)=='^')
				//if the current char is '^', continue to the next one to find the power
				continue;
		}
		if (strForMonom!="-1") {
			Monom m=new Monom(strForMonom);
			//make a new valid Monom and add it to the Polynom
			p.add(m);
		}
		Polynom=p.Polynom;
		Comparator<Monom> comp=new Monom_Comperator();
		Polynom.sort(comp);
	}
	/**
	 * This method return a simple function of the type y=f(x)
	 */
	@Override
	public double f(double x) {
		double ans=0;
		Iterator<Monom> runner=this.iteretor();
		while(runner.hasNext()) {
			Monom m=runner.next();
			ans=ans+m.f(x);
		}
		return ans;	
	}
	/**
	 * This method adds a given Polynom to another Polynom and sorts it by its powers
	 */
	@Override
	public void add(Polynom_able p1) {
		Iterator<Monom> runner=p1.iteretor();
		while (runner.hasNext()) {
			this.add(runner.next());
		}
		Comparator<Monom> comp=new Monom_Comperator();
		Polynom.sort(comp);
	}
	/**
	 * This method adds a given Monom to a Polynom and sorts it by its powers
	 */
	@Override
	public void add(Monom m1) {
		if(m1.get_coefficient()==0)
			return;
		Iterator<Monom> runner=this.iteretor();
		while (runner.hasNext())
		{
			Monom m=runner.next();
			if (m.get_power()==m1.get_power()) {
				m.add(m1);
				if(m.isZero()) {
					Polynom.remove(m);
				}
				return;
			}
		}
		Polynom.add(m1);
		Comparator<Monom> comp=new Monom_Comperator();
		Polynom.sort(comp);
	}

	/**
	 * This method subtracts a given Polynom from another Polynom and sorts it by its powers
	 */
	@Override
	public void substract(Polynom_able p1) {
		Polynom_able p=p1.copy();
		p.multiply(Monom.MINUS1);
		//multiply all the Polynom by -1 and add them together
		add(p);
		Comparator<Monom> comp=new Monom_Comperator();
		Polynom.sort(comp);

	}
	/**
	 * This method multiplies a given Polynom by another one and sorts it by its powers
	 */
	@Override
	public void multiply(Polynom_able p1) {
		Polynom tempPol=new Polynom();
		//create a zero Polynom
		Iterator<Monom> runner = p1.iteretor();
		while (runner.hasNext()) {
			Monom mForP1=runner.next();
			Polynom_able tempForCopy=new Polynom();
			tempForCopy=this.copy();
			//copy this Polynom and multiply it by each Monom
			tempForCopy.multiply(mForP1);
			tempPol.add(tempForCopy);
			//add the Monom to the Polynom we created
		}
		this.Polynom=tempPol.Polynom;
		Comparator<Monom> comp=new Monom_Comperator();
		this.Polynom.sort(comp);
	}
	/**
	 * This method checks if two Polynoms are logically equals
	 */
	@Override
	public boolean equals(Polynom_able p1) {
		Iterator<Monom>runner=this.iteretor();
		Iterator<Monom> runnerForP1 = p1.iteretor();
		while(runner.hasNext()) {
			if(!runnerForP1.hasNext())
				return false;
			if(!runner.next().equals(runnerForP1.next()))
				return false;
		}
		return true;
	}
	/**
	 * This method checks if a Polynom is the zero Polynom
	 */
	@Override
	public boolean isZero() {
		Iterator<Monom> runner = this.iteretor();
		while(runner.hasNext()) {
			Monom m=runner.next();
			if (!(m.isZero()))
				return false;
		}
		return true;
	}
	/**
	 * This method finds the root assuming (f(x0)*f(x1)<=0, it returns f(x2) such that:
	 *					(i) x0<=x2<=x2 & (ii) f(x2)<ep
	 */
	@Override
	public double root(double x0, double x1, double eps) {
		double mid=0;
		if ((f(x0)>0 && f(x1)>0) || (f(x0)<0 && f(x1)<0) || x0>x1 || eps<=0) {
			throw new RuntimeException("Values are not valid. The inequations f(x0)*f(x1)<=0, x0<=x1 and eps>0 needs to be true for the input values");
		}
		mid = (x0 + x1) / 2;
		//find the middle
		if (Math.abs(f(mid)) < eps)
			return mid;
		if (f(x0) == 0) 
			//if the value of x0 is, the function "cuts" the x-axis exactly there
			return x0;
		if (f(x1) == 0)	
			//if the value of x1 is, the function "cuts" the x-axis exactly there
			return x1;
		if (f(mid) < 0) 
			//like binary search: if the value is negative 
			//set the "start point" to be the middle
			x0 = mid;
		else if (f(mid) > 0) 
			//if the value is positive
			//set the "end point" to be the middle
			x1 = mid;
		return root(x0, x1, eps);
		//recursively find the root with the new values
	}
	/**
	 * This method creates a new Polynom by deep copying a given one
	 */
	@Override
	public Polynom_able copy() {
		Iterator<Monom> runner = this.iteretor();
		Polynom p=new Polynom();
		while (runner.hasNext()) {
			Monom m= new Monom (runner.next());	
			p.add(m);	
		}
		return p;
	}

	@Override
	/**
	 * This method returns the derivative of a Polynom
	 */
	public Polynom_able derivative() {
		Iterator<Monom>runner=this.iteretor();
		Polynom p=new Polynom();
		while(runner.hasNext()) {
			Monom m=runner.next().derivative();
			p.add(m);
		}
		return p;
	}

	@Override
	/**
	 * This method computes the area, using a given epsilon, between two given pointers 
	 */
	public double area(double x0, double x1, double eps) {
		double ans=0;
		double distance=0;
		if (eps <=0)
			return 0;
		for (double i = x0; i < x1; i+=eps) {
			distance=f(i);
			if (distance>0)
				ans=ans+distance*eps;
		}
		return ans;
	}

	public Iterator<Monom> iteretor() {
		return Polynom.iterator();
	}
	/**
	 * This method multiplies a Polynom by a given Monom
	 */
	@Override
	public void multiply(Monom m1) {
		Iterator<Monom> runner=this.iteretor();
		while(runner.hasNext()) {
			Monom m = runner.next();
			m.multipy(m1);
		}
	}
	/**
	 * This method prints a Polynom in its valid form
	 */
	public String toString() {
		String strForPol ="";
		Iterator <Monom> runner=this.iteretor();
		while(runner.hasNext()) {
			Monom m=runner.next();
			if(!m.isZero()) {
				//if it's not zero add all the Monoms togehter and sperate them with '+'
				strForPol=strForPol+"+"+m.toString();
			}
		}
		if(strForPol=="") 
			strForPol="0";
		if(strForPol.charAt(0)=='+')
			//if the first char is '+', the String can start from the second one
			strForPol=strForPol.substring(1);
		strForPol=strForPol.replace("+-", "-");
		//if there was a negative number, it is shown as "+-" now.
		//replace it with just '-'
		return strForPol;
	}
	private ArrayList<Monom> Polynom;

	private boolean Valid_chars(char c) {
		if((c!='x' && c!='^'&& c!='+' && c!='.' && c!='-')) {
			//if the char is different from any of the above
			if(c<48 || c>57)
				//and also is different from the numbers 0-9
				return false;
		}
		return true;
	}
}

