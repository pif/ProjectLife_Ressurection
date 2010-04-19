package projectlife;

import processing.core.*;

/**
*/
public class Warrior extends MovingObject {

	public int experience;

	public Warrior(Main applet, PVector position, String img, float angle,
			int color, float radius, float health, float maxSpeed,
			Weapon weapon, PVector target, int experience) {
		super(applet, position, img, angle, color, radius, health, maxSpeed,
				weapon, target);
		this.experience = experience;
		// TODO Auto-generated constructor stub
		
		for(int i=1;i<5;i++)
			this.sprite.addSprite(p.loadImage(p.dataPath+"images/warrior/"+i+".png"), 50);
	}

	public void move() {
		super.move();

		location.x = PApplet.constrain(location.x, 0, p.width);
		location.y = PApplet.constrain(location.y, 0, p.height);

		target = new PVector(p.mouseX, p.mouseY);
	}

	public void shoot(int x, int y) {
		weapon.shoot((int) location.x, (int) location.y, angle);
	}

	public boolean display() {
		weapon.displayBullets();

		p.pushMatrix();
		p.fill(255, 100);
		p.translate(location.x, location.y);
		p.rotate(PApplet.atan2(velocity.y, velocity.x) + PConstants.HALF_PI);
		p.triangle(-10, -50, 0, -70, 10, -50);
		p.rect(0, -45, 10, 10);
		p.popMatrix();

		return super.display();
	}

	public void stop() {
		super.stop();
		for (int i = 0; i < weapon.bullets.length; i++) {
			weapon.bullets[i].stop();
		}
	}
	
	public void letGo() {
		super.letGo();
		for (int i = 0; i < weapon.bullets.length; i++) {
			weapon.bullets[i].letGo();
		}
	}	
}
