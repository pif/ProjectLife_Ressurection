package projectlife.weapons;

import processing.core.PApplet;
import processing.core.PVector;
import processing.xml.XMLElement;
import projectlife.Animation;
import projectlife.Harmable;
import projectlife.Main;
import projectlife.MovingObject;
import projectlife.Weapon;
import projectlife.Bullet;
import projectlife.weapons.PlagueSpreader.PlagueSpreaderBullet;
public class PlagueSpreader extends Weapon {
	
	public class PlagueSpreaderBullet extends Bullet {

		public PlagueSpreaderBullet(Main applet, PVector position, String img,
				float angle, int color, float radius, float health,
				float maxSpeed, Weapon weapon, float weight,PVector startPos) {
			super(applet, position, img, angle, color, radius, health,
					maxSpeed, weapon, weight,startPos);		
			
			acceleration = new PVector(speed* p.cos(angle), maxSpeed
					* p.sin(angle));
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
					weapon.generateBullet(this.target.x, this.target.y, this.location.x+32*p.cos(angle), this.location.y+ 32*p.sin(angle), this.angle-p.QUARTER_PI);
					weapon.generateBullet(this.target.x, this.target.y, this.location.x+32*p.cos(angle), this.location.y+ 32*p.sin(angle), this.angle+p.QUARTER_PI);
					this.visible=false;
					return i;
				}
			}
			return -1;
		}

	}
	public PlagueSpreader(Main applet, MovingObject owner, XMLElement prefs) {
		super(applet,owner, prefs);
	}
	@Override
	public void generateBullet(float targetX, float targetY, float startX,
			float startY, float angle) {
		
		// TODO Auto-generated method stub
	 PlagueSpreaderBullet bullet = new PlagueSpreaderBullet(p, new PVector(
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
