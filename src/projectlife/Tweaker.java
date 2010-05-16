package projectlife;

import processing.core.*;

/**
*/
public class Tweaker extends MyObject {
	public class Tweak {
		float value;
		float previous;

		long startTime;
		boolean stable;
		long lastingTime;
		boolean set;

		Tweak(float value, long last, boolean stable) {
			this.value = value;
			this.lastingTime = last;
			this.stable = stable;
			this.set = false;
		}

		Tweak(float value, boolean stable) {
			this(value, 5000, stable);
		}

		boolean isOn() {
			if (set) {
				return true;
			}
			return false;
		}

		float remove() {
			set = false;
			return previous;
		}

		float set(float previous) {
			if (!set) {
				this.set = true;
				this.startTime = p.millis();
				this.previous = previous;
			} else {
				this.startTime = p.millis();
			}
			return previous * value;
		}

		boolean outdated() {
			if (stable) {
				return false;
			} else {
				return (p.millis() - startTime > lastingTime);
			}
		}
	}

	/**
*/
	public Tweak healthFactor;
	/**
*/
	/**
*/
	public Tweak reloadFactor;
	/**
*/
	public Tweak speedFactor;
	public Tweak accuracyFactor;

	public Tweak seeHealth;

	public Tweaker(Main applet) {
		super(applet);

		this.healthFactor = new Tweak(1.2f, true);
		this.reloadFactor = new Tweak(0.5f, false);
		this.speedFactor = new Tweak(1.2f, false);
		this.accuracyFactor = new Tweak(1f, false);
		this.seeHealth = new Tweak(-1f, true);
	}

	/**
	 * @param warrior
	 * @param Return
	 */
	public void tweak(Warrior warrior, String command) {
		// got weapon bonus
		if (p.availableWeapons.containsKey(command)) {
			boolean hasWeapon = false;
			for (int i = 0; i < warrior.weapons.size(); ++i) {
				if (warrior.weapons.get(i).getClass().getName().contains(command)) {
					hasWeapon = true;
					break;
				}
			}
			if (!hasWeapon) {
				warrior.weapons.add(Weapon.factory(p, warrior,
						p.availableWeapons.get(command), command));
				warrior.updateTargets(p.level);
			}
		}
		if (command.equals("kulish")) {
			warrior.health = healthFactor.set(warrior.health);
		}
		if (command.equals("kin")) {
			warrior.maxSpeed = speedFactor.set(warrior.maxSpeed);
		}
		if (command.equals("reload")) {
			warrior.reloadTime = reloadFactor.set(warrior.reloadTime);
		}
		if (command.equals("accuracy")) {
			warrior.accuracy = reloadFactor.set(warrior.accuracy);
		}
		if (command.equals("see")) {
			seeHealth.value = seeHealth.set(-1);
		}

	}

	public void update(Warrior warrior) {
		if (reloadFactor.set && reloadFactor.outdated())
			warrior.reloadTime = reloadFactor.remove();
		if (speedFactor.set && speedFactor.outdated())
			warrior.maxSpeed = speedFactor.remove();
		if (accuracyFactor.set && accuracyFactor.outdated())
			warrior.accuracy = accuracyFactor.remove();
	}
}
