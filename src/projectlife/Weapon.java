package projectlife;

import processing.core.*;

/**
*/
public class Weapon extends MyObject {

	public float damage;
	public float jitter;
	public float weight;
	public float bulletSpeed;
	public int rackSize;
	public int reloadTime;
	public int timeBetweenShots;
	public float caliber;	
	
	public Bullet[] bullets;
	public int currentRackSize;
	
	public boolean canShoot;	
	public int lastShotTime;
	private int reloadStartTime;

	
	/**
	 * 
	 * @param Return
	 * @return reloads weapon's rack with new bullets. there's no bullet limit
	 *         in game.
	 */
	public boolean reload() {
		// reload weapon
		canShoot = false;
		currentRackSize = rackSize;
		reloadStartTime = p.millis();
		return canShoot;
	}

	// classes should implement this method. it is called every time we can
	// shoot...
	public void generateBullet(float targetX, float targetY, float startX, float startY, float angle) {
		//TODO weapon bullet stub. every weapon should have it's own...i suppose
		Bullet b = new Bullet(p, new PVector(startX, startY), "sdf.sdf", angle
				+ p.random(-jitter, jitter), 0xFFFFFFFF, caliber, damage,
				bulletSpeed, this, new PVector(), new PVector(startX, startY));
		this.bullets = (Bullet[]) PApplet.append(this.bullets, b);
		currentRackSize--;
		lastShotTime = p.millis();
	}

	// shoot in position x,y with the angle=angle
	public void shoot(float targetX, float targetY, float shootPosX, float shootPosY, float angle) {
		if (canShoot) {
			if (p.millis() - lastShotTime >= timeBetweenShots) {
				if (currentRackSize > 0) {
					// you can shoot. you have bullets in rack.
					generateBullet(targetX, targetY, shootPosX, shootPosY, angle);
				} else {
					reload();
				}
			}
		} else {
			if (p.millis() - reloadStartTime >= reloadTime) {
				canShoot = true;
			}
		}
	}

	/**
	 * @param Return
	 *            checks if bullet can be on the board. if it is still needed.
	 *            if not so - deletes it.
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
	 *            calls every bullets' display method
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
		this.caliber = radius;
		this.bullets = new Bullet[0];
		this.bulletSpeed = speed;
		this.jitter = jitter;
		this.lastShotTime = 0;
		this.reloadTime = reloadTime;
		this.rackSize = rackSize;
		this.timeBetweenShots = timeBetweenShoots;

		this.currentRackSize = rackSize;
		this.canShoot = true;
	}
	
	public Weapon() {
	
	}
}
