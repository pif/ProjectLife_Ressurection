package projectlife;

import processing.core.*;

/**
*/
public abstract class Bullet extends MovingObject {
	
	public float caliber;//got from weapon
	public float speed;
	public float weight;
	public float damage;//got from weapon
	//public float radius...got it from Standing object
	
	public Bullet(Main applet, PVector position, String img, float angle,
			int color, float radius, float health, float maxSpeed,
			Weapon weapon, PVector target, PVector start) {

		super(applet, position, img, angle, color, radius, health, maxSpeed,
				weapon, target);

		acceleration = new PVector(maxSpeed * PApplet.cos(angle), maxSpeed
				* PApplet.sin(angle));

		bulletsTrail = new PVector[50];
		for (int i = 0; i < bulletsTrail.length; i++) {
			bulletsTrail[i] = location;
		}
	}
	public Bullet()	{
		super(null,new PVector(),"",0,0,0,0,0,new Weapon(),new PVector());
	}

	// draw the bullet...
	// use Animation class!
	public boolean display() {
		// for (int i = bulletsTrail.length - 1; i != 0; i--) {
		// p.fill(255, 255 - i * 5);
		// p.ellipse(bulletsTrail[i].x, bulletsTrail[i].y, radius, radius);
		// }
		return super.display();
	}

	// hmm... that seems to me that every weapon should have it's own bullet
	// class..
	// maybe, we don't even need different weapons...we just need different
	// bullets???
	// that's the method which moves a bullet. for different weapons this should
	// be quite different
	public abstract void move();// {
		// acceleration.normalize();
	//}
	public boolean collide() {
		
	}

	public PVector startPos;
	// should return an object, which was hit...and weapon should think WHAT to
	// do with theM!!!!
	// maybe i should return an array of IHittable objects?
	public int kill() {
		for (int i = 0; i < p.level.beasts.length; i++) {
			if (PApplet.dist(this.location.x, this.location.y,
					p.level.beasts[i].location.x, p.level.beasts[i].location.y) < (this.radius + p.level.beasts[i].radius)) {
				p.level.beasts[i].health -= this.health;
				p.level.ground.addBlood(new PVector(this.location.x,
						this.location.y), 0x88FF0000);
				this.visible = false;
				float ad=PConstants.TWO_PI;
				// p.level.ground.dust.image(p.level.ground.blood,
				// p.level.beasts[i].location.x, p.level.beasts[i].location.x);
				return i;
			}
		}
		return -1;
	}
	public boolean checkPosition() {
		if(p.level.isCoordianteOnBoard(this.location))
			return true;
		else return false;
	}
}
