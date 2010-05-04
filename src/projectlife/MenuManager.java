package projectlife;

import processing.core.*;
import processing.xml.XMLElement;

/**
*/
public class MenuManager extends OverlayManager {

	// visible: true == game is on, false == menu is on fire!

	public MenuManager(Main applet) {
		super(applet);
		overlays = new Overlay[] {
				new Overlay(applet, new PVector(p.width / 2, 50), p.dataPath
						+ "images/play.png", PApplet.radians(0), 100, 0,
						new PVector()),
				new Overlay(applet, new PVector(p.width / 2, 100), p.dataPath
						+ "images/records.png", PApplet.radians(0), 10100, 0,
						new PVector()),
				new Overlay(applet, new PVector(p.width / 2, 150), p.dataPath
						+ "images/exit.png", PApplet.radians(0), 20100, 0,
						new PVector()) };
		visible = true;
		// overlays = (Overlay[])PApplet.append(overlays, new Overlay(applet,
		// position, img, angle, color, radius))
	}

	public MenuManager(Main applet, XMLElement preferences) {
		super(applet);
		overlays = new Overlay[3];
		for (int i = 0; i < preferences.getChildCount(); i++) {
			XMLElement item = preferences.getChild(i);
			// 0=play button 1=records button 2=exit game
			String function = item.getStringAttribute("function");
			if (function.equals("play")) {
				overlays[0] = new Overlay(applet, new PVector(((item
						.getFloatAttribute("left") == -1) ? (p.width / 2)
						: (item.getFloatAttribute("left"))), item
						.getFloatAttribute("top")), item
						.getStringAttribute("src"), item
						.getFloatAttribute("angle"), 0, item
						.getFloatAttribute("radius"), new PVector());
			} else if (function.equals("records")) {
				overlays[1] = new Overlay(applet, new PVector(((item
						.getFloatAttribute("left") == -1) ? (p.width / 2)
						: (item.getFloatAttribute("left"))), item
						.getFloatAttribute("top")), item
						.getStringAttribute("src"), item
						.getFloatAttribute("angle"), 0, item
						.getFloatAttribute("radius"), new PVector());
			} else if (function.equals("exit")) {
				overlays[2] = new Overlay(applet, new PVector(((item
						.getFloatAttribute("left") == -1) ? (p.width / 2)
						: (item.getFloatAttribute("left"))), item
						.getFloatAttribute("top")), item
						.getStringAttribute("src"), item
						.getFloatAttribute("angle"), 0, item
						.getFloatAttribute("radius"), new PVector());
			} else {
				// <item name="play" src="images/play.png" top="50" left="-1"
				// angle="0" radius="-1"></item>
				overlays = (Overlay[]) PApplet
						.append(
								overlays,
								new Overlay(
										applet,
										new PVector(
												((item
														.getFloatAttribute("left") == -1) ? (p.width / 2)
														: (item
																.getFloatAttribute("left"))),
												item.getFloatAttribute("top")),
										item.getStringAttribute("src"), item
												.getFloatAttribute("angle"), 0,
										item.getFloatAttribute("radius"),
										new PVector()));
			}
		}

		show();
		// overlays = (Overlay[])PApplet.append(overlays, new Overlay(applet,
		// position, img, angle, color, radius))
	}

	/**
	 * 0=play button 1=records button 2=exit game
	 */
	/**
	 * @param Return
	 */
	public void click(PVector point) {
		switch (this.getClickedOverlay(point)) {
		case 0:
			this.hide();
			p.level.start();
			break;
		case 1:
			// p.records.show();
			break;
		case 2:
			p.exit();
			break;

		default:
			break;
		}
	}

	public void display() {
		if (visible) {
			p.pushStyle();
			p.fill(0, 0, 0, 140);
			p.rect(p.width / 2, p.height / 2, p.width, p.height);
			p.popStyle();

			super.display();
			// for (int i = 0; i < overlays.length; i++) {
			// overlays[i].display();
			// }
		}

	}

	public void show() {
		super.show();
		if (p.level != null) {
			p.level.suspend();
		}
	}

	public void hide() {
		super.hide();
		if (p.level != null) {
			p.level.start();
		}
	}

}
