import processing.core.*;

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

	public Controller(Main applet, Warrior warrior) {
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

	/**
*/
	private static boolean[] keys = new boolean[256];

	/**
	 * @param Return
	 */
	public void press(int value) {
		keys[value] = true;

		if (keys[up]) {
			p.level.warrior.acceleration.y = -1;// --;
		}
		if (keys[down]) {
			p.level.warrior.acceleration.y = +1;// --;
		}
		if (keys[left]) {
			p.level.warrior.acceleration.x = -1;// --;
		}
		if (keys[right]) {
			p.level.warrior.acceleration.x = +1;// ++;
		}
		// TODO cool input, check everything. Grrreat switch!
		if (value == shoot) {
			p.level.warrior.shoot(p.mouseX, p.mouseY);
			p.level.ground
					.addBlood(new PVector(p.mouseX, p.mouseY), 0xFFFF0000);
		}
		if (value == ' ') {
			p.level.ground
					.addBlood(new PVector(p.mouseX, p.mouseY), 0xFFFF0000);
			p.level.beasts = (Beast[]) PApplet.append(p.level.beasts,
					new Beast(p, new PVector(p.random(100, 200), p.random(100,
							200)), "beast.png", 0, 0, 64, 100, 4, new Weapon(p,
							20, 20, 0, 0), p.level.warrior.location));
		} else {
		}
	}

	/**
	 * @param value
	 * @param Return
	 */
	public void release(int value) {
		keys[value] = false;
		if (!keys[up]) {
			p.level.warrior.acceleration.y = 0;
		} else {
			p.level.warrior.acceleration.y = -1;// --;
		}
		if (!keys[down]) {
			p.level.warrior.acceleration.y = 0;
		} else {
			p.level.warrior.acceleration.y = +1;// ++;
		}
		if (!keys[left]) {
			p.level.warrior.acceleration.x = 0;
		} else {
			p.level.warrior.acceleration.x = -1;// --;
		}
		if (!keys[right]) {
			p.level.warrior.acceleration.x = 0;
		} else {
			p.level.warrior.acceleration.x = +1;// ++;
		}
		if (!(keys[up] || keys[down] || keys[left] || keys[right])) {
			p.level.warrior.acceleration = new PVector(0, 0);
			p.level.warrior.velocity = new PVector(0, 0);
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
