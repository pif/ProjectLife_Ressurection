import processing.core.*;

/**
*/
public class Beast extends DynamicObject {

	public Beast(Main applet, PVector position, String img, float angle,
			int color, float radius, float health, float maxSpeed,
			Weapon weapon, PVector target) {
		super(applet, position, img, angle, color, radius, health, maxSpeed,
				weapon, target);
		// TODO Auto-generated constructor stub
	}

	// TODO optimise beasts. don't call turn() every frame better calculate
	// TODO AI: curveTightness() - the way monsters go to target

	public void move() {
		super.move();
		target = new PVector(p.level.warrior.location.x,
				p.level.warrior.location.y);
		acceleration = new PVector(p.cos(angle), p.sin(angle));
	}

}
