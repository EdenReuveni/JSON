package Ex1;
import java.util.Comparator;


/**
 * This class represents a simple "Monom" of shape ax^b, where a is a real number and a is an integer (summed a none negative), 
 * see: https://en.wikipedia.org/wiki/Monomial 
 * The class implements function and support simple operations as: construction, value at x, derivative, add and multiply. 
 * @author Noa Aizer
 *
 */
public class Monom implements function{
	public static final Monom ZERO = new Monom(0,0);
	public static final Monom MINUS1 = new Monom(-1,0);
	public static final double EPSILON = 0.0000001;
	public static final Comparator<Monom> _Comp = new Monom_Comperator();
	public static Comparator<Monom> getComp() {return _Comp;}
	/**
	 * default constructor
	 * @param a represents the coefficient
	 * @param b represents the power
	 */
	public Monom(double a, int b){
		this.set_coefficient(a);
		this.set_power(b);
	}
	/**
	 * {copy constructor
	 * @param ot represents the Monon we wnat to copy
	 */
	public Monom(Monom ot) {
		this(ot.get_coefficient(), ot.get_power());
	}
	/**
	 * coefficient getter
	 * @return the coefficient of the Monom
	 */
	public double get_coefficient() {
		return this._coefficient;
	}
	/**
	 * power getter
	 * @return the power of the Monom
	 */
	public int get_power() {
		return this._power;
	}
	/** 
	 * this method calculates the derivative Monom.
	 * @return the Monom’s derivative.
	 */
	public Monom derivative() {
		if(this.get_power()==0) {return getNewZeroMonom();}
		return new Monom(this.get_coefficient()*this.get_power(), this.get_power()-1);
	}
	/**This function calculates a simple function of type y=f(x)
	 * @return the value of f(x)
	 */
	public double f(double x) {
		double p = this.get_power();
		return  this.get_coefficient()*Math.pow(x, p);

	} 
	/**
	 * Test if this is the Zero Monom
	 * @return true/false if the Monom equals to zero
	 */
	public boolean isZero() {return this.get_coefficient() == 0;}
	// ***************** add your code below **********************
	/**
	 * Init a Monom from a String such as: "2x" , "3x^2","1".
	 * @param s: a string represents a Monom
	 * @throws RuntimeException if s is illegal.
	 */
	public Monom(String s) {
		s = s.replaceAll("X", "x");
		s = s.replace(" ", "");
		s=s.replace("+-", "-");
		double coeff=1;//the default coefficient
		int pow=0;// the default power
		String temp="";
		try
		{
			if(s.contains("x")&&!s.contains("^")&&s.length()-1>s.indexOf("x"))
				throw new RuntimeException("ERROR: The string is illegal");
			if(s.charAt(0)=='-')//for negative numbers set coeff to be negative.
			{
				coeff=-1;
				s=s.substring(1);
			}
			if(s.contains(""+'.')) //a double number
			{
				while(s.length()!=0 && s.charAt(0)!='x')// collect the double coefficient.
				{
					temp+=s.charAt(0);
					s=s.substring(1);
				}
				coeff*=Double.parseDouble(temp);
				temp="";
			}
			if(s.contains(""+'x'))// an int coefficient larger than 9.
			{
				while(s.charAt(0)!='x')
				{
					temp+=s.charAt(0);
					s=s.substring(1);
				}
				if(temp.length()!=0)// skip the x in the string
					coeff*=Integer.parseInt(temp);
				pow=1;
				s=s.substring(1);
				temp="";
			}
			if(s.length()!=0 && s.charAt(0)=='^')  //if has a power
			{
				s=s.substring(1);
				while(s.length()!=0)
				{
					temp+=s.charAt(0);
					s=s.substring(1);
				}
				pow=Integer.parseInt(temp);
				temp="";
			}
			if(s.length()==0) // the string is empty OR finish to pass all the string
			{
				this.set_coefficient(coeff);
				this.set_power(pow);
			}
			else //if the monom is only an int coefficient (without x) 
			{
				while(s.length()!=0)
				{
					temp+=s.charAt(0);
					s=s.substring(1);
				}
				coeff*=Integer.parseInt(temp);
			}
			// create the Monom:
			this.set_coefficient(coeff);
			this.set_power(pow);

		}
		catch(Exception e)
		{
			throw new RuntimeException("ERROR: The string is illegal");
		}

	}

	/**
	 * Add m to my monom
	 * @param m represents the monom we want to add.
	 * @throws RuntimeException if the power of this monom isn't the same as the power of m.
	 */
	public void add(Monom m) {
		if(this._power==m.get_power())
			this._coefficient+=m.get_coefficient();
		else if(m.get_coefficient()==0) return;
		else throw new RuntimeException("The power must be the same as the original monom");
	}
	/**
	 * Multiply d monom with this monom
	 * @param d represents the monom we multiply with this monom
	 */
	public void multipy(Monom d) {
		this._coefficient*=d.get_coefficient();
		this._power+=d.get_power();
	}
	/**
	 * write the Monom as a string.
	 * @return A string representing the Monom
	 */
	public String toString() {
		String ans = "";
		if(isZero())ans="0";
		else if(get_power()==0) ans=Double.toString(get_coefficient());
		else if(get_power()==1) ans=Double.toString(get_coefficient())+'x';
		else ans=get_coefficient()+"x^"+get_power();
		return ans;
	}
	// you may (always) add other methods.

	//****************** Private Methods and Data *****************

	/**
	 * Checks the logically equality of this monom and m.
	 *A special case: both 2 coefficients are equal to 0 then the monoms are the same no matter what the power is.
	 * @param m the Monom we compare with
	 * @return true- if the monoms are equal , false- the monoms are different.
	 */
	public boolean equals(Monom m) {
		if((Math.abs(m.get_coefficient()-this.get_coefficient()))<=EPSILON&&m.get_power()==this.get_power())return true;
		if(m.get_coefficient()==0&&this.get_coefficient()==0)return true;
		return false;
	}
	/**
	 * coefficient setter
	 * @param a represents the value we want to set at the coefficient
	 */
	private void set_coefficient(double a){
		this._coefficient = a;
	}
	/**
	 * power setter
	 * @param p represents the value we want to set at the power
	 */
	private void set_power(int p) {
		if(p<0) {throw new RuntimeException("ERR the power of Monom should not be negative, got: "+p);}
		this._power = p;
	}
	private static Monom getNewZeroMonom() {return new Monom(ZERO);}// create a zero Monom
	private double _coefficient; 
	private int _power;

	public function initFromString(String s) {
		return null;
	}
	public function copy() {
		return null;
	}
	public boolean equals(Object obj) {
		if(obj instanceof Monom){
			return this.equals(obj);
		}
		return false;
	
	}
}
