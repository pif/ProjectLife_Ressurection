package projectlife.weapons;

import processing.core.PApplet;
import processing.core.PVector;
import projectlife.Bullet;
import projectlife.Harmable;
import projectlife.Main;
import projectlife.MovingObject;
import projectlife.Weapon;
/**
 * simple weapon for beasts
 * @author Ostap
 *
 */
public class Teeth extends Weapon {
	public class TeethBullet extends Bullet {

		public TeethBullet(Main applet, PVector position, String img,
				float angle, int color, float radius, float health,
				float maxSpeed, Weapon weapon, float weight) {
			super(applet, position, img, angle, color, radius, health, maxSpeed, weapon,
					weight);
			
			caliber = weapon.owner.radius;
			radius = caliber+10;
		}

		@Override
		public boolean checkPosition() {
			return (PVector.sub(this.location,weapon.owner.location).mag())<weapon.owner.radius+caliber;
		}

		@Override
		public void harmTarget(Harmable target, float distance) {
			target.harm(this.damage);
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
		public void updateLocation() {
			location=weapon.owner.location;		
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
		//do nothing. we hav eone bullet. always...		
	}
	
	public Teeth(Main applet, MovingObject owner) {
		super(applet,owner);
		caliber=owner.radius;
		TeethBullet bullet = new TeethBullet(applet, owner.location, "sdf.sdf", 0, 0, caliber, 9999, 0, this, 1);
		bullets = (Bullet[]) PApplet.append(bullets, bullet); 
	}
}
