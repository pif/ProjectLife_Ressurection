package projectlife;

import java.io.*;
import java.net.*;
import java.util.HashMap;

import processing.core.*;
import processing.opengl.*;
import processing.xml.*;

public class Main extends PApplet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1886093456349654286L;

	public Level[] levels;
	public Level level;
	public Controller controller;
	public MenuManager menu;
	public Logon logon;
	public HashMap<String, XMLElement> availableWeapons;
	public HashMap<String, XMLElement> availableWarriors;
	public PFont debuggy;
	public String runPath;
	public String dataPath;
	public String levelPath;
	public boolean debug;
	public int warriorSelected;
	public int currentLevel;

	public void setup() {
		// size(600, 300, P2D);
		// size(screen.width, screen.height, OPENGL);
		size(screen.width, screen.height, OPENGL);
		runPath = Main.class.getResource("./").getPath().substring(1);// toString();
		dataPath = runPath + "../data/";
		// System.out.println(dataPath+"main.xml");

		XMLElement preferences = new XMLElement(this, dataPath + "main.xml");

		if (Integer.parseInt(preferences.getChild("debug").getContent()) == 1) {
			debug = true;
		} else {
			debug = false;
		}
		debuggy = createFont("arial", 32);
		textFont(debuggy);
		// System.out.println(preferences.getChild("fps").getContent());
		if (Integer.parseInt(preferences.getChild("fps").getContent()) != -1) {
			frameRate(Integer
					.parseInt(preferences.getChild("fps").getContent()));
		}

		if (Integer.parseInt(preferences.getChild("smooth").getContent()) == 1) {
			smooth();
			// hint(ENABLE_OPENGL_4X_SMOOTH);
		}
		if (Integer.parseInt(preferences.getChild("stroke").getContent()) == 0) {
			noStroke();
		}
		// image(loadImage(runPath+"../data/beast.png"),0,0);
		final int cursor = Integer.parseInt(preferences.getChild("cursor")
				.getContent());
		if (cursor == -1) {
			cursor(loadImage(preferences.getChild("cursor").getStringAttribute(
					"src")), 8, 8);
		} else {
			cursor(cursor);
		}

		background(Integer.parseInt(preferences.getChild("bg").getContent()));
		rectMode(Integer.parseInt(preferences.getChild("rect").getContent()));
		ellipseMode(Integer.parseInt(preferences.getChild("ellipse")
				.getContent()));

		levelPath = dataPath + preferences.getChild("levelDir").getContent()
				+ "/";

		// load available weapons for the game
		availableWeapons = new HashMap<String, XMLElement>();
		XMLElement weapons = new XMLElement(this, dataPath + "weapons.xml");
		for (int i = 0; i < weapons.getChildCount(); ++i) {
			availableWeapons.put(weapons.getChild(i).getName(), weapons
					.getChild(i));
		}

		availableWarriors = new HashMap<String, XMLElement>();
		XMLElement warriors = new XMLElement(this, dataPath + "warriors.xml");
		println(warriors);
		for (int i = 0; i < warriors.getChildCount(); ++i) {
			availableWarriors.put(warriors.getChild(i).getName(), warriors
					.getChild(i));
		}
		logon = new Logon(this);
		menu = new MenuManager(this, preferences.getChild("pause"));

		// TODO Load levels

		levels = new Level[0];
		levels = (Level[]) append(levels, new Level(dataPath + "levels/1.xml",
				this));
		currentLevel = 0;
		level = levels[currentLevel];
		controller = new Controller(this, level.warriors[0], preferences
				.getChild("controller"));

		// try {
		// XMLElement locationByIpData = new XMLElement(sendGetRequest(
		// "http://ipinfodb.com/ip_query.php", "ip=&timezone=false"));
		// System.out
		// .println(sendGetRequest("http://www.google.com/ig/api",
		// "weather="
		// + locationByIpData.getChild("City")
		// .getContent()));
		// //FIXME longitude, altitude!
		// } finally {
		//			
		// }

		// System.out.println(sendGetRequest("http://www.google.com/ig/api",
		// "weather=,,,50000000,24016667"));
		// System.out.println(sendGetRequest("http://ipinfodb.com/ip_query.php",
		// "ip=&timezone=false"));
		warriorSelected = -1;
		ellipse(100, 100, 100, 100);
	}

	public void draw() {
		fill(0);
		ellipse(100, 100, 100, 100);
		// controller.controlMouse();
		if (warriorSelected != -1) {
			if (mousePressed) {
				controller.press(mouseButton);
			}
			if (level.warriors[0].visible) {
				level.display();

				if (level.won()) {
					currentLevel++;
					if (currentLevel < levels.length) {
						level = levels[currentLevel];
					} else {
						// TODO winscreen
						exit();
					}
				}
			} else {
				pushStyle();
				fill(255);
				textAlign(CENTER);
				text((int)level.exp,width/2,height/2);
				popStyle();
			}
			menu.display();

			if (debug) {
				if (level.warriors.length > 0)
					text(((int) frameRate) + " " + level.beasts.length + "\n"
							+ level.warriors[0].weapon.bullets.length + "\n"
							+ level.warriors[0].weapon.getClass().getName()
							+ "\npower \t" + level.warriors[0].power
							+ "\naccuracy \t" + level.warriors[0].accuracy
							+ "\nspeed \t" + level.warriors[0].maxSpeed
							+ "\nhealth \t" + level.warriors[0].health
							+ "\nreload \t" + level.warriors[0].reloadTime
							+ "\nradius \t" + level.warriors[0].radius, 50, 50);
			}
		} else {
			logon.display();
		}
	}

	public void keyPressed() {
		if (warriorSelected != -1) {
			controller.press(keyCode);
		}
	}

	public void keyReleased() {
		if (warriorSelected != -1) {
			controller.release(keyCode);
		}
	}

	public void mousePressed() {
		if (warriorSelected != -1) {
			controller.press(mouseButton);
		} else {
			warriorSelected = logon.getWarrior();
			if (warriorSelected != -1) {
				level.setWarrior(logon.getWarriorString(warriorSelected));
			}
		}
	}

	public void mouseReleased() {
		if (warriorSelected != -1) {
			controller.release(mouseButton);
		}
	}

	public static String sendGetRequest(String endpoint,
			String requestParameters) {
		String result = null;
		if (endpoint.startsWith("http://")) {
			// Send a GET request to the servlet
			try {
				// Construct data
				StringBuffer data = new StringBuffer();

				// Send data
				String urlStr = endpoint;
				if (requestParameters != null && requestParameters.length() > 0) {
					urlStr += "?" + requestParameters;
				}
				URL url = new URL(urlStr);
				URLConnection conn = url.openConnection();

				// Get the response
				BufferedReader rd = new BufferedReader(new InputStreamReader(
						conn.getInputStream()));
				StringBuffer sb = new StringBuffer();
				String line;
				while ((line = rd.readLine()) != null) {
					sb.append(line);
				}
				rd.close();
				result = sb.toString();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public static void main(String args[]) {
		PApplet.main(new String[] { "--present", "--bgcolor=#000000",
				"--present-stop-color=#000000", "projectlife.Main" });
	}
}
