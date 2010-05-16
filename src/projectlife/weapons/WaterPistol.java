package projectlife.weapons;

import processing.xml.XMLElement;
import projectlife.Main;
import projectlife.MovingObject;
import projectlife.Weapon;
import projectlife.Bullet;

public class WaterPistol extends Weapon {

	public WaterPistol(Main applet, MovingObject owner, XMLElement xmlElement) {
		super(applet, owner, xmlElement);
	}

	@Override
	public void generateBullet(float targetX, float targetY, float startX,
			float startY, float angle) {
		// TODO Auto-generated method stub

	}

}
