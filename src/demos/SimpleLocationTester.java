package demos;

/** 
 * A class to illustrate class design and use.
 * Used in module 2 of the UC San Diego MOOC Object Oriented Programming in Java
 * @author UC San Diego Intermediate Software Development MOOC team
 **/

public class SimpleLocationTester {
	
	public static void main(String[] args) {
		SimpleLocation ucsd = new SimpleLocation(32.9, -117.2);
		SimpleLocation lima = new SimpleLocation(-12.0, -77.0);
		
		//latitude = -12.04;
		System.out.println(ucsd.distance(lima));
		System.out.println(ucsd.getLatitude());
		ucsd.setLatitude(32.8);
		System.out.println(ucsd.getLatitude());
		
		SimpleLocation x = new SimpleLocation(-12.0, -77.0);
		System.out.println(lima + "\t" + x);
	}

}
