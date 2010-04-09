package projectlife;
import processing.core.*;

/**
*/
public class Weapon extends MyObject {
	/**
*/
	public float damage;
	/**
*/
	public float speed;
	/**
*/
	public float radius;
	/**
*/
	public float jitter;
	/**
*/
	public int rackSize;
	/**
*/
	public Bullet[] bullets;

	public int lastShot;

	/**
	 * 
	 * @param Return
	 * @return
	 */

	public boolean reload() {
		// TODO reload weapon
		return false;
	}

	/**
	 * @param Return
	 */
	public void updateBullets() {
		for (int i = 0; i < bullets.length; ++i)
			if (!bullets[i].checkHealth()) {
				bullets[i] = bullets[bullets.length - 1];
				bullets = (Bullet[]) (p.shorten(bullets));
			}
	}

	/**
	 * @param Return
	 */
	public void displayBullets() {
		updateBullets();
		for (int i = 0; i < bullets.length; i++) {
			bullets[i].display();
		}
	}

	public Weapon(Main applet, float damage, float radius, float speed,
			float jitter) {
		super(applet);

		this.damage = damage;
		this.radius = radius;
		this.bullets = new Bullet[0];
		this.speed = speed;
		this.jitter = jitter;
		this.lastShot = 0;
	}

	public void shoot(int x, int y, float angle) {
		if (p.millis() - lastShot > 100) {
			// TODO add new bullet to array
			Bullet b = new Bullet(p, new PVector(x, y), "sdf.sdf", angle
					+ p.random(-jitter, jitter), 0xFFFFFFFF, radius, damage, speed,
					this, new PVector());
			this.bullets = (Bullet[]) p.append(this.bullets, b);
			lastShot = p.millis();
		}
	}
}
