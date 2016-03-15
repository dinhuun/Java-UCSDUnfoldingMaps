package demos;

import processing.core.PApplet;

public class MyDisplay extends PApplet {
	
	public void setup() {
		//Add setup code for MyPApplet
		size(400,400);				//set canvas size
		background(255);			//set canvas color
		stroke(0);				//set pen color
	}
	
	public void draw() {
		fill(255, 255, 0);
		ellipse(width/2, height/2, width/2, height/2);
		fill(0, 0, 0);
		ellipse(7*width/16, 4*height/9, width/16, height/12);
		fill(0, 0, 0);
		ellipse(9*width/16, 4*height/9, width/16, height/12);
		arc(width/2, 5*height/8, width/7, height/10, 0, PI);
	}
	
	public static void main (String[] args) {
		//Add main method for running as application
		PApplet.main(new String[] {"--present", "MyPApplet"});
	}

}