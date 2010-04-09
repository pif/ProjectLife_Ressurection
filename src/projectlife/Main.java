package projectlife;
import processing.core.*;

public class Main extends PApplet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1886093456349654286L;
	Level level;
	public Controller controller;
	MenuManager menu;

	public void setup() {
		// size(600, 300, P2D);
		size(screen.width, screen.height, JAVA2D);
		frameRate(25);
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
	}

	public void draw() {

		// controller.controlMouse();

		level.display();

		menu.display();

		if (mousePressed) {
			controller.press(mouseButton);
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

	public void Init(String xmlFile) {

	}

	public static void main(String args[]) {
		PApplet.main(new String[] { "--present", "Main" });
	}
}
