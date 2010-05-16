package projectlife.weapons;

import processing.xml.XMLElement;
import projectlife.Main;
import projectlife.MovingObject;
import projectlife.Weapon;
import projectlife.Bullet;
public class PlagueSpreader extends Weapon {

	public PlagueSpreader(Main applet, MovingObject owner, XMLElement prefs) {
		super(applet,owner, prefs);
	}
	@Override
	public void generateBullet(float targetX, float targetY, float startX,
			float startY, float angle) {
		// TODO Auto-generated method stub
		
	}

}
