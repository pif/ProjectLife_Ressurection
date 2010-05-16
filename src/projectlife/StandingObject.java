package projectlife;

import processing.core.*;
import processing.xml.XMLElement;

import java.io.*;

/**
*/
public class StandingObject extends MyObject {
	/**
*/
	public PVector location;
	/**
*/
	public PImage picture;
	/**
*/
	public Animation sprite;
	public Animation hover;

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
	public boolean stopped;

	public StandingObject(Main applet, PVector position, String img,
			float angle, int color, float radius, PVector target) {
		super(applet);
		this.location = position;
		File f = new File(applet.dataPath + img);
		if (f.exists()) {
			this.picture = p.loadImage(img);
		} else {
			this.picture = null;
		}

		this.angle = angle;
		this.color = color;
		
		if(radius<=0 && this.picture!=null) {
			this.radius = this.picture.width/2;
		} else {
			this.radius = radius;
		}
		this.visible = true;

		this.target = new PVector();

		this.sprite = new Animation(applet);
		this.hover = new Animation(applet);
		
	}

	public void setAnimation(XMLElement animation) {
		sprite = new Animation(animation, p);
	}

	public void setAnimation(Animation animation) {
		sprite = new Animation(p);
		for (int i = 0; i < animation.sprites.length; ++i) {
			sprite.addSprite(animation.sprites[i].image,
					animation.sprites[i].time);
		}

	}

	public boolean mouseOver() {
		return (PApplet.abs(p.mouseX - location.x) < this.radius)
				&& (PApplet.abs(p.mouseY - location.y) < this.radius);
	}

	/**
	 * turn object accoring to the rules defined
	 * 
	 * @param Return
	 */
	public void turn(PVector target) {
		angle = PApplet.atan2(target.y - location.y, target.x - location.x);
		// p.rotate(angle);
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
		if (visible) {
			p.pushMatrix();

			p.translate(location.x, location.y);
			p.rotate(angle);
			// if(p.debug) {
			// p.fill(255);
			// p.ellipse(0, 0, radius, radius);
			// } else {
			if (this.mouseOver() && hover.sprites.length>0) {
				PImage pic = hover.getNext();
				if (pic != null) {
					p.image(pic, 0 - pic.width / 2, 0 - pic.height / 2);
				}
			} else if (sprite.sprites.length > 0) {
				PImage pic = sprite.getNext();
				if (pic != null) {
					p.image(pic, 0 - pic.width / 2, 0 - pic.height / 2);
				}
			} else {
				if (picture == null) {
					p.fill(color);
					p.ellipse(0, 0, radius, radius);
				} else {
					p.image(picture, 0 - picture.width / 2,
							0 - picture.height / 2);
				}
			}
			// }
			if (p.debug) {
			//	p.ellipse(0, 0, radius, radius);
			//	p.text(this.radius, 40, 40);
			}
			p.popMatrix();
		}
		return visible;
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

	/**
	*/
	public void stop() {
		stopped = true;
		sprite.pause();
	}

	public void letGo() {
		stopped = false;
		sprite.goOn();
	}
}
