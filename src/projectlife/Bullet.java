package projectlife;
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

		for (int i = 0; i < zzz.length; i++) {
			zzz[i] = new PVector();
		}
	}

	public boolean display() {
		for (int i = zzz.length - 1; i != 0; i--) {
			p.fill(255, i * 5);
			p.ellipse(zzz[i].x, zzz[i].y, 16, 16);
		}
		return super.display();
	}

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

		for (int i = 0; i < zzz.length - 2; i++) {
			zzz[i] = zzz[i + 1];
		}
		zzz[zzz.length - 1] = location;
	}

	public PVector[] zzz = new PVector[50];

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
