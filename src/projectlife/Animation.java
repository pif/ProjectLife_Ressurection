package projectlife;

import processing.core.*;
import processing.xml.XMLElement;

public class Animation extends MyObject {

	public class Sprite {
		PImage image;
		long time;
		long startTime;

		public Sprite(PImage image, long time) {
			this.image = image;
			this.time = time;
		}
		
		public long getTime() {
			return time;
		}
	}

	public Sprite[] sprites;
	public int currentSprite;
	public boolean paused;

	public void addSprite(PImage sprite, long time) {
		sprites = (Sprite[]) PApplet.append(sprites, new Sprite(sprite, time));
	}

	public PImage getNext() {
		if (sprites.length > 0) {
			if (!paused) {
				// System.out.println(System.currentTimeMillis()+"--"+sprites[currentSprite].startTime);
				if (System.currentTimeMillis()
						- sprites[currentSprite].startTime >= sprites[currentSprite].time) {
					currentSprite++;
					currentSprite %= sprites.length;
					sprites[currentSprite].startTime = System
							.currentTimeMillis();
				}
			}
			return sprites[currentSprite].image;
		} else
			return null;
	}

	public void pause() {
		paused = true;
	}

	public void goOn() {
		paused = false;
	}

	public Animation(Main applet) {
		sprites = new Sprite[0];
		currentSprite = 0;
		paused = false;
	}

	public Animation(XMLElement spriteInfo, Main applet) {
		sprites = new Sprite[0];
		currentSprite = 0;
		paused = false;

		for (int i = 0; i < spriteInfo.getChildCount(); ++i) {
			this.addSprite(applet.loadImage(applet.dataPath
					+ spriteInfo.getChild(i).getStringAttribute("image")),
					spriteInfo.getChild(i).getIntAttribute("time"));
		}
	}

	public Animation(Animation animation) {
		this.p = animation.p;

		sprites = new Sprite[0];
		currentSprite = 0;
		paused = false;

		for (int i = 0; i < animation.sprites.length; ++i) {
			this.addSprite(animation.sprites[i].image,
					animation.sprites[i].time);
		}
	}
}
