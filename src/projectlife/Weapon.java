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
	public float bulletSpeed;
	/**
*/
	public float radius;
	/**
*/
	public float jitter;
	/**
*/
	public int rackSize;
	public int currentRackSize;
	/**
*/
	public Bullet[] bullets;

	public int lastShot;
	public boolean canShoot;
	public int reloadTime;
	public int timeBetweenShoots;

	private int reloadStart;

	/**
	 * 
	 * @param Return
	 * @return
	 */

	public boolean reload() {
		// reload weapon
		canShoot = false;
		currentRackSize = rackSize;
		reloadStart = p.millis();
		return canShoot;
	}

	public void shoot(int x, int y, float angle) {
		if (canShoot) {
			if (p.millis() - lastShot >= timeBetweenShoots) {
				if (currentRackSize > 0) {
					// you can shoot. you have bullets in rack.
					Bullet b = new Bullet(p, new PVector(x, y), "sdf.sdf",
							angle + p.random(-jitter, jitter), 0xFFFFFFFF,
							radius, damage, bulletSpeed, this, new PVector());
					this.bullets = (Bullet[]) PApplet.append(this.bullets, b);
					currentRackSize--;
					lastShot = p.millis();
				} else {
					reload();
				}
			}
		} else {
			if (p.millis() - reloadStart >= reloadTime) {
				canShoot = true;
			}
		}
	}

	/**
	 * @param Return
	 */
	public void updateBullets() {
		for (int i = 0; i < bullets.length; ++i)
			if (!bullets[i].checkHealth()) {
				bullets[i] = bullets[bullets.length - 1];
				bullets = (Bullet[]) (PApplet.shorten(bullets));
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
			float jitter, int rackSize, int reloadTime, int timeBetweenShoots) {
		super(applet);

		this.damage = damage;
		this.radius = radius;
		this.bullets = new Bullet[0];
		this.bulletSpeed = speed;
		this.jitter = jitter;
		this.lastShot = 0;
		this.reloadTime = reloadTime;
		this.rackSize = rackSize;
		this.timeBetweenShoots = timeBetweenShoots;

		this.currentRackSize = rackSize;
		this.canShoot = true;
	}

	public Weapon() {
		super();
	}

}
