package module3;

// Java libraries
import java.util.ArrayList;
import java.util.List;

// Unfolding libraries
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.utils.MapUtils;

// Processing library
import processing.core.PApplet;

// Parsing library
import parsing.ParseFeed;

/** EarthquakeCityMap
  * An application with an interactive map displaying earthquake data.
  * Author: UC San Diego Intermediate Software Development MOOC team
  * @author DinhHuuNguyen
  * Date: 02/09/2016
  **/

public class EarthquakeCityMap extends PApplet {

	// You can ignore this.  It's to keep eclipse from generating a warning.
	private static final long serialVersionUID = 1L;

	// IF YOU ARE WORKING OFFLINE, change the value of this variable to true
	private static final boolean offline = false;
	
	// Less than this threshold is a light earthquake
	public static final float THRESHOLD_MODERATE = 5;
	// Less than this threshold is a minor earthquake
	public static final float THRESHOLD_LIGHT = 4;

	/** This is where to find the local tiles, for working without an Internet connection */
	public static String mbTilesString = "blankLight-1-3.mbtiles";
	
	// The map
	private UnfoldingMap map;
	
	//feed with magnitude 2.5+ Earthquakes
	private String earthquakesURL = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_week.atom";

	// Here is an example of how to use Processing's color method to generate 
    // an int that represents the colors red, yellow, blue.
    private int red = color(255, 0, 0);
    private int yellow = color(255, 255, 0);
    private int blue = color(0, 0, 255);
	
	public void setup() {
		size(950, 600, OPENGL);

		if (offline) {
		    map = new UnfoldingMap(this, 200, 50, 700, 500, new MBTilesMapProvider(mbTilesString));
		    earthquakesURL = "2.5_week.atom"; 	// Same feed, saved Aug 7, 2015, for working offline
		}
		else {
			map = new UnfoldingMap(this, 200, 50, 700, 500, new Google.GoogleMapProvider());
			// IF YOU WANT TO TEST WITH A LOCAL FILE, uncomment the next line
			//earthquakesURL = "2.5_week.atom";
		}
		
	    map.zoomToLevel(2);
	    MapUtils.createDefaultEventDispatcher(this, map);	
			
	    //Use provided parser to collect properties for each earthquake
	    //PointFeatures have a getLocation method
	    List<PointFeature> earthquakes = ParseFeed.parseEarthquake(this, earthquakesURL);
	    
	    // These print statements show you (1) all of the relevant properties 
	    // in the features, and (2) how to get one property and use it
	    if (earthquakes.size() > 0) {
	    	PointFeature f = earthquakes.get(0);
	    	System.out.println(f.getProperties());
	    	Object magObj = f.getProperty("magnitude");
	    	float mag = Float.parseFloat(magObj.toString());
	    	System.out.println("0th earthquake has magnitude " + mag);
	    	System.out.println(earthquakes.size());
	    	// PointFeatures also have a getLocation method
	    }
	    
	    // The List you will populate with new SimplePointMarkers
	    List<Marker> markers = new ArrayList<Marker>();
	    for (PointFeature ft : earthquakes) {
	    	SimplePointMarker mk = createMarker(ft);
	    	markers.add(mk);
	    }
	    	    
	    map.addMarkers(markers);
	}
		
	// A suggested helper method that takes in an earthquake feature and 
	// returns a SimplePointMarker for that earthquake
	// TODO: Implement this method and call it from setUp, if it helps
	private SimplePointMarker createMarker(PointFeature feature) {
		// finish implementing and use this method, if it helps.
		SimplePointMarker mk = new SimplePointMarker(feature.getLocation(), feature.getProperties());
		Object magnitudeOb = mk.getProperty("magnitude");
    	float magnitude = Float.parseFloat(magnitudeOb.toString());
    	if (magnitude < THRESHOLD_LIGHT) {
    		mk.setRadius(5);
    		mk.setColor(blue);
    	}
    	if (THRESHOLD_LIGHT <= magnitude && magnitude < THRESHOLD_MODERATE) {
    		mk.setRadius(10);
    		mk.setColor(yellow);
    	}
    	if (THRESHOLD_MODERATE <= magnitude) {
    		mk.setRadius(15);
    		mk.setColor(red);
    	}
		return mk;
	}
	
	public void draw() {
	    background(10);
	    map.draw();
	    addKey();
	}

	// helper method to draw key in GUI
	// TODO: Implement this method to draw the key
	private void addKey() {
		// Remember you can use Processing's graphics methods here
		fill(255);
		rect(25, 50, 150, 250);
		
		fill(0);
		text("World Map", 50, 70);
		text("Earthquake", 50, 90);
		
		text("5.0+ Mag", 75, 130);
		text("4.0+ Mag", 75, 150);
		text("4.0- Mag", 75, 170);
		
		fill(red);
		ellipse(55, 125, 10, 10);		
		fill(yellow);
		ellipse(55, 145, 10, 10);
		fill(blue);
		ellipse(55, 165, 10, 10);
	}
	
}
