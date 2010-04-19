package projectlife;

import processing.core.*;

/**
*/
public class Ground extends StandingObject {
	/**
*/
	public boolean tile;
	/**
*/
	public PGraphics dust;
	public PImage blood;
	public PVector[] bloodSplashes;
	public int[] bloodColors;

	/**
	 * @param position
	 * @param Return
	 * @param int color
	 */
	public void addBlood(PVector position, int color) {
		position.z = p.random(PConstants.TWO_PI);
		bloodSplashes = (PVector[]) PApplet.append(bloodSplashes, position);
		bloodColors = (int[]) PApplet.append(bloodColors, color);
		// dust.beginDraw();
		// dust.tint(color);
		// dust.pushMatrix();
		// dust.translate(position.x, position.y);
		// dust.rotate(p.random(PConstants.TWO_PI));
		// dust.image(blood, -blood.width / 2, -blood.height / 2);
		// dust.popMatrix();
		// // dust.blend(bloodTinted, 0, 0, blood.width, blood.height,
		// // (int)position.x-blood.width/2, (int)position.y-blood.height/2,
		// // blood.width, blood.height, p.ADD);
		// // p.blend(dust, 0, 0, dust.width, dust.height, 0, 0, p.width,
		// p.height,
		// // p.DARKEST);
		// dust.endDraw();
	}

	/**
 * 
 */
	public void drawBackground() {
		p.image(picture, 0, 0);// p.background(sprite);

		for (int i = 0; i < bloodSplashes.length; i++) {
			p.pushMatrix();
			p.pushStyle();
			p.translate(bloodSplashes[i].x, bloodSplashes[i].y);
			p.rotate(bloodSplashes[i].z);
			p.tint(bloodColors[i]);
			p.image(blood, 0, 0);
			p.popStyle();
			p.popMatrix();
		}
		// p.image(this.dust,0,0);// this.dust);
		// TODO think about level coordnaties and applet coordinates/ this means
		// that game field can be really huge. not just limited to the screen

	}

	/**
	 * @param img
	 * @param toTile
	 */
	public Ground(String img, boolean toTile, Main applet) {
		super(applet, new PVector(0, 0), img, 0, 0, 0, new PVector());
		if (toTile) {
			PGraphics pg = applet.createGraphics(p.width, p.height,
					PConstants.P3D);

			pg.beginDraw();
			int xc = pg.width / picture.width + 1;
			int yc = pg.height / picture.height + 1;

			for (int i = 0; i < xc; ++i)
				for (int j = 0; j < yc; ++j) {
					pg.image(picture, i * picture.width, j * picture.height);
				}
			pg.endDraw();

			picture = p.createImage(pg.width, pg.height, PConstants.RGB);
			picture.loadPixels();
			pg.loadPixels();
			picture.pixels = pg.pixels;
			picture.updatePixels();
		} else {
			// stretch image
			PGraphics pg = applet.createGraphics(p.width, p.height,
					PConstants.P3D);
			pg.beginDraw();
			pg.image(picture, 0, 0, pg.width, pg.height);
			pg.endDraw();

			picture = applet.createImage(pg.width, pg.height, PConstants.RGB);
			picture.loadPixels();
			pg.loadPixels();
			picture.pixels = pg.pixels;
			picture.updatePixels();
		}

		blood = p.loadImage("blood.png");
		bloodSplashes = new PVector[0];
		bloodColors = new int[0];

		dust = p.createGraphics(p.width, p.height, PConstants.P3D);
		dust.beginDraw();
		dust.image(picture, 0, 0);// background(255);
		dust.endDraw();
		// Ground(String txt, boolean state) {
	}
}
