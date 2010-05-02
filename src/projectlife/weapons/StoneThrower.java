package projectlife.weapons;

import projectlife.*;
import processing.core.*;

public class StoneThrower extends Weapon {

	public class StoneThrowerBullet extends Bullet {

		public StoneThrowerBullet(Main applet, PVector position, String img,
				float angle, int color, float radius, float health,
				float maxSpeed, Weapon weapon, PVector target, PVector start,
				Harmable[] targets) {
			super(applet, position, img, angle, color, radius, health,
					maxSpeed, weapon, target, start, targets);
			// TODO Auto-generated constructor stub
		}

		@Override
		public boolean checkPosition() {
			return p.level.isCoordianteOnBoard(this.location);
		}

		@Override
		public void harmTarget(Harmable target, float distance) {
			target.harm(this.damage);
		}

		@Override
		public void updateLocation() {
			velocity.add(acceleration);
			velocity.limit(maxSpeed);
			location.add(velocity);
		}

		// update location.
		// if(checkposition)
		// 	if(collide()!=-1)
		//	 kill();
		//		 else deleteMe;
		@Override
		public void move() {
			if (!stopped) {
				updateLocation();

				if (checkPosition()) {
					if (this.collide() != -1) {
						kill();
					}
				} else {
					visible = false;
				}
			}
		}

	}

	@Override
	public void generateBullet(float targetX, float targetY, float startX,
			float startY, float angle) {
		// TODO Auto-generated method stub

	}
}
