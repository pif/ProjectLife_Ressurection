package projectlife;
import processing.core.*;

/**
*/
public class MenuManager extends MyObject {

	public boolean gameState;// true == game is on, false == menu is on fire!

	public MenuManager(Main applet) {
		super(applet);
		overlays = new Overlay[] {
				new Overlay(applet, new PVector(p.width / 2, 50), "play.png",
						PApplet.radians(30), 100, 0, new PVector()),
				new Overlay(applet, new PVector(p.width / 2, 100),
						"records.png", PApplet.radians(-10), 10100, 0,
						new PVector()),
				new Overlay(applet, new PVector(p.width / 2, 150), "exit.png",
						PApplet.radians(20), 20100, 0, new PVector()) };
		gameState = true;
		// overlays = (Overlay[])PApplet.append(overlays, new Overlay(applet,
		// position, img, angle, color, radius))
	}

	/**
*/
	public Overlay[] overlays;

	/**
	 * @param Return
	 */
	public void click() {
	}

	public void display() {
		if (!gameState) {
			p.pushStyle();
			p.fill(0, 0, 0, 140);
			p.rect(0, 0, p.width, p.height);
			p.popStyle();
		}

		for (int i = 0; i < overlays.length; i++) {
			overlays[i].display();
		}
	}
}
