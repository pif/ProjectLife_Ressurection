package projectlife;

import processing.core.*;

/**
*/
public class Beast extends DynamicObject {

	public static int spotDistance = 100;

	public int steps;
	public int currentTarget;
	public PVector[] targets;
	public boolean followTargets;

	public Beast(Main applet, PVector position, String img, float angle,
			int color, float radius, float health, float maxSpeed,
			Weapon weapon, PVector target) {
		super(applet, position, img, angle, color, radius, health, maxSpeed,
				weapon, target);
		// code, that chooses where to put that bastard
		int distFromBorder = 50;
		location = new PVector(p.random(p.width), p.random(p.height));
		if ((int) (p.random(2)) == 1) {
			if ((int) (p.random(2)) == 1)
				location.y = p.height + distFromBorder;
			else
				location.y = -distFromBorder;
		} else {
			if ((int) (p.random(2)) == 1)
				location.x = p.width + distFromBorder;
			else
				location.x = -distFromBorder;
		}

		steps = (int) (applet.random(2, 5));
		calcSteps(steps);
		currentTarget = 0;
		followTargets = true;
	}

	// optimise beasts. don't call turn() every frame better calculate
	// AI: curveTightness() - the way monsters go to target
	public void calcSteps(int stepCount) {
		if (stepCount <= 0) {
			followTargets = false;
		} else {
			targets = new PVector[stepCount];
			PVector p1 = new PVector(p.random(-p.width, p.width), p.random(
					-p.height, p.height));
			PVector p2 = new PVector(p.random(-p.width, p.width), p.random(
					-p.height, p.height));
			PVector s = this.location;
			PVector f = p.level.warrior.location;
			p.curveTightness(p.random(-1, 2));
			for (int i = 0; i < stepCount; ++i) {
				float t = i / (float) stepCount;
				float x = p.curvePoint(p1.x, s.x, f.x, p2.x, t);
				float y = p.curvePoint(p1.y, s.y, f.y, p2.y, t);
				targets[i] = new PVector(x, y);

				//p.curve(p1.x, p1.y, s.x, s.y, f.x, f.y, p2.x, p2.y);
			}
		}
	}

	public void move() {
		super.move();
		acceleration = new PVector(p.cos(angle), p.sin(angle));

		if (location.dist(p.level.warrior.location) < (this.radius
				+ p.level.warrior.radius + spotDistance)) {
			followTargets = false;
		} else {
			followTargets = true;
		}

		if (followTargets) {
			if (location.dist(target) < this.radius + spotDistance / 2) {
				currentTarget++;
				if (currentTarget >= steps) {
					calcSteps(steps);
					currentTarget = 0;
				}
			}
			target = targets[currentTarget];
		} else {
			target = p.level.warrior.location;
		}

//		if (followTargets) {
//			p.ellipseMode(p.CENTER);
//			for (int i = 0; i < steps; ++i)
//				p.ellipse(targets[i].x, targets[i].y, 32, 32);
//		}
	}
}
