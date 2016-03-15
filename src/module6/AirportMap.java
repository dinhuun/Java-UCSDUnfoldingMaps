package module6;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.data.ShapeFeature;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.SimpleLinesMarker;
import de.fhpotsdam.unfolding.utils.MapUtils;
import de.fhpotsdam.unfolding.geo.Location;
import parsing.ParseFeed;
import processing.core.PApplet;

/** 
 * An applet that shows flight routes within a 50km radius of mouseclick location 
 * @author Adam Setters and the UC San Diego Intermediate Software Development MOOC team
 * DinhHuuNguyen
 * 03/04/2016
 */

public class AirportMap extends PApplet {
	
	UnfoldingMap map;
	// list for airport markers
	private List<Marker> airportMarkers;
	// HashMap with key OpenFlights unique ids for quicker access when matching with routes
	private HashMap<Integer, Location> airportHash;
	// list for route markers
	private List<Marker> routeMarkers;
	
	public void setup() {
		// setting up PAppler
		size(900,700, OPENGL);
		
		// setting up map and default events
		map = new UnfoldingMap(this, 50, 50, 800, 600);
		MapUtils.createDefaultEventDispatcher(this, map);
		
		// get PointFeatures from airport data
		List<PointFeature> airportFeatures = ParseFeed.parseAirports(this, "airports.dat");	
		// initialize and fill airportMarkers and airportHash
		airportMarkers = new ArrayList<Marker>();
		airportHash = new HashMap<Integer, Location>();
		for(PointFeature airportF : airportFeatures) {
			AirportMarker airportM = new AirportMarker(airportF);
			airportM.setRadius(5);
			airportM.setHidden(true); // there are just too many to show
			airportMarkers.add(airportM);
			// put airport in hashmap with OpenFlights unique id for key
			airportHash.put(Integer.parseInt(airportF.getId()), airportF.getLocation());
		}
			
		// get ShapeFeatures from route data
		List<ShapeFeature> routeFeatures = ParseFeed.parseRoutes(this, "routes.dat");
		// add route source Location and route dest Location
		// initialize and fill routeMarkers
		routeMarkers = new ArrayList<Marker>();
		for(ShapeFeature routeF : routeFeatures) {			
			// get route source airportId and route dest airportId
			int source = Integer.parseInt((String)routeF.getProperty("source"));
			int dest = Integer.parseInt((String)routeF.getProperty("destination"));		
			// get source Location and dest Location via source airport and dest airport on route
			if(airportHash.containsKey(source) && airportHash.containsKey(dest)) {
				routeF.addLocation(airportHash.get(source));
				routeF.addLocation(airportHash.get(dest));
			}
			
			SimpleLinesMarker routeM = new SimpleLinesMarker(routeF.getLocations(), routeF.getProperties());
			routeM.setHidden(true); // there are just too many to show
			routeMarkers.add(routeM);
		}
		
		map.addMarkers(airportMarkers);
		map.addMarkers(routeMarkers);		
	}
	
	public void draw() {
		background(0);
		map.draw();
		addKey();
	}
	
	@Override
	public void mouseClicked() {
		Location location = map.getLocation(mouseX, mouseY);
		// hideAllMarkers();
		unhideMarkers(location);
	}
	
	private void unhideMarkers(Location l) {
		ArrayList<Location> array = new ArrayList<Location>();
		Location s;
		Location d;
		for (Marker routeM : routeMarkers) {
			s = ((SimpleLinesMarker)routeM).getLocation(0);
			d = ((SimpleLinesMarker)routeM).getLocation(1);
			if (l.getDistance(s) < 50.0 || l.getDistance(d) < 50.0) {
				routeM.setHidden(false);
				array.add(s);
				array.add(d);
			}
			else {
				routeM.setHidden(true);
			}
		}
		for (Marker airportM : airportMarkers) {
			if (array.contains(airportM.getLocation())) {
				airportM.setHidden(false);
				airportM.setSelected(true);
			}
			else {
				airportM.setHidden(true);
			}
		}
	}
	
	private void addKey() {
		textAlign(LEFT, CENTER);
		textSize(14);
		fill(150,150,150);
		text("Click your location to see flights within its 50km radius", 200, 20);
	}

}
