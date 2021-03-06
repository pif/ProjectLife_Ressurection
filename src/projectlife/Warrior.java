package projectlife;

import java.util.ArrayList;

import processing.core.*;
import processing.xml.XMLElement;
import projectlife.weapons.*;

/**
*/
public class Warrior extends MovingObject implements Harmable, Shooter {

	public float maxHealth;
	public int experience;
	public ArrayList<Weapon> weapons;
	public int currentWeapon;

	public static Warrior factory(Main applet, XMLElement prefs, Level level) {
		Animation waranim = new Animation(prefs, applet);

		Warrior warrior = new Warrior(applet, new PVector(applet.width / 2,
				applet.height / 2), "", 0, 0, 0, prefs
				.getFloatAttribute("health"), prefs.getFloatAttribute("speed"),
				new StoneThrower(applet, level.warriors[0],
						applet.availableWeapons.get(prefs
								.getStringAttribute("weapon"))), new PVector(0,
						0), 0);
		warrior.maxHealth = warrior.health;
		
		warrior.accuracy = prefs.getFloatAttribute("accuracy");
		warrior.power = prefs.getFloatAttribute("power");
		warrior.reloadTime = prefs.getFloatAttribute("reload");

		warrior.sprite = waranim;

		if (warrior.sprite.sprites[0] != null) {
			warrior.radius = warrior.sprite.sprites[0].image.height;
		}

		warrior.currentWeapon = 0;
		warrior.weapons = new ArrayList<Weapon>();
		warrior.weapons.add(new StoneThrower(applet, warrior,
				applet.availableWeapons.get("StoneThrower")));
		warrior.weapons.add(new Pan(applet, warrior,
				applet.availableWeapons.get("Pan")));
		
		warrior.setPrevWeapon();

		warrior.updateTargets(level);

		return warrior;
	}

	public static Warrior factory2(Main applet, PVector position, String img,
			float angle, int color, float radius, float health, float maxSpeed,
			Weapon weapon, PVector target, int experience, Level level) {
		Warrior warrior = new Warrior(applet, position, img, angle, color,
				radius, health, maxSpeed, weapon, target, experience);

		warrior.currentWeapon = 0;
		warrior.weapons = new ArrayList<Weapon>();
		warrior.weapons.add(new StoneThrower(applet, warrior,
				applet.availableWeapons.get("StoneThrower")));
		warrior.weapons.add(new Minigun(applet, warrior,
				applet.availableWeapons.get("SimpsonsMinigun")));
		warrior.weapons.add(new MadShotgun(applet, warrior,
				applet.availableWeapons.get("MadShotgun")));
		warrior.weapons.add(new BullsEye(applet, warrior,
				applet.availableWeapons.get("BullsEye")));
		warrior.setPrevWeapon();

		warrior.updateTargets(level);

		return warrior;
	}

	public void setNextWeapon() {
		currentWeapon++;
		if (currentWeapon >= weapons.size()) {
			currentWeapon = weapons.size() - 1;
		}
		weapon = weapons.get(currentWeapon);
	}

	public void setPrevWeapon() {
		currentWeapon--;
		if (currentWeapon < 0) {
			currentWeapon = 0;
		}
		weapon = weapons.get(currentWeapon);
	}

	public void updateTargets(Level level) {
		for (int i = 0; i < this.weapons.size(); ++i) {
			this.weapons.get(i).targets = level.beasts;
		}
	}

	public Warrior(Main applet, PVector position, String img, float angle,
			int color, float radius, float health, float maxSpeed,
			Weapon weapon, PVector target, int experience) {
		super(applet, position, img, angle, color, radius, health, maxSpeed,
				weapon, target);
		this.experience = experience;
		// DONE warrior sprite. you choose warrior, and it's populated with the
		// corresponding sprites
		//
		// for (int i = 1; i < 5; i++)
		// this.sprite.addSprite(p.loadImage(p.dataPath + "images/warrior/"
		// + i + ".png"), 50);
	}

	public void move() {
		super.move();

		location.x = PApplet.constrain(location.x, 0, p.width);
		location.y = PApplet.constrain(location.y, 0, p.height);

		target = new PVector(p.mouseX, p.mouseY);
		
		if(this.health>this.maxHealth){
			this.health=this.maxHealth;
		}
		
		
	}

	public void shoot(PVector target) {
		weapon.shoot(target.x, target.y, location.x, location.y, angle);
	}

	public boolean display() {
		for (int i = 0; i < weapons.size(); ++i) {
			weapons.get(i).displayBullets();
		}

		p.pushMatrix();
		p.fill(255, 100);
		p.translate(location.x, location.y);
		p.rotate(PApplet.atan2(velocity.y, velocity.x) + PConstants.HALF_PI);
		p.triangle(-10, -50, 0, -70, 10, -50);
		p.rect(0, -45, 10, 10);
		p.popMatrix();

		return super.display();
	}

	public void stop() {
		super.stop();
		for (int i = 0; i < weapon.bullets.length; i++) {
			weapon.bullets[i].stop();
		}
	}

	public void letGo() {
		super.letGo();
		for (int i = 0; i < weapon.bullets.length; i++) {
			weapon.bullets[i].letGo();
		}
	}

	@Override
	public void harm(float damage) {
		this.health -= damage;
	}

	@Override
	public PVector getLocation() {
		return this.location;
	}

	@Override
	public float getRadius() {
		return this.radius;
	}
}
