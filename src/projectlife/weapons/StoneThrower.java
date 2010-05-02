package projectlife.weapons;

import javax.swing.text.html.HTMLDocument.HTMLReader.SpecialAction;

import projectlife.*;
import processing.core.*;

public class StoneThrower extends Weapon {

	public class StoneThrowerBullet extends Bullet {

		public StoneThrowerBullet(Main applet, PVector position, String img,
				float angle, int color, float radius, float health,
				float maxSpeed, Weapon weapon, float weight) {
			super(applet, position, img, angle, color, radius, health,
					maxSpeed, weapon, weight);
			radius = caliber;
		}

		@Override
		public boolean checkPosition() {
			return p.level.isCoordianteOnBoard(this.location);
		}

		@Override
		public void harmTarget(Harmable target, float distance) {
			target.harm(this.damage);
		}

		@Override
		public void updateLocation() {
			velocity.add(acceleration);
			velocity.limit(maxSpeed);
			location.add(velocity);
		}

		// update location.
		// if(checkposition)
		// if(collide()!=-1)
		// kill();
		// else deleteMe;
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
		public int collide()  {
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
		this.bullets = (Bullet[]) PApplet.append(this.bullets, bullet);
		currentRackSize--;
		lastShotTime = p.millis();
	}
	
	public StoneThrower(Main applet, MovingObject owner) {
		super(applet,owner);
	}
}
