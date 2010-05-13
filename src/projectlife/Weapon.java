package projectlife;

import processing.core.*;
import processing.xml.XMLElement;

/**
*/
public abstract class Weapon extends MyObject implements IShootable {

	public float damage;
	public float jitter;
	public float weight;
	public float bulletSpeed;
	public int rackSize;
	public int reloadTime;
	public int timeBetweenShots;
	public float range;

	public float caliber;
	public float bulletRange;

	public MovingObject owner;
	public Animation bulletAnimation;

	public Bullet[] bullets;
	public int currentRackSize;
	public Harmable[] targets;

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
	// TODO weapon bullet stub. every weapon should have it's own...i suppose
	// public void generateBullet(float targetX, float targetY, float startX,
	// float startY, float angle) {
	// }

	// shoot in position x,y with the angle=angle
	public void shoot(float targetX, float targetY, float shootPosX,
			float shootPosY, float angle) {
		if (canShoot) {
			if (p.millis() - lastShotTime >= timeBetweenShots) {
				if (currentRackSize > 0) {
					// you can shoot. you have bullets in rack.
					generateBullet(targetX, targetY, shootPosX, shootPosY,
							angle);
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
		for (int i = 0; i < bullets.length; ++i) {
			if (!bullets[i].checkHealth()) {
				bullets[i] = bullets[bullets.length - 1];
				bullets = (Bullet[]) (PApplet.shorten(bullets));
				--i;
				continue;
			}
			if (!bullets[i].checkPosition()) {
				bullets[i] = bullets[bullets.length - 1];
				bullets = (Bullet[]) (PApplet.shorten(bullets));
				--i;
				continue;
			}
			if (bullets[i].startPos.dist(bullets[i].location) >= this.range) {
				bullets[i].kill();
				bullets[i] = bullets[bullets.length - 1];
				bullets = (Bullet[]) (PApplet.shorten(bullets));
				--i;
				continue;
			}
		}
	}
	//FIXME bullets' damage=damage*owner.power
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

	// float damage, float radius, float speed,
	// float jitter, int rackSize, int reloadTime, int timeBetweenShoots)
	public Weapon(Main applet, MovingObject owner, XMLElement preferences) {
		super(applet);

		this.damage = preferences.getFloatAttribute("damage");
		this.jitter = preferences.getFloatAttribute("jitter");
		this.weight = preferences.getFloatAttribute("weight");
		this.bulletSpeed = preferences.getFloatAttribute("bulletSpeed");
		this.rackSize = preferences.getIntAttribute("rackSize");
		this.reloadTime = preferences.getIntAttribute("reloadTime");
		this.timeBetweenShots = preferences
				.getIntAttribute("timeBetweenShoots");
		this.caliber = preferences.getFloatAttribute("caliber");
		this.bullets = new Bullet[0];
		this.range = preferences.getFloatAttribute("range");
		this.bulletRange = preferences.getFloatAttribute("bulletRange");

		this.bulletAnimation = new Animation(preferences, applet);

		this.lastShotTime = 0;
		this.owner = owner;

		this.currentRackSize = rackSize;
		this.canShoot = true;

		// this.targets = (Harmable[]) (applet.level.beasts);
	}

	public void setTargets(Harmable[] targets) {
		this.targets = targets;
	}

	public Weapon() {

	}
}
