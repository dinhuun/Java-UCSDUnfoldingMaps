package module5;

import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.geo.Location;
import processing.core.PGraphics;

/** Implements a visual marker for cities on an earthquake map
 * @author UC San Diego Intermediate Software Development MOOC team
 * DinhHuuNguyen
 * 02/16/2016
 */

// TODO: Change SimplePointMarker to CommonMarker as the very first thing you do in module 5
// now CityMarker extends CommonMarker).  It will cause an error. That's expected
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

	// Local getters for some city properties
	public String getCity() {
		return getStringProperty("name");
	}
	
	public String getCountry() {
		return getStringProperty("country");
	}
	
	public float getPopulation() {
		return Float.parseFloat(getStringProperty("population"));
	}
	
}
