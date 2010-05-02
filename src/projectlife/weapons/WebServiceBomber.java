package projectlife.weapons;

import processing.core.PApplet;
import processing.core.PVector;
import projectlife.Weapon;
import projectlife.Bullet;
public class WebServiceBomber extends Weapon {

	@Override
	public void generateBullet(float targetX, float targetY, float startX,
			float startY, float angle) {
		// TODO Auto-generated method stub
		//FIXME !!!
		Bullet b = new Bullet(p, new PVector(startX, startY), "sdf.sdf", angle
		dsfsf		+ p.random(-jitter, jitter), 0xFFFFFFFF, caliber, damage,
				bulletSpeed, this, new PVector(targetX,targetY), new PVector(startX, startY));
		this.bullets = (Bullet[]) PApplet.append(this.bullets, b);
		currentRackSize--;
		lastShotTime = p.millis();
	
	}
	

}
