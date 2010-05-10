package projectlife;

import processing.core.*;

/**
*/
public class Tweaker {
	/**
*/
	public float healthFactor;
	/**
*/
	/**
*/
	public float reloadFactor;
	/**
*/
	public float speedFactor;
	public float accurancyFactor;
	public float scaleFactor;

	public boolean seeHealth;

	public Tweaker() {
		this.healthFactor = 1f;
		this.reloadFactor = 1f;
		this.speedFactor = 1f;
		this.accurancyFactor = 1f;
		this.scaleFactor = 1f;
	}

	/**
	 * @param warrior
	 * @param Return
	 */
	public void tweak(MovingObject warrior) {
	}
}
