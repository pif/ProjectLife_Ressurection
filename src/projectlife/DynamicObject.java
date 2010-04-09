package projectlife;
import processing.core.*;

/**
*/
public class DynamicObject extends StaticObject {

	public DynamicObject(Main applet, PVector position, String img,
			float angle, int color, float radius, float health, float maxSpeed,
			Weapon weapon, PVector target) {
		super(applet, position, img, angle, color, radius, target);

		this.maxSpeed = maxSpeed;
		this.velocity = new PVector(0, 0);
		this.acceleration = new PVector(0, 0);
		this.angle = angle;
		this.health = health;
		this.weapon = weapon;
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

	/**
	 * execurtes a list of actions, which move object accordingly to the list of
	 * predefined rules
	 * 
	 * @param Return
	 */
	public void move() {
		acceleration.normalize();
		velocity.add(acceleration);
		velocity.limit(maxSpeed);
		location.add(velocity);
		turn();
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
		
		//TODO debug mode
		p.rect(location.x, location.y-64, health, 10);
		p.ellipse(location.x, location.y, radius, radius);
		return super.display();
	}

	public boolean gotTarget(float targetRadius) {
		return p.dist(location.x, location.y, target.x, target.y) < (this.radius + targetRadius);
	}

	public boolean checkHealth() {
		if (health <= 0) {
			visible = false;
		}
		return super.check();
	}
}
