package module6;

import java.util.List;

import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.SimpleLinesMarker;
import processing.core.PGraphics;

/** 
 * A class to represent AirportMarkers on a world map
 * @author Adam Setters and the UC San Diego Intermediate Software Development
 * MOOC team
 * delete demos.Airport
 */

public class AirportMarker extends CommonMarker {
	public static List<SimpleLinesMarker> routes;
	
	public AirportMarker(Feature city) {
		super(((PointFeature)city).getLocation(), city.getProperties());
	}
	
	@Override
	public void drawMarker(PGraphics pg, float x, float y) {
		pg.fill(11);
		pg.ellipse(x, y, 5, 5);	
	}

	@Override
	public void showTitle(PGraphics pg, float x, float y) {
		pg.pushStyle();
		pg.textSize(11);
		pg.fill(0);
		pg.text(getStringProperty("name"), x, y-10);
		pg.popStyle();	
	}
	
}
