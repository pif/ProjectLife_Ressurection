package projectlife;

import java.util.ArrayList;

import processing.core.*;
import processing.xml.XMLElement;
import projectlife.weapons.Pan;
import projectlife.weapons.StoneThrower;
import projectlife.weapons.Teeth;

/**
*/
public class Beast extends MovingObject implements Harmable, Shooter {

	public static int spotDistance = 100;

	public int steps;
	public int currentTarget;
	public PVector[] targets;
	public boolean followTargets;

	
	public int exp;
	public static Beast factoryXML(Main applet, XMLElement prefs, Level level) {
		Animation beastAnim = new Animation(prefs.getChild("animation"), applet);

		Beast beast = Beast.factory(applet, new PVector(), "beast.png", 0, 0, 32,
				prefs.getFloatAttribute("health"), applet.random(prefs.getFloatAttribute("minSpeed"), prefs
						.getFloatAttribute("maxSpeed")), new Teeth(applet,
						null, applet.availableWeapons.get(prefs
								.getAttribute("weapon"))),
				level.warriors[0].location, level);

		// <Six health="160" accuracy="1" reload="0.7" weapon="Teeth"
		// minSpeed="3" maxSpeed="8">
		beast.accuracy = prefs.getFloatAttribute("accuracy");
		beast.power = prefs.getFloatAttribute("power");
		beast.reloadTime = prefs.getFloatAttribute("reload");
		
		beast.exp = prefs.getIntAttribute("exp");
		
		beast.sprite = beastAnim;

		if (beast.sprite.sprites[0] != null) {
			beast.radius = beast.sprite.sprites[0].image.height;
		} 
//		else if (beast.picture != null) {
//			beast.radius = beast.picture.width;
//		}

		return beast;
	}

	// /outdated
	public static Beast factory(Main applet, PVector position, String img,
			float angle, int color, float radius, float health, float maxSpeed,
			Weapon weapon, PVector target, Level level) {
		Beast beast = new Beast(applet, position, img, angle, color, radius,
				health, maxSpeed, weapon, target);

		beast.weapon.owner = beast;
		beast.weapon.targets = level.warriors;
		return beast;
	}

	public Beast(Main applet, PVector position, String img, float angle,
			int color, float radius, float health, float maxSpeed,
			Weapon weapon, PVector target) {
		super(applet, position, img, angle, color, radius, health, maxSpeed,
				weapon, target);
		// code, that chooses where to put that bastard
		int distFromBorder = 50;
		location = new PVector(p.random(p.width), p.random(p.height));
		if ((int) (p.random(2)) == 1) {
			if ((int) (p.random(2)) == 1)
				location.y = p.height + distFromBorder;
			else
				location.y = -distFromBorder;
		} else {
			if ((int) (p.random(2)) == 1)
				location.x = p.width + distFromBorder;
			else
				location.x = -distFromBorder;
		}

		steps = (int) (applet.random(2, 5));
		calcSteps(steps);
		currentTarget = 0;
		followTargets = true;

		this.power = 1;
	}

	// optimise beasts. don't call turn() every frame better calculate
	// AI: curveTightness() - the way monsters go to target
	public void calcSteps(int stepCount) {
		if (stepCount <= 0) {
			followTargets = false;
		} else {
			targets = new PVector[stepCount];
			PVector p1 = new PVector(p.random(-p.width, p.width), p.random(
					-p.height, p.height));
			PVector p2 = new PVector(p.random(-p.width, p.width), p.random(
					-p.height, p.height));
			PVector s = this.location;
			PVector f = p.level.warriors[0].location;
			p.curveTightness(p.random(-1, 2));
			for (int i = 0; i < stepCount; ++i) {
				float t = i / (float) stepCount;
				float x = p.curvePoint(p1.x, s.x, f.x, p2.x, t);
				float y = p.curvePoint(p1.y, s.y, f.y, p2.y, t);
				targets[i] = new PVector(x, y);

				if (p.debug) {
					p.curve(p1.x, p1.y, s.x, s.y, f.x, f.y, p2.x, p2.y);
				}
			}
		}
	}

	public boolean display() {
		weapon.displayBullets();

		return super.display();
	}

	public void move() {
		super.move();

		this.shoot(weapon.targets[0].getLocation());

		acceleration = new PVector(PApplet.cos(angle), PApplet.sin(angle));

		if (location.dist(p.level.warriors[0].location) < (this.radius
				+ p.level.warriors[0].radius + spotDistance)) {
			followTargets = false;
		} else {
			followTargets = true;
		}

		if (followTargets) {
			if (location.dist(target) < this.radius + spotDistance / 2) {
				currentTarget++;
				if (currentTarget >= steps) {
					calcSteps(steps);
					currentTarget = 0;
				}
			}
			target = targets[currentTarget];
		} else {
			target = p.level.warriors[0].location;
		}

		if (p.debug) {
			if (followTargets) {
				p.ellipseMode(PConstants.CENTER);
				for (int i = 0; i < steps; ++i)
					p.ellipse(targets[i].x, targets[i].y, 32, 32);
			}
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

	@Override
	public void shoot(PVector target) {
		weapon.shoot(target.x, target.y, location.x, location.y, angle);
	}
}
