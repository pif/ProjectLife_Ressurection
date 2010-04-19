package projectlife;

import processing.core.*;

/**
*/
public class MenuManager extends MyObject {

	public boolean gameState;// true == game is on, false == menu is on fire!

	public MenuManager(Main applet) {
		super(applet);
		overlays = new Overlay[] {
				new Overlay(applet, new PVector(p.width / 2, 50), p.dataPath+"images/play.png",
						PApplet.radians(0), 100, 0, new PVector()),
				new Overlay(applet, new PVector(p.width / 2, 100),
						p.dataPath+"images/records.png", PApplet.radians(0), 10100, 0,
						new PVector()),
				new Overlay(applet, new PVector(p.width / 2, 150), p.dataPath+"images/exit.png",
						PApplet.radians(0), 20100, 0, new PVector()) };
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
