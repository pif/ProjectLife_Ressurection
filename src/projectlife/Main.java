package projectlife;

import java.awt.Dimension;
import java.awt.Toolkit;

import processing.core.*;
import processing.opengl.*;
import processing.xml.XMLElement;

public class Main extends PApplet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1886093456349654286L;
	public Level level;
	public Controller controller;
	public MenuManager menu;

	public PFont debuggy;
	public String runPath;
	public String dataPath;
	public String levelPath;
	public boolean debug;

	public void setup() {
		// size(600, 300, P2D);
		// int x=screen.width,y=screen.height;
		Dimension scr = Toolkit.getDefaultToolkit().getScreenSize();
		
		size(scr.width, scr.height, OPENGL);

		runPath = Main.class.getResource("./").getPath().substring(1);// toString();
		dataPath = runPath + "../data/";
		XMLElement preferences = new XMLElement(this, dataPath + "main.xml");

		if (Integer.parseInt(preferences.getChild("debug").getContent()) == 1) {
			debug = true;
		} else {
			debug = false;
		}
		debuggy = createFont("arial", 32);
		textFont(debuggy);
		// System.out.println(preferences.getChild("fps").getContent());
		if(Integer.parseInt(preferences.getChild("fps").getContent())!=-1) {
			frameRate(Integer.parseInt(preferences.getChild("fps").getContent()));
		}
		if (Integer.parseInt(preferences.getChild("smooth").getContent()) == 1) {
			smooth();
		}
		if (Integer.parseInt(preferences.getChild("stroke").getContent()) == 0) {
			noStroke();
		}
		// image(loadImage(runPath+"../data/beast.png"),0,0);
		final int cursor = Integer.parseInt(preferences.getChild("cursor")
				.getContent());
		if (cursor == -1) {
			cursor(loadImage(preferences.getChild("cursor").getStringAttribute(
					"src")), 8, 8);
		} else {
			cursor(cursor);
		}

		background(Integer.parseInt(preferences.getChild("bg").getContent()));
		rectMode(Integer.parseInt(preferences.getChild("rect").getContent()));
		ellipseMode(Integer.parseInt(preferences.getChild("ellipse")
				.getContent()));

		levelPath = dataPath + preferences.getChild("levelDir").getContent()
				+ "/";

		level = new Level("", this);
		controller = new Controller(this, level.warrior, preferences
				.getChild("controller"));
		menu = new MenuManager(this, preferences.getChild("menu"));

	}

	public void draw() {
		// controller.controlMouse();
		level.display();

		menu.display();

		if (mousePressed) {
			controller.press(mouseButton);
		}

		if (debug) {
			text(((int) frameRate) + " " + level.beasts.length, 50, 50);
		}

	}

	public void keyPressed() {
		controller.press(keyCode);
	}

	public void keyReleased() {
		controller.release(keyCode);
	}

	public void mousePressed() {
		controller.press(mouseButton);
	}

	public void mouseReleased() {
		controller.release(mouseButton);
	}

	public static void main(String args[]) {
	    PApplet.main(new String[] { 
	    		"--present",
	    		"--bgcolor=#000000",
	    		"--present-stop-color=#000000",
	    		"Main"
	    	    });
	}
}
