package module6;

import de.fhpotsdam.unfolding.data.PointFeature;
import processing.core.PConstants;
import processing.core.PGraphics;

/** Implements a visual marker for earthquakes on an earthquake map
 * @author UC San Diego Intermediate Software Development MOOC team
 */

// TODO: Implement the comparable interface
public abstract class EarthquakeMarker extends CommonMarker implements Comparable<EarthquakeMarker> {
	
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

	// constants for distance
	protected static final float kmPerMile = 1.6f;
		
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
	
	// TODO: method compareTo to implement Comparable inteterface
	public int compareTo(EarthquakeMarker other) {
		Float a = new Float(this.getMagnitude());
		Float b = new Float(other.getMagnitude());
		return b.compareTo(a);
	}
	
	// implement abstract method drawMarker in superclass CommonMarker
	public void drawMarker(PGraphics pg, float x, float y) {
		// save previous styling
		pg.pushStyle();
			
		// determine color of marker from depth
		colorDetermine(pg);
		
		// call abstract method implemented in subclasses LandMarker and OceanMarker
		// draw marker shape
		drawEarthquake(pg, x, y);
		
		// Optional: checks age and draws X if needed
		String age = getStringProperty("age");
		if ("Past Hour".equals(age) || "Past Day".equals(age)) {
			pg.strokeWeight(2);
			pg.strokeWeight(2);
			pg.line(x - radius/2, y - radius/2, x + radius/2, y + radius/2);
			pg.line(x - radius/2, y + radius/2, x + radius/2, y - radius/2);			
		}
		
		// reset to previous styling
		pg.popStyle();
	}

	// implement abstract method showTitle in superclass CommonMarker
	// show title of earthquake if this marker is selected
	public void showTitle(PGraphics pg, float x, float y) {
		pg.pushStyle();
		pg.textAlign(PConstants.LEFT, PConstants.TOP);
		pg.textSize(11);
		pg.fill(0);
		pg.text(getTitle(), x, y-10);
		pg.popStyle();
	}

	/**
	 * Return the "threat circle" within which this earthquake can affect things   
	 * DISCLAIMER: this formula is for illustration purposes
	 * and not intended to be used for safety-critical or predictive applications
	 */
	public double threatCircle() {	
		double miles = 20.0f * Math.pow(1.8, 2*getMagnitude()-5);
		double km = (miles * kmPerMile);
		return km;
	}
	
	// determine color of marker from depth
	// We use: Deep = red, intermediate = blue, shallow = yellow
	private void colorDetermine(PGraphics pg) {
		float depth = getDepth();
		if (depth < THRESHOLD_INTERMEDIATE) {
			pg.fill(255, 255, 0);
		}
		else if (depth <= THRESHOLD_DEEP) {
			pg.fill(0, 0, 255);
		}
		else {
			pg.fill(255, 0, 0);
		}
	}
	
	/* getters for earthquake properties */	
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
	
	/** Returns an earthquake marker's string representation */
	public String toString() {
		return getTitle();
	}

}
