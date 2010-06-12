package projectlife;

import java.util.ArrayList;
import java.util.HashMap;

import processing.core.*;
import processing.xml.XMLElement;
import projectlife.weapons.StoneThrower;
import projectlife.weapons.Teeth;

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
	public WarriorOverlay warriorOverlay;
	public EventManager eventManager;
	public Tweaker tweaker;

	public String[] availableBonuses;
	public HashMap<String, XMLElement> availableBeasts;
	
	public int killed;
	public long exp;

	public Level(String xmlPath, Main applet) {

		super(applet);
		XMLElement levelFile = new XMLElement(applet, xmlPath);
		beasts = new Beast[0];
		warriors = new Warrior[1];

		// warriors[0] = Warrior.factory2(applet, new PVector(applet.width / 2,
		// applet.height / 2), "warrior.png", 0, 0, 0, 100, 8,
		// new StoneThrower(applet, warriors[0], applet.availableWeapons
		// .get("StoneThrower"))/*
		// * , 20, 10, 50, (float) 0.1, 30,
		// * 500, 100)
		// */, new PVector(), 0, this);

		ground = new Ground(levelFile.getChild("background").getContent(),
				true, applet);

		bonuses = new Bonus[0];
		overlays = new Overlay[0];

		tweaker = new Tweaker(applet);
		availableBonuses = levelFile.getChild("bonuses").listChildren();

		availableBeasts = new HashMap<String, XMLElement>();
		XMLElement bbeast = levelFile.getChild("beasts");
		for (int i = 0; i < bbeast.getChildCount(); ++i) {
			availableBeasts.put(bbeast.getChild(i).getName(), bbeast
					.getChild(i));
		}

		warriorOverlay = new WarriorOverlay(p);
		eventManager = new EventManager(applet, levelFile.getChild("scenery"));
		passed = false;
		// DONE bullet problem. if they'r too quick. the overjump bastards.
		// that's ok.
	}

	public void setWarrior(String warrior) {
		// here we set our warrior for the level;
		warriors[0] = Warrior
				.factory(p, p.availableWarriors.get(warrior), this);
	}

	public void display() {
		for (int i = 0; i < bonuses.length; ++i) {
			if (bonuses[i].warriorGotMe()) {
			}
		}
		for (int i = 0; i < warriors.length; ++i) {
			tweaker.update(warriors[i]);
		}

		// draw level ground
		ground.drawBackground();

		for (int i = 0; i < bonuses.length; i++) {
			if (bonuses[i].visible) {
				bonuses[i].display();
			} else {
				bonuses[i] = bonuses[bonuses.length - 1];
				bonuses = (Bonus[]) (PApplet.shorten(bonuses));
				--i;
			}

		}		
		
		for (int i = 0; i < beasts.length; i++) {
			if (beasts[i].visible) {
				beasts[i].display();
			} else {
				p.level.addBonus(beasts[i].location);
				beasts[i] = beasts[beasts.length - 1];
				exp+=beasts[i].exp;
				beasts = (Beast[]) (PApplet.shorten(beasts));

				for (int q = 0; q < warriors.length; ++q) {
					warriors[q].updateTargets(this);
				}
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

		warriorOverlay.display();
		
		eventManager.act();
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
		p.println(command);
		bonuses = (Bonus[]) p.append(bonuses,
				new Bonus(p, new PVector(pos.x, pos.y), "images/bonuses/"
						+ command + ".png", 0, 0, 32, command));

	}

	public void addBonus(PVector pos, String c) {
		String command = c;
		p.println(command);
		bonuses = (Bonus[]) p.append(bonuses,
				new Bonus(p, new PVector(pos.x, pos.y), "images/bonuses/"
						+ command + ".png", 0, 0, 32, command));

	}
	
	public Beast addBeast() {
		String name = "";
		String[] names = (String[]) availableBeasts.keySet().toArray(
				new String[availableBeasts.keySet().size()]);
		return addBeast(names[(int) p.random(names.length)]);
	}
	
	public Beast addBeast(String name) {
		if (availableBeasts.containsKey(name)) {
			Beast b = Beast.factoryXML(p, availableBeasts.get(name), this);
			beasts = (Beast[]) PApplet.append(beasts, b);
			
			
			for (int q = 0; q < warriors.length; ++q) {
				warriors[q].updateTargets(this);
			}
			
			return b;
		} else {
			Beast b = Beast.factory(p,
					new PVector(), "beast.png", 0, 0, 32, 100, p.random(2, 8),
					new Teeth(p, null, p.availableWeapons.get("Teeth")),
					warriors[0].location, p.level);
			beasts = (Beast[]) PApplet.append(beasts, b);

			for (int q = 0; q < warriors.length; ++q) {
				warriors[q].updateTargets(this);
			}
			
			return b;
		}
	}

	public Beast addBeast(String name, float x, float y) {
		if (availableBeasts.containsKey(name)) {
			
			Beast b = addBeast(name);
//			(p, new PVector(x, y), "beast.png", 0, 0,
//					32, 100, p.random(2, 8), new Teeth(p, null,
//							p.availableWeapons.get("Teeth")),
//					warriors[0].location, p.level);

			b.location.set(x, y, 0);
			return b;
		} else {
			Beast b = addBeast();
			return b;
		}
	}
}
