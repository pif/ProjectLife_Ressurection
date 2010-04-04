import processing.core.*;

/**
*/
public class Weapon extends MyObject{
	/**
*/
	public float damage;
	/**
*/
	public float speed;
	/**
*/
	public float radius;
	/**
*/
	public float jitter;
	/**
*/
	public int rackSize;
	/**
*/
	public Bullet[] bullets;

	/**
	 * @param Return
	 * @return
	 */

	public boolean reload() {
		return false;
	}

	/**
	 * @param Return
	 */
	public void updateBullets() {
	}

	/**
	 * @param Return
	 */
	public void displayBullets() {
	}

	public Weapon(Main applet, float damage, float radius, float speed, float jitter) {
		super(applet);
		
		this.damage = damage;
		this.radius = radius;
		this.bullets = new Bullet[0];
		this.speed = speed;
		this.jitter = jitter;
	}

	public void shoot(int x, int y) {
		// TODO add new bullet to array
	    //Bullet b = new Bullet(,new PVector(x,y),"",
	    //weapon.bullets = (Bullet[]) append(weapon.bullets,b);		
	}
}
