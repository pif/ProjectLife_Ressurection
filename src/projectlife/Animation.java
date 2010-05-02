package projectlife;

import processing.core.*;
import processing.xml.XMLElement;

public class Animation {

	public class Sprite {
		PImage image;
		long time;
		long startTime;

		public Sprite(PImage image, long time) {
			this.image = image;
			this.time = time;
		}
	}

	public Sprite[] sprites;
	public int currentSprite;
	public boolean paused;

	public void addSprite(PImage sprite, long time) {
		sprites = (Sprite[]) PApplet.append(sprites, new Sprite(sprite, time));
	}

	public PImage getNext() {
		if (!paused) {
			// System.out.println(System.currentTimeMillis()+"--"+sprites[currentSprite].startTime);
			if (System.currentTimeMillis() - sprites[currentSprite].startTime >= sprites[currentSprite].time) {
				currentSprite++;
				currentSprite %= sprites.length;
				sprites[currentSprite].startTime = System.currentTimeMillis();
			}
		}
		return sprites[currentSprite].image;
	}

	public void pause() {
		paused = true;
	}
	
	public void goOn() {
		paused = false;
	}

	public Animation() {
		sprites = new Sprite[0];
		currentSprite = 0;
		paused = false;
	}
	
	public Animation(XMLElement spriteInfo){
		
	}
}
