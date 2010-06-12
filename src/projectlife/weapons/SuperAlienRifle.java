package projectlife.weapons;

import projectlife.*;
import processing.core.*;
import processing.xml.XMLElement;

public class SuperAlienRifle extends Weapon {

	public class SuperAlienRifleBullet extends Bullet {

		private long start;
		long ttl;

		public SuperAlienRifleBullet(Main applet, PVector position, String img,
				float angle, int color, float radius, float health,
				float maxSpeed, Weapon weapon, float weight, PVector startPos) {
			super(applet, position, img, angle, color, radius, health,
					maxSpeed, weapon, weight, startPos);
			start = applet.millis();
			
			ttl = 0;
			for (int i = 0; i < sprite.sprites.length; ++i) {
				ttl += sprite.sprites[i].getTime();
			}
		}

		@Override
		public boolean checkPosition() {
			return p.level.isCoordianteOnBoard(this.location);
		}

		@Override
		public void harmTarget(Harmable target, float distance) {
			target.harm(this.damage
					* p.map(distance, 0, this.range, -1f, -0.5f) * (-1f));
		}

		@Override
		public void updateLocation() {
			// velocity.add(acceleration);
			// velocity.limit(maxSpeed);
			// location.add(velocity);
		}

		@Override
		public void move() {
			if (!stopped) {
				updateLocation();

				if (checkPosition()) {
					if ((this.collide() != -1) || ((p.millis() - start) > ttl)) {
						kill();
						visible = false;
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

		public boolean display() {
			p.pushStyle();
			p.noFill();
			p.stroke(0);
			p.strokeWeight(5);
			p.ellipse(this.location.x, this.location.y, this.range * 2,
					this.range * 2);
			p.ellipse(this.location.x, this.location.y, this.caliber* 2,
					this.caliber* 2);			
			p.popStyle();
			return super.display();
		}
	}

	@Override
	public void generateBullet(float targetX, float targetY, float startX,
			float startY, float angle) {
		SuperAlienRifleBullet bullet = new SuperAlienRifleBullet(p,
				new PVector(startX, startY), "sdf.sdf", angle
						+ p.random(-jitter, jitter), 0xFFFFFFFF, caliber, 60,
				this.bulletSpeed, this, this.weight / rackSize, new PVector(
						startX, startY));
		bullet.sprite = new Animation(this.bulletAnimation);
		this.bullets = (Bullet[]) PApplet.append(this.bullets, bullet);
		currentRackSize--;
		lastShotTime = p.millis();
	}

	public SuperAlienRifle(Main applet, MovingObject owner, XMLElement prefs) {
		super(applet, owner, prefs);
	}
}
