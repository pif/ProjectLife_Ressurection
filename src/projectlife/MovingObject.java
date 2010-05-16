package projectlife;

import processing.core.*;

/**
*/
public class MovingObject extends StandingObject {

	public MovingObject(Main applet, PVector position, String img, float angle,
			int color, float radius, float health, float maxSpeed,
			Weapon weapon, PVector target) {
		super(applet, position, img, angle, color, radius, target);

		this.maxSpeed = maxSpeed;
		this.velocity = new PVector(0, 0);
		this.acceleration = new PVector(0, 0);
		this.angle = angle;
		this.health = health;
		this.weapon = weapon;
		this.stopped = false;
	}

	/**
*/
	public PVector velocity;
	/**
*/
	public PVector acceleration;
	/**
*/
	public float maxSpeed;
	/**
*/
	public float health;
	/**
*/
	public Weapon weapon;

	public float power;
	public float accuracy;
	public float reloadTime;

	/**
	 * execurtes a list of actions, which move object accordingly to the list of
	 * predefined rules
	 * 
	 * @param Return
	 */
	public void move() {
		if (!stopped) {
			acceleration.normalize();
			velocity.add(acceleration);
			velocity.limit(maxSpeed);
			location.add(velocity);
			turn();
		}
	}

	public boolean display() {
		if (checkHealth()) {
			move();
		}
		// p.pushMatrix();
		// p.translate(location.x, location.y);
		// p.rotate(p.atan2(velocity.y, velocity.x)+p.HALF_PI);
		// p.triangle(-10, -50, 0, -70, 10, -50);
		// p.rectMode(p.CORNERS);
		// p.rect(-5, -40, 5, -50);
		// p.popMatrix();

		if (p.level.tweaker.seeHealth.isOn()) {
			if (!this.getClass().getName().contains("Bullet")) {
				p.pushStyle();
				p.fill(0,255,0);
				p.rect(location.x, location.y + 64, health, 4);
				p.popStyle();
			}
		}
		if (p.debug) {
			p.rect(location.x, location.y +20, health, 10);
			p.text(health, location.x - 20, location.y - radius - 20);
		}
		return super.display();
	}

	public boolean gotTarget(float targetRadius) {
		return PApplet.dist(location.x, location.y, target.x, target.y) < (this.radius + targetRadius);
	}

	public boolean checkHealth() {
		if (health <= 0) {
			visible = false;
		}
		return super.check();
	}
}
