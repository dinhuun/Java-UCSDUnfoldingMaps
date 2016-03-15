package module6;

import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.geo.Location;
import processing.core.PConstants;
import processing.core.PGraphics;

/** Implements a visual marker for cities on an earthquake map
 * @author UC San Diego Intermediate Software Development MOOC team
 */
public class CityMarker extends CommonMarker {
	
	// The size of the triangle marker
	public static int TRI_SIZE = 5;
	
	public CityMarker(Location location) {
		super(location);
	}
	
	public CityMarker(Feature city) {
		super(((PointFeature)city).getLocation(), city.getProperties());
	}
	
	
	/** Implementation of method to draw marker on the map */
	public void drawMarker(PGraphics pg, float x, float y) {
		// Save previous drawing style
		pg.pushStyle();
		
		// IMPLEMENT: drawing triangle for each city
		pg.fill(150, 30, 30);
		pg.triangle(x - TRI_SIZE, y + TRI_SIZE, x + TRI_SIZE, y + TRI_SIZE, x, y - TRI_SIZE);
		
		// Restore previous drawing style
		pg.popStyle();
	}
	
	/** Show the title of the city if this marker is selected */
	public void showTitle(PGraphics pg, float x, float y) {
		// TODO: Implement this method
		pg.pushStyle();
		pg.textSize(11);
		pg.fill(0);
		pg.text(getCity() + ", " + getCountry() + ", " + getPopulation(), x, y-10);
		pg.popStyle();
	}
	
	private String getCity() {
		return getStringProperty("name");
	}
	
	private String getCountry() {
		return getStringProperty("country");
	}
	
	private float getPopulation() {
		return Float.parseFloat(getStringProperty("population"));
	}
}
