package projectlife;

import java.util.ArrayList;
import java.util.HashMap;

import processing.core.*;
import processing.xml.XMLElement;
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

	public Tweaker tweaker;

	public String[] availableBonuses;
	public HashMap<String, XMLElement> availableBeasts;

	public Level(String xmlPath, Main applet) {

		super(applet);
		beasts = new Beast[0];
		warriors = new Warrior[1];

		warriors[0] = Warrior.factory2(applet, new PVector(applet.width / 2,
				applet.height / 2), "warrior.png", 0, 0, 0, 100, 8,
				new StoneThrower(applet, warriors[0], applet.availableWeapons
						.get("StoneThrower"))/*
											 * , 20, 10, 50, (float) 0.1, 30,
											 * 500, 100)
											 */, new PVector(), 0, this);

		ground = new Ground("1.png", true, applet);

		bonuses = new Bonus[0];
		overlays = new Overlay[0];

		tweaker = new Tweaker(applet);
		availableBonuses = new String[0];
		availableBeasts = new HashMap<String, XMLElement>();
		passed = false;
		// DONE bullet problem. if they'r too quick. the overjump bastards.
		// that's ok.
	}

	public void setWarrior(String warrior) {
		warriors[0] = Warrior
				.factory(p, p.availableWarriors.get(warrior), this);
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

				for (int q = 0; q < warriors.length; ++q) {
					warriors[q].updateTargets(this);
				}
				--i;
			}

		}

		for (int i = 0; i < bonuses.length; i++) {
			if (bonuses[i].visible) {
				bonuses[i].display();
			} else {
				bonuses[i] = bonuses[bonuses.length - 1];
				bonuses = (Bonus[]) (PApplet.shorten(bonuses));
				--i;
			}

		}
		for (int i = 0; i < warriors.length; i++) {
			if (warriors[i].visible) {
				warriors[i].display();
			} else {
				warriors[i] = warriors[warriors.length - 1];
				warriors = (Warrior[]) (PApplet.shorten(warriors));
			}

		}

		if (warriors.length <= 0) {
			p.exit();
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
		if (coordinates.x > 0 && coordinates.x < p.width)
			if (coordinates.y > 0 && coordinates.y < p.height)
				return true;
		return false;
	}

	public boolean won() {
		// TODO Auto-generated method stub
		return false;
	}

	public void addBonus(PVector pos) {
		String command = availableBonuses[(int) p
				.random(availableBonuses.length)];
		bonuses = (Bonus[]) p.append(bonuses, new Bonus(p, pos, p.dataPath
				+ "images/bonuses/" + command + ".png", 0, 0, 32, command));

	}
}
