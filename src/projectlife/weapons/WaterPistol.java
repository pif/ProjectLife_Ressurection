package projectlife.weapons;

import processing.core.*;
import processing.xml.XMLElement;
import projectlife.*;

public class WaterPistol extends Weapon {
	
	public class WaterPistolBullet extends Bullet {

		private int start;
		public WaterPistolBullet(Main applet, PVector position, String img,
				float angle, int color, float radius, float health,
				float maxSpeed, Weapon weapon, float weight,PVector startPos) {
			super(applet, position, img, angle, color, radius, health,
					maxSpeed, weapon, weight,startPos);		
			
			acceleration = new PVector(speed* PApplet.cos(angle), maxSpeed
					* PApplet.sin(angle));
			this.start = 0;
		}

		@Override
		public boolean checkPosition() {
			return p.level.isCoordianteOnBoard(this.location);
		}

		@Override
		public void harmTarget(Harmable target, float distance) {
			target.harm(this.damage);//*(p.map(p.abs(distance/this.range),0,this.range,0,1)));
		}

		@Override
		public void updateLocation() {
			velocity.add(acceleration);
			//velocity.limit(maxSpeed);
			location.add(velocity);
			acceleration.set(PApplet.cos(start)*start, PApplet.sin(start)*start, 0);
			++start;
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
					this.visible=false;
					return i;
				}
			}
			return -1;
		}

	}
	public WaterPistol(Main applet, MovingObject owner, XMLElement prefs) {
		super(applet,owner, prefs);
	}
	@Override
	public void generateBullet(float targetX, float targetY, float startX,
			float startY, float angle) {
		
		// TODO Auto-generated method stub
		WaterPistolBullet bullet = new WaterPistolBullet(p, new PVector(
				startX, startY), "sdf.sdf", angle + p.random(-jitter, jitter),
				0xFFFFFFFF, caliber, this.damage, this.bulletSpeed, this, this.weight
						/ rackSize,new PVector(startX, startY));
		bullet.sprite = new Animation(this.bulletAnimation);
		
		this.bullets = (Bullet[]) PApplet.append(this.bullets, bullet);
		currentRackSize--;
		if(currentRackSize<0) {
			currentRackSize=0;
		}
		lastShotTime = p.millis();
		
	}

}