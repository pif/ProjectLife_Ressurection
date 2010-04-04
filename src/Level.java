import processing.core.*;

/**
 * 
 * @author Ostap Level is used to manage levels of game. -- loads data from xml
 *         file, -- creates all the units on field. -- displays all units --
 *         controls all units
 */
public class Level extends MyObject {

	public Warrior warrior;
	public Ground ground;

	public Beast[] beasts;
	public Bonus[] bonuses;
	public Overlay[] overlays;

	public Level(String xmlPath, Main applet) {
		// TODO Xml parser
		super(applet);

		warrior = new Warrior(applet, new PVector(applet.width / 2,
				applet.height / 2), "", 0, 0, 0, 100, 4, new Weapon(applet, 20, 10, 10, (float) 0.1), new PVector(), 0);

		ground = new Ground("1.png", true, applet);

		beasts = new Beast[0];
		bonuses = new Bonus[0];
		overlays = new Overlay[0];
	}

	public Level(Main applet) {
		// TODO Auto-generated constructor stub
		super(applet);
		warrior = new Warrior(applet, new PVector(applet.width / 2,
				applet.height / 2), "warrior.png", 0, 0, 0, 100, 8, new Weapon(applet, 20, 10, 10, (float) 0.1), new PVector(), 0);

		ground = new Ground("1.png", true, applet);

		beasts = new Beast[0];
		bonuses = new Bonus[0];
		overlays = new Overlay[0];
	}

	public void display() {
		// draw level ground
		ground.drawBackground();

		for (int i = 0; i < beasts.length; i++) {
			beasts[i].display();
		}

		for (int i = 0; i < bonuses.length; i++) {
			bonuses[i].display();
		}

		warrior.display();

	}
}
