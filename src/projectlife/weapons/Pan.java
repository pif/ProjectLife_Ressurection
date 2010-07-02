package projectlife.weapons;

import processing.core.*;
import processing.xml.XMLElement;
import projectlife.*;
public class Pan extends Weapon {
	public class PanBullet extends Bullet {

		public PanBullet(Main applet, PVector position, String img,
				float angle, int color, float radius, float health,
				float maxSpeed, Weapon weapon, float weight,PVector startPos) {
			super(applet, position, img, angle, color, radius, health, maxSpeed, weapon,
					weight, startPos);
			
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
			location=weapon.owner.location.get();
			location.x+=PApplet.cos(weapon.owner.angle)*32+4;
			location.y+=PApplet.sin(weapon.owner.angle)*32-16;
		}

		@Override
		public int collide() {
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
		//do nothing. we have one bullet. always...
		if(bullets.length<1) {
			PanBullet bullet = new PanBullet(this.p, owner.location, "sdf.sdf", 0, 0, caliber, 1, 0, this, 1, owner.location);
			//bullet.sprite = new Animation(this.bulletAnimation);
			
			bullets = (Bullet[]) PApplet.append(bullets, bullet);			
		}
	}
	
	public Pan(Main applet, MovingObject owner, XMLElement prefs) {
		super(applet,owner, prefs); 
	}
	
	public void displayBullets() {
		super.displayBullets();		
	}
}

