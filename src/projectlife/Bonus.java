package projectlife;

import processing.core.*;

/**
*/
public class Bonus extends Overlay {

	public String action;
	public long startTime;
	public long timeToLive;
	public int alpha;

	public Bonus(Main applet, PVector position, String img, float angle,
			int color, float radius, String command) {
		super(applet, position, img, angle, color, radius, new PVector());

		this.action = command;
		startTime = applet.millis();
		timeToLive = 8000;
		alpha = 255;
	}

	boolean warriorGotMe() {
		for (int i = 0; i < p.level.warriors.length; ++i) {
			if (p.level.warriors[i].location.dist(this.location) <= (this.radius / 2 + p.level.warriors[i].radius / 2)) {
				p.level.tweaker.tweak(p.level.warriors[i], action);
				visible = false;
				return true;
			}
		}
		return false;
	}

	public boolean display() {
		if (p.millis() - startTime > timeToLive) {
			alpha--;
			if (alpha <= 0) {
				this.visible = false;
			}
		}
		p.pushStyle();
		p.tint(255, alpha);
		super.display();
		p.popStyle();
		return visible;
	}
}
