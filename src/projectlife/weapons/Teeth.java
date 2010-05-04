package projectlife.weapons;

import processing.core.PApplet;
import processing.core.PVector;
import processing.xml.XMLElement;
import projectlife.Animation;
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
			range = caliber+10;
			used = false;
		}
		private boolean used;
		@Override
		public boolean checkPosition() {
			return !used;//(PVector.sub(this.location,weapon.owner.location).mag())<weapon.owner.radius+caliber;
		}

		@Override
		public void harmTarget(Harmable target, float distance) {
			target.harm(this.damage);
			this.used=true;
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
		//do nothing. we have one bullet. always...
		if(bullets.length<1) {
			TeethBullet bullet = new TeethBullet(this.p, owner.location, "sdf.sdf", 0, 0, caliber, 1, 0, this, 1);
			//bullet.sprite = new Animation(this.bulletAnimation);
			
			bullets = (Bullet[]) PApplet.append(bullets, bullet);			
		}
	}
	
	public Teeth(Main applet, MovingObject owner, XMLElement prefs) {
		super(applet,owner, prefs); 
	}
	
	public void displayBullets() {
		generateBullet(0, 0, 0, 0, 0);
		super.displayBullets();		
	}
}
