package demos;

/** a class to test when value is passed, when reference is passed
 * DinhHuuNguyen
 * 02/04/2016
 **/

public class ReferencesVersusValues {
	
	private double coords[];
	
	public ReferencesVersusValues(double[] crds) {
		System.out.println(crds);
		this.coords = crds;
	}

	public static void main(String[] args) {
		double[] coords = {0.0, 1.0};
		System.out.println(coords);
		double[] test = {coords[0], coords[1]};
		ReferencesVersusValues temp = new ReferencesVersusValues(coords);
		coords[0] = 2.0;
		coords[1] = 3.0;
		System.out.println(temp.coords);
		System.out.println(temp.coords[0] + "\t" + temp.coords[1]);
		System.out.println(test);
		System.out.println(test[0] + "\t" + test[1]);
	}

}