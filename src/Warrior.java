import processing.core.*;

/**
*/
public class Warrior extends DynamicObject {

	public int experience;

	public Warrior(Main applet, PVector position, String img, float angle,
			int color, float radius, float health, float maxSpeed,
			Weapon weapon, PVector target, int experience) {
		super(applet, position, img, angle, color, radius, health, maxSpeed,
				weapon,target);
		this.experience = experience;
		// TODO Auto-generated constructor stub
	}

	public void move() {
		super.move();
		
		location.x = PApplet.constrain(location.x, 0, p.width);
		location.y = PApplet.constrain(location.y, 0, p.height);

		target = new PVector(p.mouseX, p.mouseY);
	}

	public void shoot(int x, int y) {
		weapon.shoot(x,y);
	}
	
	public boolean display() {
		p.pushMatrix();
		p.translate(location.x, location.y);
		p.rotate(p.atan2(velocity.y, velocity.x)+p.HALF_PI);
		p.triangle(-10, -50, 0, -70, 10, -50);
		p.rectMode(p.CORNERS);
		p.rect(-5, -40, 5, -50);
		p.popMatrix();
		
		return super.display();
	}
	/**
*/
}