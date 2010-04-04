import processing.core.*;

/**
*/
public class Bullet extends DynamicObject {

	public Bullet(Main applet, PVector position, String img, float angle,
			int color, float radius, float health, float maxSpeed, Weapon weapon, PVector target) {
		super(applet, position, img, angle, color, radius, health, maxSpeed,
				weapon,target);
		// TODO Auto-generated constructor stub
	}

}
