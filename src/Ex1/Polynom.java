package Ex1;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;


/**
 * This class represents a Polynom with add, multiply functionality, it also should support the following:
 * 1. Riemann's Integral: https://en.wikipedia.org/wiki/Riemann_integral
 * 2. Finding a numerical value between two values (currently support root only f(x)=0).
 * 3. Derivative
 * 
 * @author Noa Aizer
 *
 */
public class Polynom implements Polynom_able{
	private ArrayList<Monom>Pol;
	/**
	 * Zero (empty polynom)
	 */
	public Polynom() {
		this.Pol=new ArrayList<Monom>();
		;
	}
	/**
	 * init a Polynom from a String such as:
	 *  {"x", "3+1.4X^3-34x",'-x+3+5X'};
	 * @param s: is a string represents a Polynom
	 * @throws RuntimeException if the s is illegal.
	 */
	public Polynom(String s) {
		this.Pol=new ArrayList<Monom>();
		s = s.replaceAll("X", "x");
		s = s.replace(" ", "");
		String temp=""; // each Monom saved in the temporary string
		try {
			while(s.length()!=0) {
				if(s.contains("-")||s.contains("+")&&(!s.contains("+-")&&!s.contains("-+"))) 
				{
					if(s.charAt(0)=='-')
					{
						temp=temp+s.charAt(0);
						s=s.substring(1);
					}
					if(s.charAt(0)=='+')//skip the '+' 
						s=s.substring(1);
					while(s.length()!=0&&s.charAt(0)!='-'&&s.charAt(0)!='+')// create a string represnt a Monom
					{
						temp=temp+s.charAt(0);
						s=s.substring(1);
					}
					add(new Monom(temp));
					temp="";// clear the temporary string
				}	
				else // if there is only one Monom in the string/ is the last one.
				{
					Pol.add(new Monom(s));
					s=s.substring(1);
				}
			}
		}
		catch(Exception e)
		{
			throw new RuntimeException("ERROR: The string is illegal");

		}
	}
	/**This function calculates a simple function of type y=f(x)
	 * @return the value of f(x)
	 */
	public double f(double x) {
		// TODO Auto-generated method stub
		double ans=0;
		Iterator <Monom> it= this.iteretor();
		while(it.hasNext())
			ans+=it.next().f(x);
		return ans;
	}
	/**
	 * Add p1 to this Polynom
	 * @param p1
	 */
	public void add(Polynom_able p1) {
		// TODO Auto-generated method stub
		Iterator<Monom> it =p1.iteretor();
		while (it.hasNext()) 
			this.add(it.next());
		Comparator<Monom> cmpByPower = new Monom_Comperator();
		Pol.sort(cmpByPower);
	}
	/**
	 * Add m1 to this Polynom
	 * @param m1 Monom
	 */
	@Override
	public void add(Monom m1) {
		// TODO Auto-generated method stub
		boolean flag=false;// if we already add the Monom
		Iterator <Monom> it= this.iteretor();
		while(it.hasNext()&&!flag)
		{
			Monom m=it.next();
			if(m.get_power()==m1.get_power()) 
			{
				m.add(m1);
				flag=true;
			}
		}
		if(!flag)// if there is no Monom with the same power as m1.
		{
			if(!m1.equals(Monom.ZERO))
			Pol.add(m1);
			Comparator<Monom> cmpByPower = new Monom_Comperator();
			Pol.sort(cmpByPower);
		}
	}
	/**
	 * Subtract p1 from this Polynom
	 * @param p1
	 */
	@Override
	public void substract(Polynom_able p1) {
		// TODO Auto-generated method stub
		Polynom_able p2 = p1.copy();
		p2.multiply(Monom.MINUS1);
		add(p2);

	}
	/**
	 * Multiply this Polynom by p1
	 * @param p1
	 */
	@Override
	public void multiply(Polynom_able p1) {
		// TODO Auto-generated method stub
		Polynom temp_pol=new Polynom();
		Monom m;
		Iterator <Monom> it= this.iteretor();
		while(it.hasNext()) 
		{
			m=it.next();
			Iterator <Monom> it_p1= p1.iteretor();
			while(it_p1.hasNext()) 
			{
				Monom temp=new Monom(it_p1.next());
				temp.multipy(m);
				temp_pol.add(temp);
			}
		}
		this.Pol=temp_pol.Pol;
		this.Pol.sort(new Monom_Comperator());
	}
	/**
	 * Test if this Polynom is logically equals to p1.
	 * @param p1
	 * @return true if this polynom represents the same function as p1
	 */
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(obj instanceof Monom){
			if(obj.equals(this.Pol.get(0)))
				return true;
		}
		if(obj instanceof Polynom) {
			Polynom p1 = (Polynom) obj;
			Iterator<Monom>it=this.iteretor();
			Iterator<Monom> it_p1 = p1.iteretor();
			while(it.hasNext()) 
			{
				if(!it_p1.hasNext())
					return false;
				if(!it.next().equals(it_p1.next()))
					return false;
			}
			return true;
		}
		return false;
	}
	

	/**
	 * Test if this is the Zero Polynom
	 * @return
	 */
	public boolean isZero() {
		Iterator <Monom> it= this.iteretor();
		while(it.hasNext()) 
		{
			Monom m=it.next();
			if(!m.isZero()) return false;	
		}
		return true;
	}
	/**
	 * Compute a value x' (x0<=x'<=x1) for with |f(x')| < eps
	 * assuming (f(x0)*f(x1)<=0, else should throws runtimeException 
	 * computes f(x') such that:
	 * 	(i) x0<=x'<=x1 && 
	 * 	(ii) |f(x')|<eps
	 * @param x0 starting point
	 * @param x1 end point
	 * @param eps>0 (positive) representing the epsilon range the solution should be within.
	 * @return an approximated value (root) for this (cont.) function 
	 */
	@Override
	public double root(double x0, double x1, double eps) {
		// TODO Auto-generated method stub
		if(x1<=x0)throw new RuntimeException("x1 should be higher than x0");
		if(f(x0)*f(x1)>0)throw new RuntimeException("Got 2 points above / below X axis");
		double mid=(x0+x1)/2;
		if(Math.abs(f(mid))<eps) return mid;// found the root
		if(Math.abs(f(x0))<eps)return x0;
		if(Math.abs(f(x1))<eps)return x1;
		if(f(x0)<f(x1)) // This is an increasing function
		{
			if (f(mid) < 0) {// the root can be found in the smaller half
				x0 = mid;
			} else if (f(mid) > 0) {// the root can be found in the higher half
				x1 = mid;
			}
		}
		else {// This is a declining function
			if (f(mid) > 0) {
				x0 = mid;
			} 
			else if (f(mid) < 0) {
				x1 = mid;
			}
		}
		return root(x0,x1,eps);
	}

	/**
	 * create a deep copy of this Polynom
	 * @return 
	 */
	@Override
	public Polynom_able copy() {
		// TODO Auto-generated method stub
		Polynom new_pol= new Polynom();
		Iterator <Monom> it= this.iteretor();
		while(it.hasNext()) 
			new_pol.add(new Monom(it.next()));	
		return new_pol;
	}
	/**
	 * Compute a new Polynom which is the derivative of this Polynom
	 * @return
	 */
	@Override
	public Polynom_able derivative() {
		// TODO Auto-generated method stub
		Polynom dev_pol= new Polynom();
		Iterator <Monom> it= this.iteretor();
		while(it.hasNext()) 
		{
			Monom m=it.next();
			if(m.get_power()!=0)
				dev_pol.add(m.derivative());	
		}
		return dev_pol;
	}

	/**
	 * Compute a Riman's integral from x0 to x1 in eps steps. 
	 * @param x0 starting pooint
	 * @param x1 end point
	 * @param eps positive step value
	 * @return the approximated area above X-axis below this function bounded in the range of [x0,x1]
	 */
	public double area(double x0, double x1, double eps) {
		// TODO Auto-generated method stub
		if(eps<=0)return 0;
		double sum = 0.0;
		for(double i=x0;i<x1;i+=eps)
		{
			if(f(i)>0)// the distance should be above x axis
				sum+=f(i)*eps;
		}

		return sum;
	}

	/**
	 * @return an Iterator (of Monoms) over this Polynom
	 * @return
	 */
	@Override
	public Iterator<Monom> iteretor() {
		return Pol.iterator();
		// TODO Auto-generated method stub
	}
	/**
	 * Multiply this Polynom by Monom m1
	 * @param m1
	 */
	@Override
	public void multiply(Monom m1) {
		// TODO Auto-generated method stub
		Iterator <Monom> it= this.iteretor();
		while(it.hasNext()) 
			it.next().multipy(m1);
	}
	/**
	 * write the Polynom as a string.
	 * @return A string representing the Polynom.
	 */
	public String toString() {
		String ans = "";
		if(isZero())return "0";
		int count=0;// count how many Monoms we already have in the string
		Iterator <Monom> it= this.iteretor();
		while(it.hasNext()) 
		{
			String m_str=it.next().toString();
			if(count>=1&&m_str.charAt(0)!='-'&&!m_str.equals("0")&&!ans.isEmpty())
				ans+='+';// will not add + if it's the first monom in the string/ the monom is 0.
			if(!m_str.equals("0"))
				ans+=m_str;
			count++;

		}
		return ans;
	}
	@Override
	public function initFromString(String s) {
		// TODO Auto-generated method stub
		return null;
	}

}
