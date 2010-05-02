package projectlife.weapons.bullets;

import projectlife.*;
import processing.core.*;

public class StoneThrowerBullet extends Bullet{

	@Override
	public void generateBullet(float targetX, float targetY, float startX,
			float startY, float angle) {
		// TODO Auto-generated method stub
		
	}
	
	//move
	
	
	@Override
	public boolean checkPosition() {
		if(p.level.isCoordianteOnBoard(this.location))
			return true;
		else return false;
	}
//	public StoneThrower(Main applet) {
//		super(applet, position, img, angle, color, radius, health, maxSpeed, weapon, target)	
//	}
}
