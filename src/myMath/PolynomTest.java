package myMath;

public class PolynomTest {
	public static void main(String[] args) {
		test1();
		test2();
		test3();
		test4();
	}
	public static void test1() {
		Polynom p1 = new Polynom();
		String[] monoms = {"1","x","0.5x^2", "0.5x^2"};
		for(int i=0;i<monoms.length;i++) {
		Monom m = new Monom(monoms[i]);
		p1.add(m);
		}
		System.out.println("********Test 1:********");
		double aa = p1.area(0, 1, 0.0001);
		System.out.println("p1: "+p1);
		p1.multiply(p1); 
		System.out.println("p1*p1: "+p1);
		p1.substract(p1);
		System.out.println("p1: "+p1);
		System.out.println("The root is: "+p1.root(0,1, 0.000001));
		System.out.println("The area is: "+aa);
		System.out.println("p1-p1: "+p1);
	}
	public static void test2() {
		Polynom p1 = new Polynom(), p2 =  new Polynom(), p3=new Polynom();
		String[] monoms1 = {"2", "-x","-3.2x^2","4","-1.5x^2"};
		String[] monoms2 = {"5", "1.7x","3.2x^2","-3","-1.5x^2"};
		String[] monoms= {"-0x^4","0x"};
		for(int i=0;i<monoms1.length;i++) {
			Monom m = new Monom(monoms1[i]);
			p1.add(m);
		}
		for(int i=0;i<monoms2.length;i++) {
			Monom m = new Monom(monoms2[i]);
			p2.add(m);
		}
		for(int i=0;i<monoms.length;i++) {
			Monom m = new Monom(monoms[i]);
			p3.add(m);
		}
		System.out.println("********Test 2:********");
		Polynom_able p4=p1.copy();
		p4.multiply(Monom.MINUS1);
		System.out.println("p1: "+p1);
		System.out.println("p2: "+p2);
		System.out.println("p3: "+p3.toString());
		System.out.println("p4: "+p4);
		p4.add(p1);
		System.out.println("p1+p4: "+p4);
		p1.add(p2);
		System.out.println("p1+p2: "+p1);
		p1.multiply(p2);
		System.out.println("(p1+p2)*p2: "+p1);
		Polynom_able pp=p1.copy();
		System.out.println("pp: "+pp);
		System.out.println("Are pp and p1 equals? "+pp.equals(p1));
		System.out.println("Are pp and p3 equals? "+pp.equals(p3));
		p2.multiply(p3);
		System.out.println("p2*p3: "+p2);
		System.out.println("Is p3 the zero polynom? "+p3.isZero());
		System.out.println("Is p1 the zero polynom? "+p1.isZero());
		String s1 = p1.toString();
		System.out.println("s1: "+s1);
	}
	public static void test3() {
		Polynom p1 = new Polynom(), p2 =  new Polynom(), p3=new Polynom();
		String[] monoms1 = {"1.3x^3","-44x","+3.x^2","4x"};
		String[] monoms2 = {"432x","2x^2","4"};
		String[] monoms3= {"4.2x","0x","3x^5","-1x","2"};
		for(int i=0;i<monoms1.length;i++) {
			Monom m = new Monom(monoms1[i]);
			p1.add(m);
		}
		for(int i=0;i<monoms2.length;i++) {
			Monom m = new Monom(monoms2[i]);
			p2.add(m);
		}
		for(int i=0;i<monoms3.length;i++) {
			Monom m = new Monom(monoms3[i]);
			p3.add(m);
		}
		System.out.println("********Test 3:********");
		System.out.println("p1: "+p1);
		System.out.println("p2: "+p2);
		System.out.println("p3: "+p3);
		System.out.println("The derivative is: "+p2.derivative());
		p3.add(p1);
		System.out.println("p1+p3: "+p3);
		p1.substract(p2);
		System.out.println("p1-p2: "+p1);
		System.out.println("The area is: "+p2.area(0, 1, 0.00001));
		System.out.println("The root is: "+p2.root(-1, 1, 0.00001));
	}
	public static void test4() {
		Polynom p1 = new Polynom(), p2 =  new Polynom(),
		p3=new Polynom(), p4 =  new Polynom(), p5 =  new Polynom(),
		p6=new Polynom(), p7=new Polynom(), p8=new Polynom(), p9=new Polynom(), p10=new Polynom();
		String[] monoms1 = {"3x^-4"};
		String[] monoms2 = {"3X^+4"};
		String[] monoms3= {"3x^","4"};
		String[] monoms4= {"-3","x"};
		String[] monoms5= {"x"};
		String[] monoms7= {"x","0"};
		String[] monoms8= {"5x2"};
		String[] monoms9= {"4x^0"};
		String[] monoms10= {"x^"};
		System.out.println("********Test 4:********");
		for(int i=0;i<monoms1.length;i++) {
			try {
				Monom m = new Monom(monoms1[i]);
				p1.add(m);
			} catch (Exception e) {
				System.out.println("ERR the power of the Monom is negative");
			}
		}
		for(int i=0;i<monoms2.length;i++) {
			Monom m = new Monom(monoms2[i]);
			p2.add(m);
		}
		for(int i=0;i<monoms3.length;i++) {
			try {
			Monom m = new Monom(monoms3[i]);
			p3.add(m);
			} catch (Exception e) {
				System.out.println("ERR there is no power to the Monom");
			}
		}
		for(int i=0;i<monoms4.length;i++) {
			Monom m = new Monom(monoms4[i]);
			p4.add(m);
		}
		for(int i=0;i<monoms5.length;i++) {
			Monom m = new Monom(monoms5[i]);
			p5.add(m);
		}
		for(int i=0;i<monoms7.length;i++) {
			Monom m = new Monom(monoms7[i]);
			p7.add(m);
		}
		for(int i=0;i<monoms8.length;i++) {
			try {
			Monom m = new Monom(monoms8[i]);
			p8.add(m);
			}catch (Exception e) {
				System.out.println("ERR the Monom valid shape is ax^b OR aX^b");
			}
		}
		for(int i=0;i<monoms9.length;i++) {
			Monom m = new Monom(monoms9[i]);
			p9.add(m);
		}
		for(int i=0;i<monoms10.length;i++) {
			try {
			Monom m = new Monom(monoms10[i]);
			p10.add(m);
			} catch (Exception e) {
				System.out.println("ERR the Monom valid shape is ax^b OR aX^b");
			}
		}
		
		p6=new Polynom(p2.toString());
		System.out.println("Are p2 and p6 equals? "+p2.equals(p6));
		System.out.println("Are p7 and p5 equals? "+p7.equals(p5));
		System.out.println("p2: "+p2);
		System.out.println("p4: "+p4);
		System.out.println("p5: "+p5);
		p5.substract(p4);
		System.out.println("p5-p4: "+p5);
		System.out.println("p9: "+p9);
	}
}
