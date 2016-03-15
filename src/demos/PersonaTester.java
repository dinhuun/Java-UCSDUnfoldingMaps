package demos;

/** a class to test abstract class Persona and subclass PersonaEstudiente
 * DinhHuuNguyen
 * 02/04/2016
 **/

public class PersonaTester {
	
	public static void main (String[] args) {
		Persona m = new Persona("Clare");
		System.out.println(m);
		Persona n = new PersonaEstudiente("Bill", 1);
		System.out.println(n);
	}
	
}