package projectlife;

import processing.core.*;
import projectlife.weapons.StoneThrower;

/**
 * 
 * @author Ostap Level is used to manage levels of game. -- loads data from xml
 *         file, -- creates all the units on field. -- displays all units --
 *         controls all units
 */
public class Level extends MyObject {

	public boolean passed; 
	
	public Warrior[] warriors;
	public Ground ground;
	public Beast[] beasts;
	public Bonus[] bonuses;
	public Overlay[] overlays;
	public CloudsManager clouds;
	
	public Level(String xmlPath, Main applet) {

		super(applet);
		beasts = new Beast[0];
		warriors = new Warrior[1];
		
		warriors[0] = Warrior.factory(applet, new PVector(applet.width / 2,
				applet.height / 2), "warrior.png", 0, 0, 0, 100, 8, new StoneThrower(applet, warriors[0])/*, 20, 10, 50, (float) 0.1, 30, 500, 100)*/, new PVector(),
				0, this);

		ground = new Ground("1.png", true, applet);


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

		for (int i = 0; i < warriors.length; i++) {
			warriors[i].display();
		}
	}

	public void start() {
		for (int i = 0; i < beasts.length; i++) {
			beasts[i].letGo();
		}
		for (int i = 0; i < warriors.length; i++) {
			warriors[i].letGo();
		}
	}

	public void suspend() {
		for (int i = 0; i < beasts.length; i++) {
			beasts[i].stop();
		}
		for (int i = 0; i < warriors.length; i++) {
			warriors[i].stop();
		}
	}

	public boolean isCoordianteOnBoard(PVector coordinates) {
		if(coordinates.x>0 && coordinates.x<p.width)
			if(coordinates.y>0 && coordinates.y<p.height)
				return true;
		return false;
	}
}
