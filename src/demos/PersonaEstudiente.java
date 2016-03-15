package demos;

/** A class to extends Persona and store information about a PersonaEstudiente
 * DinhHuuNguyen
 * 02/04/2016
 **/

public class PersonaEstudiente extends Persona {
	
	private int id;

	public PersonaEstudiente(String n, int id) {
		super(n);
		this.id = id;
	}
	public String toString() {
		return super.toString() + "\t" + id;
	}
	
}
