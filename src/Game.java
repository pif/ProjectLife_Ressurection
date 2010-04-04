import processing.core.*;

/**
*/
public class Game extends MyObject {
	/**
*/
	public Bonus[] bonuses;
	/**
*/
	public Beast[] beasts;
	/**
*/
	public Overlay[] overlays;

	/**
	 * @param xmlConf
	 */
	public Game(String xmlConf, Main applet) {
		super(applet);
	}
}
