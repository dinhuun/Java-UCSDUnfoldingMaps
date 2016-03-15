package module4;

import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import processing.core.PGraphics;

/** Implements a visual marker for earthquakes on an earthquake map
 * @author UC San Diego Intermediate Software Development MOOC team
 * DinhHuuNguyen
 * 02/10/2016
 */

public abstract class EarthquakeMarker extends SimplePointMarker {
	
	// Did the earthquake occur on land?  This will be set by the subclasses.
	protected boolean isOnLand;

	// The radius of the Earthquake marker
	// You will want to set this in the constructor, either
	// using the thresholds below, or a continuous function based on magnitude. 
	protected float radius;
	
	/** Greater than or equal to this threshold is a moderate earthquake */
	public static final float THRESHOLD_MODERATE = 5;
	/** Greater than or equal to this threshold is a light earthquake */
	public static final float THRESHOLD_LIGHT = 4;

	/** Greater than or equal to this threshold is an intermediate depth */
	public static final float THRESHOLD_INTERMEDIATE = 70;
	/** Greater than or equal to this threshold is a deep depth */
	public static final float THRESHOLD_DEEP = 300;

	// ADD constants for colors if you want
	
	// abstract method implemented in derived classes
	public abstract void drawEarthquake(PGraphics pg, float x, float y);

	// constructor
	public EarthquakeMarker (PointFeature feature) {
		super(feature.getLocation());
		// Add a radius property and then set the properties
		java.util.HashMap<String, Object> properties = feature.getProperties();
		float magnitude = Float.parseFloat(properties.get("magnitude").toString());
		properties.put("radius", 2*magnitude );
		setProperties(properties);
		this.radius = 1.75f*getMagnitude();
	}

	// implement abstract method draw in superclass SimplePointMarker
	public void draw(PGraphics pg, float x, float y) {
		// save previous styling
		pg.pushStyle();
			
		// determine color of marker from depth
		colorDetermine(pg);
		
		// call abstract method implemented in subclasses LandMarker and OceanMarker
		// draw marker shape
		drawEarthquake(pg, x, y);
		
		// Optional: checks age and draws X if needed
		String age = this.getStringProperty("age");
		if (age.equals("Past Hour") || age.equals("Past Day")) {
			pg.strokeWeight(2);
			pg.line(x - radius/2, y - radius/2, x + radius/2, y + radius/2);
			pg.line(x - radius/2, y + radius/2, x + radius/2, y - radius/2);
		}
		
		if (this.isSelected()) {
			String title = this.getTitle();
			Float depth = this.getDepth();
			pg.textSize(3*radius/2);
			pg.fill(255, 51, 51);
			pg.text(title, x + 3*radius/2, y);
			pg.text(depth, x + 3*radius/2, y + 10);
		}
		
		// reset to previous styling
		pg.popStyle();	
	}
	
	// determine color of marker from depth
	// We suggest: Deep = red, intermediate = blue, shallow = yellow
	private void colorDetermine(PGraphics pg) {
		float d = this.getDepth();
		EarthquakeCityMap eqcm = new EarthquakeCityMap();
		if (d < THRESHOLD_INTERMEDIATE) {
			pg.fill(eqcm.getYellow());
		}
		else if (d <= THRESHOLD_DEEP) {
			pg.fill(eqcm.getBlue());
		}
		else {
			pg.fill(eqcm.getRed());
		}
	}
	
	// You might find the getters for earthquake properties below helpful
	public float getMagnitude() {
		return Float.parseFloat(getProperty("magnitude").toString());
	}
	
	public float getDepth() {
		return Float.parseFloat(getProperty("depth").toString());	
	}
	
	public String getTitle() {
		return (String) getProperty("title");	
	}
	
	public float getRadius() {
		return Float.parseFloat(getProperty("radius").toString());
	}
	
	public boolean isOnLand() {
		return isOnLand;
	}
	
}
