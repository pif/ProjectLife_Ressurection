package projectlife;

import processing.core.*;

/**
*/
public class Bonus extends Overlay {

	public String action;

	public Bonus(Main applet, PVector position, String img, float angle,
			int color, float radius, String command) {
		super(applet, position, img, angle, color, radius, new PVector());
		
		this.action = command;
	}

	boolean warriorGotMe() {
		for (int i = 0; i < p.level.warriors.length; ++i) {
			if (p.level.warriors[i].location.dist(this.location) <= (this.radius + p.level.warriors[i].radius)) {
				p.level.tweaker.tweak(p.level.warriors[i], action);
				visible=false;
			}
		}
		return false;
	}

}
