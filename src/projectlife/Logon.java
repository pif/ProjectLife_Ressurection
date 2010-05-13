package projectlife;

import processing.core.*;

public class Logon extends MyObject {
	class HoverPic extends MyObject {
		public PImage pic;
		public PImage hover;
		boolean mouse;
		int hoverAlpha;
		public PVector pos;

		HoverPic(Main applet, String p, String h, PVector ps) {
			super(applet);
			pic = applet.loadImage(p);
			hover = applet.loadImage(h);
			mouse = false;
			hoverAlpha = 0;
			pos = ps;
		}

		void update(PVector center) {
			mouse = false;
			if (p.mouseX >= center.x + pos.x
					&& p.mouseX <= center.x + pos.x + pic.width) {
				if (p.mouseY >= center.y + pos.y
						&& p.mouseY <= center.y + pos.y + pic.height) {
					mouse = true;
				}
			}
		}

		void display() {
			update(cossacsCenter);
			p.image(pic, pos.x, pos.y);

			if (mouse) {
				hoverAlpha = 255;
			} else {
				hoverAlpha -= 10;
			}
			if (hoverAlpha > 0) {
				p.pushStyle();
				p.tint(255, hoverAlpha);
				p.image(hover, pos.x + pic.width / 2 - hover.width / 2, pos.y
						- hover.height);
				p.popStyle();
			}
		}
	}

	PImage skyBg;
	PImage cossacs;
	PVector cossacsCenter;
	HoverPic[] warriors;

	public Logon(Main applet) {
		super(applet);
		skyBg = p.loadImage(p.dataPath + "images/skybg.png");

		cossacs = p.loadImage(p.dataPath + "images/cossacs.png");
		cossacsCenter = new PVector(p.width / 2, p.height - cossacs.height / 2);

		warriors = new HoverPic[0];

		warriors = (HoverPic[]) p.append(warriors, new HoverPic(applet,
				p.dataPath + "images/oko_pic.png", p.dataPath
						+ "images/oko_hov.png", new PVector(66, 29)));
		warriors = (HoverPic[]) p.append(warriors, new HoverPic(applet,
				p.dataPath + "images/graj_pic.png", p.dataPath
						+ "images/graj_hov.png", new PVector(-53, -49)));
		warriors = (HoverPic[]) p.append(warriors, new HoverPic(applet,
				p.dataPath + "images/tur_pic.png", p.dataPath
						+ "images/tur_hov.png", new PVector(-187, 21)));
	}

	String getWarriorString(int i) {
		switch (i) {
		case 0:
			return "Oko";
		case 1:
			return "Graj";
		case 2:
			return "Tur";
		}

		return "";
	}

	int getWarrior() {
		for (int i = 0; i < warriors.length; ++i) {
			if (warriors[i].mouse) {
				return i;
			}
		}
		return -1;
	}

	void display() {
		p.ellipse(100, 100, 100, 100);
		p.image(skyBg, 0, 0, p.width, p.height);// background(skyBg);
		p.image(cossacs, p.width / 2 - cossacs.width / 2, p.height
				- cossacs.height);

		p.pushMatrix();
		p.translate(cossacsCenter.x, cossacsCenter.y);
		for (int i = 0; i < warriors.length; ++i) {
			warriors[i].display();
		}
		p.popMatrix();
	}
}
