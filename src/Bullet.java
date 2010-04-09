import processing.core.*;

/**
*/
public class Bullet extends DynamicObject {

	public Bullet(Main applet, PVector position, String img, float angle,
			int color, float radius, float health, float maxSpeed,
			Weapon weapon, PVector target) {

		super(applet, position, img, angle, color, radius, health, maxSpeed,
				weapon, target);
		// TODO Auto-generated constructor stub
		acceleration = new PVector(maxSpeed * p.cos(angle), maxSpeed
				* p.sin(angle));
	}

	// public boolean display() {
	// return super.display();
	// }

	public void move() {
		kill();
		// acceleration.normalize();
		velocity.add(acceleration);
		velocity.limit(maxSpeed);
		location.add(velocity);

		if (location.x < 0 || location.x > p.width || location.y < 0
				|| location.y > p.height) {
			visible = false;
		}
	}

	public int kill() {
		for (int i = 0; i < p.level.beasts.length; i++) {
			if (location.dist(p.level.beasts[i].location) < weapon.radius) {
				p.level.beasts[i].health -= this.health;
				p.level.ground.addBlood(new PVector(this.location.x,
						this.location.y), 0x88FF0000);
				return i;
			}
		}
		return -1;
	}
}
