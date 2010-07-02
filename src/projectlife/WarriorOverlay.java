package projectlife;

import java.util.HashMap;


import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;
import processing.core.PVector;

public class WarriorOverlay extends OverlayManager {

	PImage house;

	PImage eyes;
	PImage speed;
	PImage reload;

	PImage head;

	float alpha;
	HashMap<String, PImage> weapons;

	public WarriorOverlay(Main applet) {
		super(applet);

		overlays = new Overlay[] { new Overlay(applet, new PVector(p.width / 2,
				50), "images/house2.png", PApplet.radians(0), 100, 0,
				new PVector()) };

		visible = true;
		alpha = 255;
		overlays[0].location.set((float) p.width - overlays[0].picture.width
				/ 2, (float) p.height - overlays[0].picture.height / 2, 0);

		eyes = p.loadImage(p.dataPath + "images/eyes.png");
		speed = p.loadImage(p.dataPath + "images/speed.png");
		reload = p.loadImage(p.dataPath + "images/reload.png");

		head = null;

		weapons = new HashMap<String, PImage>();
		String[] keys = (String[]) p.availableWeapons.keySet().toArray(
				new String[p.availableWeapons.keySet().size()]);
		for (int i = 0; i < keys.length; ++i) {
			weapons.put(keys[i], p.loadImage(p.dataPath + "images/bonuses/"
					+ keys[i] + ".png"));
		}
		weapons.remove("Teeth");

	}

	public void display() {
		p.pushStyle();
		if ((p.level.warriors[0].location.x > overlays[0].location.x && p.level.warriors[0].location.y > overlays[0].location.y)
				|| (p.level.warriors[0].location.x > overlays[0].location.x && p.level.warriors[0].location.y > p.height
						- overlays[0].picture.height)
				|| (p.level.warriors[0].location.x > p.width
						- overlays[0].picture.width && p.level.warriors[0].location.y > overlays[0].location.y)) {

			alpha -= 5;
			alpha = PApplet.constrain(alpha, 125, 255);
		} else {
			alpha += 5;
			alpha = PApplet.constrain(alpha, 125, 255);
		}
		p.tint(255, alpha);
		super.display();
		p.popStyle();

		p.pushStyle();
		p.pushMatrix();
		// health bar display
		p.pushMatrix();
		p.translate(overlays[0].location.x - overlays[0].picture.width / 2
				+ 1015 / 2, overlays[0].location.y - overlays[0].picture.height
				/ 2 + 611 / 2);
		p.rectMode(PConstants.CORNER);
		p.noStroke();
		p.fill(255, 0, 0, 255 / 2);
		p.rect(0, 0, 418 / 2, 9);
		p.fill(255, 0, 0);
		p.rect(0, 0, PApplet.map(p.level.warriors[0].health, 0,
				p.level.warriors[0].maxHealth, 0, 418 / 2), 9);
		p.popMatrix();

		// bullets bar display
		p.pushMatrix();
		p.translate(overlays[0].location.x - overlays[0].picture.width / 2
				+ 360 / 2, overlays[0].location.y - overlays[0].picture.height
				/ 2 + 611 / 2);
		p.rectMode(PConstants.CORNER);
		p.noStroke();
		p.fill(0, 0, 255, 255 / 2);
		p.rect(0, 0, 259 / 2, 9);
		p.fill(0, 0, 255);
		if (p.level.warriors[0].weapon.canShoot) {
			p.rect(0, 0, PApplet.map(p.level.warriors[0].weapon.currentRackSize, 0,
					p.level.warriors[0].weapon.rackSize, 0, 259 / 2), 9);
		} else {

			p.rect(0, 0, PApplet.constrain(PApplet.map(p.millis()
					- p.level.warriors[0].weapon.reloadStartTime, 0,
					p.level.warriors[0].weapon.reloadTime, 0, 259 / 2), 0,
					259 / 2), 9);
		}
		p.popMatrix();

		// draw bonuses
		// eyes
		p.pushMatrix();
		p.translate(overlays[0].location.x - overlays[0].picture.width / 2
				+ 1393 / 2, overlays[0].location.y - overlays[0].picture.height
				/ 2 + 488 / 2);
		if (p.level.tweaker.seeHealth.set) {
			p.image(eyes, 0, 0);
		} else {
			p.pushStyle();
			p.tint(75, 125);
			p.image(eyes, 0, 0);
			p.popStyle();
		}
		p.popMatrix();
		// speed-horse
		p.pushMatrix();
		p.translate(overlays[0].location.x - overlays[0].picture.width / 2
				+ 1290 / 2, overlays[0].location.y - overlays[0].picture.height
				/ 2 + 488 / 2);
		if (p.level.tweaker.speedFactor.set) {
			p.image(speed, 0, 0);
		} else {
			p.pushStyle();
			p.tint(75, 125);
			p.image(speed, 0, 0);
			p.popStyle();
		}
		p.popMatrix();
		// reload
		p.pushMatrix();
		p.translate(overlays[0].location.x - overlays[0].picture.width / 2
				+ 1190 / 2, overlays[0].location.y - overlays[0].picture.height
				/ 2 + 490 / 2);
		if (p.level.tweaker.reloadFactor.set) {
			p.image(reload, 0, 0);
		} else {
			p.pushStyle();
			p.tint(75, 125);
			p.image(reload, 0, 0);
			p.popStyle();
		}
		p.popMatrix();

		// head
		if(head==null) {
			head = p.loadImage(p.dataPath + "images/head" + p.warriorSelected
					+ ".png");
		}
		p.pushMatrix();
		p.translate(overlays[0].location.x - overlays[0].picture.width / 2
				+ 909 / 2, overlays[0].location.y - overlays[0].picture.height
				/ 2 + 531 / 2);

		p.image(head, 0, 0);

		p.popMatrix();

		// weapon
		p.pushMatrix();
		p.translate(overlays[0].location.x - overlays[0].picture.width / 2
				+ 269 / 2, overlays[0].location.y - overlays[0].picture.height
				/ 2 + 585 / 2);

		String st = p.level.warriors[0].weapon.getClass().getSimpleName();

		p.image(weapons.get(st), 0, 0);

		p.popMatrix();

		p.popStyle();
		p.popMatrix();
		
		//exp
		
		p.pushMatrix();
		p.pushStyle();
		p.textAlign(PConstants.RIGHT);
		p.translate(overlays[0].location.x - overlays[0].picture.width / 2
				+ 602-20 , overlays[0].location.y - overlays[0].picture.height
				/ 2 + 183-70);
		p.fill(0,51,0);
		p.text((int)p.level.exp,100,100);
		p.popStyle();
		p.popMatrix();
	}
}
