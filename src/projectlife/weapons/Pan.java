package projectlife.weapons;

import processing.core.*;
import processing.xml.XMLElement;
import projectlife.*;
public class Pan extends Weapon {

	public Pan(Main applet, MovingObject owner, XMLElement xmlElement) {
		super(applet,owner,xmlElement);
	}

	@Override
	public void generateBullet(float targetX, float targetY, float startX,
			float startY, float angle) {
		// TODO Auto-generated method stub
		
	}

}
