package projectlife;

import processing.core.PApplet;
import processing.core.PVector;
import processing.xml.XMLElement;

public class EventManager extends MyObject {

	final static int BONUS = 0;
	final static int BEAST = 1;

	Event[] events;

	public class Event {
		long time;
		long times;
		long interval;
		PVector pos;
		int type;// 0==bonus 1==beast
		String value;
		long start;

		boolean finished;
		boolean needed;

		// <event startTime="6000" times="10" interval="1000" type="beast"
		// value="Six" coords="0" x="0" y="0"/>
		public Event(long time, long cnt, long inter, int t, String v,
				int coords, float x, float y) {
			pos = new PVector();

			this.time = time;
			this.times = cnt;
			this.interval = inter;
			this.type = t;
			this.value = v;

			switch (coords) {
			case 0:
				pos.set(p.random(p.width), p.random(p.height), 0);
				break;
			case 1:
				pos.set(x, y, 0);
				break;
			case 2:
				pos.set(PApplet.map(x, 0, 100, 0, p.width), PApplet.map(y, 0, 100, 0,
						p.height), 0);
				break;
			}

			pos.set(x, y, 0);

			finished = false;
			needed = true;

			start = 0;
		}

		void act() {
			if (times > 0) {
				if (type == BONUS) {
					p.level.addBonus(pos, value);
				} else if (type == BEAST) {
					p.level.addBeast(value);
				}

				times--;
			} else {
				needed = false;
			}
		}

		// <event startTime="6000" times="10" interval="1000" type="beast"
		// value="Six" coords="0" x="0" y="0"/>
	}

	// <event startTime="6000" times="10" interval="1000" type="beast"
	// value="Six" coords="0" x="0" y="0"/>

	long start;

	public EventManager(Main applet, XMLElement prefs) {
		super(applet);
		events = new Event[0];
		start = 0;

		for (int i = 0; i < prefs.getChildCount(); ++i) {
			XMLElement event = prefs.getChildAtIndex(i);
			events = (Event[]) PApplet.append(events, new Event(event
					.getIntAttribute("startTime"), event
					.getIntAttribute("times"), event
					.getIntAttribute("interval"),
					event.getIntAttribute("type"), event.getAttribute("value"),
					event.getIntAttribute("coords"),
					event.getIntAttribute("x"), event.getIntAttribute("y")));
		}
		
		
	}

	public void act() {
		if (!p.menu.visible) {
			start+=p.frameRate;		

			for (int i = 0; i < events.length; ++i) {
				if (start - events[i].time > 100) {
					if (events[i].needed) {
						events[i].act();
					}
				}
			}
		}
	}
}
