package projectlife;

import processing.core.*;

/**
*/
public abstract class Bullet extends MovingObject {

	public float caliber;// got from weapon
	public float speed;
	public float damage;// got from weapon
	public float weight;
	public float range;

	// radius for radius of destruction;
	// public float radius...got it from Standing object
	public PVector startPos;
	
	public Bullet(Main applet, PVector position, String img, float angle,
			int color, float radius, float health, float maxSpeed,
			Weapon weapon, float weight, PVector startPos) {
		super(applet, position, img, angle, color, radius, health, maxSpeed, weapon,
				new PVector());

		this.caliber=weapon.caliber;
		this.speed = weapon.bulletSpeed;
		this.damage = weapon.damage * weapon.owner.power;
		this.weight = weight;
		this.range = weapon.bulletRange;
		this.health = weapon.damage;
		this.radius = this.caliber;
		this.startPos = startPos;
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

	// update location.
	// if(checkposition)
	// if(collide()!=-1)
	// kill();
	// else deleteMe;
	public abstract void move();

	public abstract void updateLocation();

	public abstract int collide();

	/**
	 * calculate damage according to your preference
	 */
	public abstract void harmTarget(Harmable target, float distance);

	// should return an object, which was hit...and weapon should think WHAT to
	// do with theM!!!!
	// maybe i should return an array of IHittable objects?
	/**
	 * calls harmTarget(target,distance) for every target in the bullet's range
	 * of destruction
	 */
	public void kill() {
		boolean harmed=false;
		for (int i = 0; i < weapon.targets.length; i++) {
			float distToTrgt = this.location.dist(weapon.targets[i].getLocation());
			if (distToTrgt <= this.range+this.caliber+weapon.targets[i].getRadius()) {
				harmTarget(weapon.targets[i], distToTrgt);
				harmed=true;
				// p.level.beasts[i].health -= this.health;
				PVector harmedPos = new PVector();
				harmedPos.x=weapon.targets[i].getLocation().x;
				harmedPos.y=weapon.targets[i].getLocation().y;
				
				p.level.ground.addBlood(harmedPos, 0x88FF0000);
				p.level.addBonus(harmedPos);
				// this.visible = false;
				// p.level.ground.dust.image(p.level.ground.blood,
				// p.level.beasts[i].location.x, p.level.beasts[i].location.x);

			}
		}
		if(harmed){
			this.health-=this.damage;
		}
	}

	/**
	 * check if bullet is OK
	 * 
	 * @return
	 */
	public abstract boolean checkPosition();
}
