package projectlife;

import processing.core.*;

/**
 * 
 * @author Ostap Level is used to manage levels of game. -- loads data from xml
 *         file, -- creates all the units on field. -- displays all units --
 *         controls all units
 */
public class Level extends MyObject {

	public boolean passed; 
	
	public Warrior warrior;
	public Ground ground;
	public Beast[] beasts;
	public Bonus[] bonuses;
	public Overlay[] overlays;
	public CloudsManager clouds;

	public Level(String xmlPath, Main applet) {

		super(applet);

		warrior = new Warrior(applet, new PVector(applet.width / 2,
				applet.height / 2), "warrior.png", 0, 0, 0, 100, 8, new Weapon(
				applet)/*, 20, 10, 50, (float) 0.1, 30, 500, 100)*/, new PVector(),
				0);

		ground = new Ground("1.png", true, applet);

		beasts = new Beast[0];
		bonuses = new Bonus[0];
		overlays = new Overlay[0];
		
		passed=false;
		// TODO bullet problem. if they'r too quick. the overjump bastards
	}

	public void display() {
		// draw level ground
		ground.drawBackground();

		for (int i = 0; i < beasts.length; i++) {
			if (beasts[i].visible) {
				beasts[i].display();
			} else {
				beasts[i] = beasts[beasts.length - 1];
				beasts = (Beast[]) (PApplet.shorten(beasts));
			}

		}

		for (int i = 0; i < bonuses.length; i++) {
			bonuses[i].display();
		}

		warrior.display();

	}

	public void start() {
		for (int i = 0; i < beasts.length; i++) {
			beasts[i].letGo();
		}
		warrior.letGo();
	}

	public void suspend() {
		for (int i = 0; i < beasts.length; i++) {
			beasts[i].stop();
		}
		warrior.stop();
	}

	public boolean isCoordianteOnBoard(PVector coordinates) {
		if(coordinates.x>0 && coordinates.x<p.width)
			if(coordinates.y>0 && coordinates.y<p.height)
				return true;
		return false;
	}
}
