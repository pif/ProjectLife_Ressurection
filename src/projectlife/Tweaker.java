package projectlife;

import processing.core.*;

/**
*/
public class Tweaker extends MyObject{
	public class Tweak {
		float value;
		
		long startTime;
		long lastingTime;
		
		Tweak(float value) {
			this.value=value;
		}
		
		boolean isOn(){
			if(value>0) {
				return true;
			}
			return false;
		}
	}
	/**
*/
	public Tweak healthFactor;
	/**
*/
	/**
*/
	public Tweak  reloadFactor;
	/**
*/
	public Tweak  speedFactor;
	public Tweak  accuracyFactor;
	public Tweak  scaleFactor;

	public Tweak  seeHealth;

	public Tweaker(Main applet) {
		super(applet);
		
		this.healthFactor = new Tweak(1);
		this.reloadFactor = new Tweak(1);
		this.speedFactor = new Tweak(1);
		this.accuracyFactor = new Tweak(1);
		this.scaleFactor = new Tweak(1);
		this.seeHealth= new Tweak(1);
	}
	
	/**
	 * @param warrior
	 * @param Return
	 */
	public void tweak(Warrior warrior, String command) {
		if(p.availableWeapons.containsKey(command)) {
			//got weapon bonus
			boolean hasWeapon=false;
			for(int i=0;i<warrior.weapons.size();++i) {
				if(warrior.weapons.get(i).getClass().getName().equalsIgnoreCase(command)){
					hasWeapon=true;
					break;
				}
			}
			if(!hasWeapon) {
				warrior.weapons.add(Weapon.factory(p, warrior, p.availableWeapons.get(command), command));
			}
		}
		if(command.equals("kulish")) {
			
		}
	}
}
