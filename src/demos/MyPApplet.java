package demos;

import processing.core.PApplet;
import processing.core.PImage;

/** 
 * A class to illustrate some use of the PApplet class
 * Used in module 3 of the UC San Diego MOOC Object Oriented Programming in Java
 * @author UC San Diego Intermediate Software Development MOOC team
 */

public class MyPApplet extends PApplet {
	PImage img;
	
	public void setup() {
		//Add setup code for MyPApplet
		size(400,400);				//set canvas size
		background(255);			//set canvas color
		stroke(0);				//set pen color
		img = loadImage("palmTrees.jpg");
		img.resize(0,  height);
		image(img, 0, 0);
	}
	
	public void draw() {
		int[] color = sunColorSec(second());
		fill(color[0], color[1], color[2]);
		ellipse(width/4, height/5, width/4, height/5);
	}
	
	public int[] sunColorSec(float seconds) {
		int[] rgb = new int[3];
		float diffFrom30 = Math.abs(30 - seconds);
		float ratio = diffFrom30 / 30;
		rgb[0] = (int)(255 * ratio);
		rgb[1] = (int)(255 * ratio);
		rgb[2] = 0;
		return rgb;
		
	}
	
	public static void main (String[] args) {
		//Add main method for running as application
		PApplet.main(new String[] {"--present", "MyPApplet"});
	}
	
}


