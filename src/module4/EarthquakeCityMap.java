package module4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.AbstractShapeMarker;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.MultiMarker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.utils.MapUtils;
import parsing.ParseFeed;
import processing.core.PApplet;

/** EarthquakeCityMap
 * An application with an interactive map displaying earthquake data.
 * Author: UC San Diego Intermediate Software Development MOOC team
 * DinhHuuNguyen
 * 02/10/2016
 */

public class EarthquakeCityMap extends PApplet {
	
	// We will use member variables, instead of local variables, to store the data
	// that the setUp and draw methods will need to access (as well as other methods)
	// You will use many of these variables, but the only one you should need to add
	// code to modify is countryQuakes, where you will store the number of earthquakes
	// per country.
	
	// You can ignore this.  It's to get rid of eclipse warnings
	private static final long serialVersionUID = 1L;

	// IF YOU ARE WORKING OFFILINE, change the value of this variable to true
	private static final boolean offline = false;
	
	/** This is where to find the local tiles, for working without an Internet connection */
	public static String mbTilesString = "blankLight-1-3.mbtiles";

	//feed with magnitude 2.5+ Earthquakes
	private String earthquakesURL = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_week.atom";
	
	// The files containing city names and info and country names and info
	private String cityFile = "city-data.json";
	private String countryFile = "countries.geo.json";
	
	// The map
	private UnfoldingMap map;
	
    // List of country markers
	private List<Marker> countryMarkers;
	// List of city markers
	private List<Marker> cityMarkers;
	// List of earthquake markers
	private List<Marker> quakeMarkers;
	
	private int red = color(255, 0, 0);
    private int yellow = color(255, 255, 0);
    private int blue = color(0, 0, 255);

	public void setup() {		
		// (1) Initializing canvas and map tiles
		size(850, 700, OPENGL);
		if (offline) {
		    map = new UnfoldingMap(this, 200, 50, 600, 600, new MBTilesMapProvider(mbTilesString));
		    earthquakesURL = "2.5_week.atom";  // The same feed, but saved August 7, 2015
		}
		else {
			map = new UnfoldingMap(this, 200, 50, 600, 600, new Google.GoogleMapProvider());
			// IF YOU WANT TO TEST WITH A LOCAL FILE, uncomment the next line
		    //earthquakesURL = "2.5_week.atom";
		}
		MapUtils.createDefaultEventDispatcher(this, map);
		
		// FOR TESTING: Set earthquakesURL to be one of the testing files by uncommenting
		// one of the lines below.  This will work whether you are online or offline
		//earthquakesURL = "test1.atom";
		//earthquakesURL = "test2.atom";
		
		// WHEN TAKING THIS QUIZ: Uncomment the next line
		//earthquakesURL = "quiz1.atom";
		
		
		// (2) Reading in earthquake data and geometric properties
	    //     STEP 1: load country features and markers
		List<Feature> countries = GeoJSONReader.loadData(this, countryFile);
		countryMarkers = MapUtils.createSimpleMarkers(countries);
		
		//     STEP 2: read in city data
		List<Feature> cities = GeoJSONReader.loadData(this, cityFile);
		cityMarkers = new ArrayList<Marker>();
		for(Feature city : cities) {
		  cityMarkers.add(new CityMarker(city));
		}
	    
		//     STEP 3: read in earthquake RSS feed
		List<PointFeature> earthquakes = ParseFeed.parseEarthquake(this, earthquakesURL);
	    quakeMarkers = new ArrayList<Marker>();
	    
	    for(PointFeature feature : earthquakes) {
		  //check if LandQuake
		  if(isLand(feature)) {
		    quakeMarkers.add(new LandQuakeMarker(feature));
		  }
		  // OceanQuakes
		  else {
		    quakeMarkers.add(new OceanQuakeMarker(feature));
		  }
	    }
	    
	    // could be used for debugging
	    printQuakes();
	 		
	    // (3) Add markers to map
	    //     NOTE: Country markers are not added to the map.  They are used
	    //           for their geometric properties
	    map.addMarkers(quakeMarkers);
	    map.addMarkers(cityMarkers);
	    
	}  // End setup
	
	public void draw() {
		background(0);
		map.draw();
		addKey();
		
	}
	
	// helper method to draw key in GUI
	// TODO: Update this method as appropriate
	private void addKey() {	
		// Remember you can use Processing's graphics methods here
		fill(255);
		rect(25, 50, 150, 250);
		
		textAlign(LEFT, CENTER);
		textSize(12);
		fill(0);
		text("Earthquakes", 50, 70);
		
		fill(255);
		triangle(50,115,60,115,55,105);
		ellipse(55,130,10,10);
		rect(50,145,10,10);
		fill(0);
		text("city", 75, 110);
		text("land quake", 75, 130);
		text("ocean quake", 75, 150);
		text("size ~ magnitude", 50, 170);
			
		text("70- Shallow", 75, 190);
		text("70+ Intermediate", 75, 210);
		text("300+ Deep", 75, 230);
		text("Past Day", 75, 250);
		fill(yellow);
		ellipse(55, 190, 10, 10);		
		fill(blue);
		ellipse(55, 210, 10, 10);
		fill(red);
		ellipse(55, 230, 10, 10);
		line(50,245, 60,255);
		line(50,255, 60,245);
	}

	// Checks whether this quake occurred on land
	// sets "country" property of its PointFeature to the country where it occurred
	// returns true, else returns false
	// Note that the helper method isInCountry will set this "country" property already
	private boolean isLand(PointFeature earthquake) {
		for (Marker country : countryMarkers) {
			if (isInCountry(earthquake, country)) {
				return true;
			}
		}
		return false;
	}
	
	// prints countries with number of earthquakes
	// You will want to loop through the country markers or country features
	// and then for each country, loop through the quakes to count how many occurred in that country
	// Recall that country markers have a "name" property and LandQuakeMarkers have a "country" property
	private void printQuakes() {
		HashMap<String, Integer> quakesInCountries = new HashMap<String, Integer>();
		quakesInCountries.put("Ocean", 0);
		for (Marker earthquake : quakeMarkers) {
			if (earthquake.getProperties().containsKey("country")) {
				String name = earthquake.getProperty("country").toString();
				if (! quakesInCountries.containsKey(name)) {
					quakesInCountries.put(name, 1);
				}
				else {
					quakesInCountries.put(name, quakesInCountries.get(name) + 1);
				}
			}
			else {
				quakesInCountries.put("Ocean", quakesInCountries.get("Ocean") + 1);
			}
		}
		for (String country : quakesInCountries.keySet()) {
			System.out.println(country + " " + quakesInCountries.get(country));
		}
		
	}
	
    // helper method to test whether a given earthquake is in some country
    // This will also add the country property to the properties of the earthquake feature
    // if it's in one of the countries. You should not have to modify this code
	private boolean isInCountry(PointFeature earthquake, Marker country) {
		// getting location of feature
		Location checkLoc = earthquake.getLocation();
		// some countries represented it as MultiMarker
		// looping over SimplePolygonMarkers which make them up to use isInsideByLoc
		if(country.getClass() == MultiMarker.class) {		
			// looping over markers making up MultiMarker
			for(Marker marker : ((MultiMarker)country).getMarkers()) {	
				// checking if inside
				if(((AbstractShapeMarker)marker).isInsideByLocation(checkLoc)) {
					earthquake.addProperty("country", country.getProperty("name"));		
					// return if is inside one
					return true;
				}
			}
		}		
		// check if inside country represented by SimplePolygonMarker
		else if(((AbstractShapeMarker)country).isInsideByLocation(checkLoc)) {
			earthquake.addProperty("country", country.getProperty("name"));
			return true;
		}
		return false;
	}
	
	public int getYellow() {
		return yellow;
	}
	
	public int getBlue() {
		return blue;
	}
	
	public int getRed() {
		return red;
	}

}
