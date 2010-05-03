package projectlife.weapons;

import projectlife.*;
import processing.core.*;
import processing.xml.XMLElement;

public class StoneThrower extends Weapon {

	public class StoneThrowerBullet extends Bullet {

		public StoneThrowerBullet(Main applet, PVector position, String img,
				float angle, int color, float radius, float health,
				float maxSpeed, Weapon weapon, float weight) {
			super(applet, position, img, angle, color, radius, health,
					maxSpeed, weapon, weight);
			radius = caliber;
			range = 30;
			acceleration = new PVector(speed * p.cos(angle), maxSpeed
					* p.sin(angle));
		}

		@Override
		public boolean checkPosition() {
			return p.level.isCoordianteOnBoard(this.location);
		}

		@Override
		public void harmTarget(Harmable target, float distance) {
			target.harm(this.damage*(1-PApplet.map(distance, 0, range, 0, 1)));
		}

		@Override
		public void updateLocation() {
			velocity.add(acceleration);
			velocity.limit(maxSpeed);
			location.add(velocity);
		}

		@Override
		public void move() {
			if (!stopped) {
				updateLocation();

				if (checkPosition()) {
					if (this.collide() != -1) {
						kill();
					}
				} else {
					visible = false;
				}
			}
		}

		@Override
		public int collide() {
			for (int i = 0; i < weapon.targets.length; ++i) {
				if (this.location.dist(weapon.targets[i].getLocation()) < this.caliber
						+ weapon.targets[i].getRadius()) {
					return i;
				}
			}
			return -1;
		}

	}

	@Override
	public void generateBullet(float targetX, float targetY, float startX,
			float startY, float angle) {
		StoneThrowerBullet bullet = new StoneThrowerBullet(p, new PVector(
				startX, startY), "sdf.sdf", angle + p.random(-jitter, jitter),
				0xFFFFFFFF, caliber, 60, this.bulletSpeed, this, this.weight
						/ rackSize);
		bullet.sprite = new Animation(this.bulletAnimation);

		this.bullets = (Bullet[]) PApplet.append(this.bullets, bullet);
		currentRackSize--;
		lastShotTime = p.millis();
	}

	public StoneThrower(Main applet, MovingObject owner) {
		super(applet, owner);

		this.damage = 40;
		this.jitter = 0.1f;
		this.weight = 10;
		this.bulletSpeed = 10;
		this.rackSize = 3;
		this.reloadTime = 3000;
		this.timeBetweenShots = 200;
		this.caliber = 10;
		this.range = 100;

		this.bulletAnimation = new Animation(
				new XMLElement(
						"<weapon name=\"StoneThrower\" damage=\"80\" radius=\"40\" speed=\"100\" jitter=\"0\" rackSize=\"1\" reloadTime=\"0\" timeBetweenShoots=\"0\" image=\"images/weapons/st\"><sprite image=\"images/weapons/st/1.png\" time=\"100\" /><sprite image=\"images/weapons/st/2.png\" time=\"100\" /><sprite image=\"images/weapons/st/3.png\" time=\"100\" /></weapon>"),applet);
	}
}
