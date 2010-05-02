package projectlife;

import processing.core.*;
import processing.xml.XMLElement;
import projectlife.weapons.Teeth;

/**
*/
public class Controller extends MyObject {

	public int up;
	public int down;
	public int left;
	public int right;
	public int shoot;
	public int getLevel;
	public int menu;
	public int nextWeapon;
	public int prevWeapon;

	public Controller(Main applet, MovingObject warrior) {
		super(applet);
		// this.warrior = warrior;
		up = 87;// 'w';//PConstants.UP;
		down = 83;// 's';//PConstants.DOWN;
		left = 65;// 'a';//PConstants.LEFT;
		right = 68;// 'd';//PConstants.RIGHT;
		shoot = PConstants.LEFT;
		getLevel = PConstants.RIGHT;
		menu = PConstants.ESC;
		nextWeapon = (int) 'e';// TODO next, previous weapon
		prevWeapon = (int) 'q';
	}

	public Controller(Main applet, MovingObject warrior, XMLElement preferences) {
		super(applet);

		up = preferences.getIntAttribute("up");
		down = preferences.getIntAttribute("down");
		left = preferences.getIntAttribute("left");
		right = preferences.getIntAttribute("right");
		shoot = preferences.getIntAttribute("shoot");
		getLevel = preferences.getIntAttribute("level");
		menu = preferences.getIntAttribute("menu");
		nextWeapon = preferences.getIntAttribute("next");
		prevWeapon = preferences.getIntAttribute("prev");
	}

	/**
*/
	private static boolean[] keys = new boolean[256];

	/**
	 * @param Return
	 */
	public void press(int value) {
		keys[value] = true;

		if (keys[up]) {
			p.level.warriors[0].acceleration.y = -1;// --;
		}
		if (keys[down]) {
			p.level.warriors[0].acceleration.y = +1;// --;
		}
		if (keys[left]) {
			p.level.warriors[0].acceleration.x = -1;// --;
		}
		if (keys[right]) {
			p.level.warriors[0].acceleration.x = +1;// ++;
		}
		// cool input, check everything. Grrreat switch!/that was todo
		// no switch available. instead i use a lot of (if) statements
		if (keys[shoot]) {
			if (p.menu.visible) {
				p.menu.click(new PVector(p.mouseX, p.mouseY));
			} else {
				p.level.warriors[0].shoot(p.mouseX, p.mouseY);
			}
		}
		if (keys[menu]) {
			p.key = 0;
			p.keyCode = 0;

			if (p.menu.visible) {
				p.menu.hide();
			} else {
				p.level.suspend();
				p.menu.show();
			}
		}
		if (value == ' ') {
			// p.level.ground
			// .addBlood(new PVector(p.mouseX, p.mouseY), 0xFFFF0000);  
			p.level.beasts = (Beast[]) PApplet.append(p.level.beasts,
					new Beast(p, new PVector(p.random(100, 200), p.random(100,
							200)), "beast.png", 0, 0, 32, 100, p.random(2, 8),
							new Teeth(p,null),
							p.level.warriors[0].location));
			p.level.beasts[p.level.beasts.length-1].weapon.owner=p.level.beasts[p.level.beasts.length-1];
			if(p.menu.visible){
				p.level.beasts[p.level.beasts.length-1].stop();
			}
		}
	}

	/**
	 * @param value
	 * @param Return
	 */
	public void release(int value) {
		keys[value] = false;
		if (!keys[up]) {
			p.level.warriors[0].acceleration.y = 0;
		} else {
			p.level.warriors[0].acceleration.y = -1;// --;
		}
		if (!keys[down]) {
			p.level.warriors[0].acceleration.y = 0;
		} else {
			p.level.warriors[0].acceleration.y = +1;// ++;
		}
		if (!keys[left]) {
			p.level.warriors[0].acceleration.x = 0;
		} else {
			p.level.warriors[0].acceleration.x = -1;// --;
		}
		if (!keys[right]) {
			p.level.warriors[0].acceleration.x = 0;
		} else {
			p.level.warriors[0].acceleration.x = +1;// ++;
		}
		if (!(keys[up] || keys[down] || keys[left] || keys[right])) {
			p.level.warriors[0].acceleration = new PVector(0, 0);
			p.level.warriors[0].velocity = new PVector(0, 0);
		}
	}

	/**
	 * @param anyKey
	 * @param Return
	 */
	public boolean isOn(int anyKey) {
		return keys[anyKey];
	}
}
