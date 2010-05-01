package projectlife;

import processing.core.*;

/**
*/
public class Bullet extends MovingObject {

	public Bullet(Main applet, PVector position, String img, float angle,
			int color, float radius, float health, float maxSpeed,
			Weapon weapon, PVector target) {

		super(applet, position, img, angle, color, radius, health, maxSpeed,
				weapon, target);

		acceleration = new PVector(maxSpeed * PApplet.cos(angle), maxSpeed
				* PApplet.sin(angle));

		bulletsTrail = new PVector[50];
		for (int i = 0; i < bulletsTrail.length; i++) {
			bulletsTrail[i] = location;
		}
	}

	//draw the bullet...
	//use Animation class!
	public boolean display() {
		for (int i = 0; i < bulletsTrail.length; i++) {
			p.fill(100 + i * 3);
			p.ellipse(bulletsTrail[i].x, bulletsTrail[i].y, radius, radius);
		}
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
	public void move() {
		// acceleration.normalize();
		if (!stopped) {
			velocity.add(acceleration);
			velocity.limit(maxSpeed);
			location.add(velocity);

			if (location.x < 0 || location.x > p.width || location.y < 0
					|| location.y > p.height) {
				visible = false;
			}
			kill();
			for (int i = 0; i < bulletsTrail.length - 2; i++) {
				bulletsTrail[i] = bulletsTrail[i + 1];
			}
			bulletsTrail[bulletsTrail.length - 1] = location;
		}
	}
	
	//needless array... Animation owned u
	public PVector[] bulletsTrail;

	// should return an object, which was hit...and weapon should think WHAT to do with theM!!!! 
	//maybe i should return an array of IHittable objects?
	public int kill() {
		for (int i = 0; i < p.level.beasts.length; i++) {
			if (PApplet.dist(this.location.x, this.location.y,
					p.level.beasts[i].location.x, p.level.beasts[i].location.y) < (this.radius + p.level.beasts[i].radius)) {
				p.level.beasts[i].health -= this.health;
				p.level.ground.addBlood(new PVector(this.location.x,
						this.location.y), 0x88FF0000);
				this.visible = false;
				// p.level.ground.dust.image(p.level.ground.blood,
				// p.level.beasts[i].location.x, p.level.beasts[i].location.x);
				return i;
			}
		}
		return -1;
	}
}
