import processing.core.*;

/**
*/
public class Ground extends StaticObject {
	/**
*/
	public boolean tile;
	/**
*/
	public PGraphics dust;
	public PImage blood;

	/**
	 * @param position
	 * @param Return
	 * @param int color
	 */
	public void addBlood(PVector position, int color) {
		dust.beginDraw();
		dust.tint(color);
		dust.pushMatrix();
		dust.translate(position.x, position.y);
		dust.rotate(p.random(PConstants.TWO_PI));
		dust.image(blood, -blood.width / 2, -blood.height / 2);
		dust.popMatrix();
		// dust.blend(bloodTinted, 0, 0, blood.width, blood.height,
		// (int)position.x-blood.width/2, (int)position.y-blood.height/2,
		// blood.width, blood.height, p.ADD);
		// p.blend(dust, 0, 0, dust.width, dust.height, 0, 0, p.width, p.height,
		// p.DARKEST);
		dust.endDraw();
	}

	/**
 * 
 */
	public void drawBackground() {
		p.background(this.dust);
		// TODO think about level coordnaties and applet coordinates

	}

	/**
	 * @param img
	 * @param toTile
	 */
	public Ground(String img, boolean toTile, Main applet) {
		super(applet, new PVector(0, 0), img, 0, 0, 0, new PVector());
		if (toTile) {
			PGraphics pg = applet.createGraphics(p.width, p.height, PConstants.P2D);

			pg.beginDraw();
			int xc = pg.width / sprite.width + 1;
			int yc = pg.height / sprite.height + 1;

			for (int i = 0; i < xc; ++i)
				for (int j = 0; j < yc; ++j) {
					pg.image(sprite, i * sprite.width, j * sprite.height);
				}
			pg.endDraw();

			sprite = p.createImage(pg.width, pg.height, PConstants.RGB);
			sprite.loadPixels();
			pg.loadPixels();
			sprite.pixels = pg.pixels;
			sprite.updatePixels();
		} else {
			// stretch image
			PGraphics pg = applet.createGraphics(p.width, p.height, PConstants.P2D);
			pg.beginDraw();
			pg.image(sprite, 0, 0, pg.width, pg.height);
			pg.endDraw();

			sprite = applet.createImage(pg.width, pg.height, PConstants.RGB);
			sprite.loadPixels();
			pg.loadPixels();
			sprite.pixels = pg.pixels;
			sprite.updatePixels();
		}

		blood = p.loadImage("blood.png");

		dust = p.createGraphics(p.width, p.height, PConstants.P2D);
		dust.beginDraw();
		dust.image(sprite, 0, 0);// background(255);
		dust.endDraw();
		// Ground(String txt, boolean state) {
	}
}
