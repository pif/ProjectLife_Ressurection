package projectlife.weapons;

import projectlife.*;
import processing.core.*;
import processing.xml.XMLElement;

public class Minigun extends Weapon {

	public class MinigunBullet extends Bullet {

		public MinigunBullet(Main applet, PVector position, String img,
				float angle, int color, float radius, float health,
				float maxSpeed, Weapon weapon, float weight,PVector startPos) {
			super(applet, position, img, angle, color, radius, health,
					maxSpeed, weapon, weight,startPos);
			
			acceleration = new PVector(speed* PApplet.cos(angle), maxSpeed
					* PApplet.sin(angle));
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
				if (this.location.dist(weapon.targets[i].getLocation()) < this.caliber/2
						+ weapon.targets[i].getRadius()/2) {
					return i;
				}
			}
			return -1;
		}

	}

	@Override
	public void generateBullet(float targetX, float targetY, float startX,
			float startY, float angle) {
		MinigunBullet bullet = new MinigunBullet(p, new PVector(
				startX, startY), "sdf.sdf", angle + p.random(-jitter, jitter),
				0xFFFFFFFF, caliber, this.damage, this.bulletSpeed, this, this.weight
						/ rackSize,new PVector(startX, startY));		
		
		this.bullets = (Bullet[]) PApplet.append(this.bullets, bullet);
		currentRackSize--;
		lastShotTime = p.millis();
	}
	
	public Minigun(Main applet, MovingObject owner, XMLElement prefs) {
		super(applet,owner, prefs);
	}
}
