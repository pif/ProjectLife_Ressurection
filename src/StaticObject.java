import processing.core.*;

/**
*/
public class StaticObject extends MyObject {
	/**
*/
	public PVector location;
	/**
*/
	public PImage sprite;
	/**
*/
	public PVector target;

	public float angle;
	/**
*/
	public int color;
	/**
*/
	public float radius;
	/**
*/
	public boolean visible;

	public StaticObject(Main applet, PVector position, String img, float angle,
			int color, float radius, PVector target) {
		super(applet);
		this.location = position;
		this.sprite = p.loadImage(img);
		this.angle = angle;
		this.color = color;
		this.radius = radius;
		this.visible = true;

		this.target = new PVector();
	}

	/**
	 * turn object accoring to the rules defined
	 * 
	 * @param Return
	 */
	public void turn(PVector target) {
		angle = PApplet.atan2(target.y - location.y, target.x - location.x);
		//p.rotate(angle);
	}

	public void turn() {
		turn(this.target);
	}

	/**
	 * draws an object
	 * 
	 * @param Return
	 * @return
	 */
	public boolean display() {
		p.pushMatrix();

		p.translate(location.x, location.y);
		p.rotate(angle);
		if (sprite == null) {
			p.fill(color);
			p.ellipse(0, 0, radius, radius);
		} else {
			p.image(sprite, 0 - sprite.width / 2, 0 - sprite.height / 2);
		}

		p.popMatrix();
		return false;
	}

	/**
	 * returns true, if object is ok (should be drawn, visible=true) false, if
	 * it's useless (should not be drawn, visible=false)
	 * 
	 * @param Return
	 * @return
	 */
	public boolean check() {
		return visible;
	}
}
