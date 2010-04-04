import processing.core.*;

public class Overlay extends StaticObject {
	public Overlay(Main applet, PVector position, String img, float angle,
			int color, float radius, PVector target) {
		super(applet, position, img, angle, color, radius,target);
		topLeft = new PVector(position.x - sprite.width / 2, position.y
				- sprite.height / 2);
		bottomRight = new PVector(position.x + sprite.width / 2, position.y
				+ sprite.height / 2);
		// TODO think about angle, polar coordinates. maybe in MenuManager
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
}
