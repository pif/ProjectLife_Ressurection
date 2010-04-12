package projectlife;
import processing.core.*;
import processing.opengl.*;

public class Main extends PApplet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1886093456349654286L;
	public Level level;
	public Controller controller;
	public MenuManager menu;

	public PFont debuggy;
	public void setup() {
		// size(600, 300, P2D);
		//int x=screen.width,y=screen.height;
		size(800, 600, OPENGL);
		frameRate(30);
		smooth();
		noStroke();
		cursor(CROSS);
		background(100);
		rectMode(CENTER);
		ellipseMode(CENTER);
		// TODO load config.xml file
		level = new Level("",this);
		controller = new Controller(this, level.warrior);
		menu = new MenuManager(this);
		
		debuggy = createFont("arial", 32);
		textFont(debuggy);
	}

	public void draw() {
		// controller.controlMouse();
		level.display();

		menu.display();
		
		if (mousePressed) {
			controller.press(mouseButton);
		}
		
		text(((int)frameRate)+" "+level.beasts.length,50,50);
		
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

	public void Init(String xmlFile) {

	}

	public static void main(String args[]) {
		PApplet.main(new String[] { "--present", "Main" });
	}
}
