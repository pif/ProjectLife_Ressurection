package projectlife;

import processing.core.*;

public class Overlay extends StandingObject {
	public Overlay(Main applet, PVector position, String img, float angle,
			int color, float radius, PVector target) {
		super(applet, position, img, angle, color, radius, target);
		topLeft = new PVector(position.x - picture.width / 2, position.y
				- picture.height / 2);
		bottomRight = new PVector(position.x + picture.width / 2, position.y
				+ picture.height / 2);
	}

	/**
	 */
	public boolean mouseOver;
	/**
	 */
	public boolean mouseDown;
	/**
	 */
	public PVector topLeft;
	/**
	 */
	public PVector bottomRight;

	/**
	 * @param Return
	 * @return
	 */
	public boolean click() {
		return false;
	}

	public void act() {
		// TODO Auto-generated method stub

	}
	
	public boolean display() {
		return super.display();
	}
}
